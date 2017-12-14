package org.sunyata.game.majiang.core.service;

import org.sunyata.core.json.Json;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.lock.MutiLock;
import org.sunyata.core.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.exceptions.NotExistsRoomException;
import org.sunyata.game.majiang.core.exceptions.RoomFullException;
import org.sunyata.game.majiang.core.models.*;
import org.sunyata.game.majiang.core.models.room.*;
import org.sunyata.game.majiang.core.store.RoomDao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class RoomDomainService {
    @Autowired
    RoomCheckIdPoolUtils roomCheckIdPoolUtils;

    @Autowired
    RoomDao roomDao;
    @Autowired
    LockService lockService;

    public String createRoom(Integer userId, String orderId, Integer sceneId, RoomConfigInfo roomConfigInfo) throws
            Exception {
        RoomEntity room = new RoomEntity();
        //获取checkId
        String roomCheckId = roomCheckIdPoolUtils.getBufferId();
        room
                .setOrderId(orderId)
                .setRoomCheckId(roomCheckId)
                .setCreateUserId(userId)
                .setUuid(UUID.generateNoSep())
                .setSceneId(sceneId)
                .setUserMax(roomConfigInfo.getUserMax())
                .setChapterNums(roomConfigInfo.getChapterNums())
                .setCreateDate(new Date())
                .setStartDate(new Date())
                .setStatus(RoomStatusType.Init.getValue())
                .setCreateRoomType(roomConfigInfo.getCreateRoomType())
                .setConfig(Json.encode(roomConfigInfo));
        roomDao.insert(room);
        initRoomData(room);
        return roomCheckId;
    }

    private void initRoomData(RoomEntity room) {

    }

    public boolean existsForMySelf(Integer userId) {
        RoomEntity ro = roomDao.getRoomByCreateUserId(userId, CreateRoomType.MySelf);
        return ro != null;
    }

    @Autowired
    RoomLockKeys lockKeys;

    public AllocateSeatRetInfo allocateSeat(int userId, String userName, String roomCheckId) throws Exception {
        MutiLock getlock = lockService.getlock(lockKeys.getJoinGameLockKey(roomCheckId));
        getlock.acquire(5, TimeUnit.SECONDS);
        try {
            //获取当前房卡号对应的没有结束的房间信息
            RoomEntity room = roomDao.getNotEndRoom(roomCheckId);
            if (room == null) {
                throw new NotExistsRoomException("不存在的房间ID:" + roomCheckId);
            }

            //获取当前房间的玩家信息
            List<UserRoom> userRooms = roomDao.getUserRooms(room.getId());

            UserRoom allocateUserRoom = null;

            //获取玩家信息中是否存在当前玩家
            UserRoom userRoomExistUser = userRooms.stream().filter(p -> p.getUserId().equals(userId))
                    .findFirst().orElse(null);
            if (userRoomExistUser != null) {
                if (userRoomExistUser.isDisable()) {//如果当前玩家不可用,则更新为可用
                    userRoomExistUser.setDisable(false);
                    roomDao.updateUserRoom(userRoomExistUser);
                }
                allocateUserRoom = userRoomExistUser;

            } else {
                int userMax = room.getUserMax();
                if (roomIsFull(userRooms, userMax)) {//房间已满
                    throw new RoomFullException("room is full:" + room.getId());

                }
                //开始分配
                for (int i = 0; i < userMax; i++) {
                    final Integer position = i;
                    UserRoom userRoom = userRooms.stream().filter(p -> p.getPosition().equals(position) && !p
                            .isDisable()).findFirst().orElse(null);
                    if (userRoom == null) {
                        allocateUserRoom = innerAllocateSeat(room, userId, userName, position);
                        roomDao.insertUserRoom(allocateUserRoom);
                        break;
                    }
                }
            }
            if (allocateUserRoom == null) {
                throw new Exception("分配房间时出错");
            }
//            List<UserRoom> userRoomsRet = roomDao.getUserRooms(room.getId());
//            room.setUserRooms(userRoomsRet);
            return new AllocateSeatRetInfo().setRoom(room).setUserRoom(allocateUserRoom);
        } finally {
            getlock.release();
        }
    }


    private UserRoom innerAllocateSeat(RoomEntity room, Integer userId, String userName, Integer locationId) {
        return new UserRoom().setRoomId(room.getId()).setUserId(userId).setDisable(false)
                .setJoinDate(new Date()).setPosition(locationId).setScore(0).setUserName(userName);
    }

    public boolean roomIsFull(List<UserRoom> userRooms, int userMax) {
        long count = userRooms.stream().filter(p -> !p.isDisable()).count();
        return count >= userMax;
    }

    public RoomEntity loadRoom(String roomCheckId) throws Exception {
        RoomEntity notEndRoom = roomDao.getNotEndRoom(roomCheckId);
        if (notEndRoom != null) {
            List<UserRoom> userRooms = roomDao.getUserRooms(notEndRoom.getId());
            notEndRoom.setUserRooms(userRooms);
        }
        return notEndRoom;
    }

    public RoomEntity loadRoomById(Integer roomId) throws Exception {
        RoomEntity notEndRoom = roomDao.getRoomById(roomId);
        if (notEndRoom != null) {
            List<UserRoom> userRooms = roomDao.getUserRooms(notEndRoom.getId());
            notEndRoom.setUserRooms(userRooms);
        }
        return notEndRoom;
    }

    public UserRoom loadUserRoom(int userId, boolean isDisable) throws Exception {
        return roomDao.getUserRoom(userId, isDisable);
    }

    public UserRoom loadUnEndRoom(int userId) throws Exception {
        return roomDao.getUnEndUserRoom(userId);
    }

    public String loadUnEndRoomCheckId(int userId) throws Exception {
        UserRoom unEndUserRoom = roomDao.getUnEndUserRoom(userId);
        if (unEndUserRoom != null) {
            RoomEntity roomById = roomDao.getRoomById(unEndUserRoom.getRoomId());
            return roomById.getRoomCheckId();
        }
        return null;
    }
}