package org.sunyata.game.majiang.core.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.mapper.RoomMapper;
import org.sunyata.game.majiang.core.models.room.CreateRoomType;
import org.sunyata.game.majiang.core.models.room.RoomEntity;
import org.sunyata.game.majiang.core.models.room.UserRoom;

import java.util.List;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class RoomDao {
    protected final Logger LOGGER = LoggerFactory.getLogger(RoomDao.class);

    @Autowired
    RoomMapper roomMapper;

    public RoomEntity getNotEndRoom(String roomCheckId) throws Exception {
        RoomEntity room = roomMapper.getNotEndRoom(roomCheckId);
        return room;
    }

    public void insert(RoomEntity room) {
        roomMapper.insertRoom(room);
    }

    public List<UserRoom> getUserRooms(Integer roomId) {
        return roomMapper.getUserRooms(roomId);
    }

    public void updateUserRoom(UserRoom userRoomExistUser) {

    }

    public void insertUserRoom(UserRoom allocateUserRoom) {
        roomMapper.insertUserRoom(allocateUserRoom);
    }

    public UserRoom getUserRoom(int userId, boolean isDisable) {
        return roomMapper.getUserRoom(userId, isDisable);
    }

    public RoomEntity getRoomById(Integer roomId) {
        return roomMapper.getRoomById(roomId);
    }

    public UserRoom getUnEndUserRoom(int userId) {
        return roomMapper.getUnEndUserRoom(userId);
    }

    public RoomEntity getRoomByCreateUserId(Integer userId, CreateRoomType forMySelf) {
        return roomMapper.getRoomByCreateUserId(userId, forMySelf.getValue());
    }
}
