package org.sunyata.game.majiang.scene.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.lock.MutiLock;
import org.sunyata.game.majiang.core.message.OctopusCheckJoinRoomMsg;
import org.sunyata.game.majiang.core.models.message.CheckJoinRoom;
import org.sunyata.game.majiang.core.models.room.RoomEntity;
import org.sunyata.game.majiang.core.models.UserInfo;
import org.sunyata.game.majiang.core.models.room.UserRoom;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.majiang.core.service.RoomLockKeys;
import org.sunyata.game.majiang.core.service.RoomService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserCacheInfo;
import org.sunyata.game.service.UserLocationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.joinRoomToScene, routeModel = ServerCommand.directMode)
public class JoinRoomToSceneCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(JoinRoomToSceneCommand.class);


    @Autowired
    RoomDomainService roomDomainService;
    @Autowired
    RoomLockKeys roomLockKeys;
    @Autowired
    LockService lockService;

    @Autowired
    RoomService roomService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }


    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        byte[] body = request.getMessage().getRawMessage().getBody();
        CheckJoinRoom checkJoinRoom = (CheckJoinRoom) new CheckJoinRoom().parseFrom(body);
        MutiLock getlock = lockService.getlock(roomLockKeys.getJoinGameLockKey(checkJoinRoom.getRoomCheckId()));
        getlock.acquire(5, TimeUnit.SECONDS);//加锁
        try {
            RoomEntity room = roomDomainService.loadRoom(checkJoinRoom.getRoomCheckId());
            OctopusCheckJoinRoomMsg msg = new OctopusCheckJoinRoomMsg();
            msg.setRoomEntity(room);
            msg.setSceneId(room.getSceneId());
            msg.setRoomId(room.getId());
            msg.setCreateUserId(room.getCreateUserId());
            msg.setUserMax(room.getUserMax());
            msg.setUuid(room.getUuid());
            msg.setRoomCheckId(room.getRoomCheckId());
            UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(checkJoinRoom.getUserId());
            UserCacheInfo userJoinCacheInfo = userCacheService.getUserInfo(checkJoinRoom.getUserId());
            List<UserInfo> userInfos = new ArrayList<>();
            msg.setJoinGatewayId(userLocationInfo.getServerId());
            msg.setJoinSessionId(userLocationInfo.getUserIdInGateway());
            msg.setJoinUserId(userJoinCacheInfo.getId());
            for (UserRoom userRoom : room.getUserRooms()) {
                UserInfo ui = new UserInfo();
                UserCacheInfo currUserCacheInfo = userCacheService.getUserInfo(userRoom.getUserId());
                if (currUserCacheInfo == null) {
                    continue;
                }
                ui.setAvatar(currUserCacheInfo.getAvatar());
                ui.setIp(currUserCacheInfo.getIp());
                ui.setLatitude(currUserCacheInfo.getLatitude());
                ui.setLongitude(currUserCacheInfo.getLongitude());
                ui.setLocationIndex(userRoom.getPosition());
                ui.setSex(currUserCacheInfo.getSex());
                ui.setUserId(userRoom.getUserId());
                ui.setUserName(userRoom.getUserName());
                ui.setScore(userRoom.getScore());
                ui.setGold(1000);
                userInfos.add(ui);
            }
            msg.setUserInfos(userInfos);
            roomService.checkJoinRoom(msg);

        } finally {
            getlock.release();
        }
    }
}
