package org.sunyata.game.majiang.scene.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.majiang.core.message.UserJoinGameJsonBodySerializer;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.majiang.core.service.RoomLockKeys;
import org.sunyata.game.majiang.core.service.RoomService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.userJoinGameEvent, routeModel = ServerCommand.lbMode)
public class UserJoinGameEventCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(UserJoinGameEventCommand.class);


    @Autowired
    RoomDomainService roomDomainService;
    @Autowired
    RoomLockKeys roomLockKeys;
    @Autowired
    LockService lockService;

    @Autowired
    RoomService roomService;

    @Autowired
    UserLocationService userLocationService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }


    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        byte[] body = request.getMessage().getRawMessage().getBody();
        UserJoinGameJsonBodySerializer jsonBodySerializer = (UserJoinGameJsonBodySerializer) new
                UserJoinGameJsonBodySerializer().parseFrom(body);
        UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(jsonBodySerializer.getUserId());
        userLocationService.cacheUserLocation(userLocationInfo.setRoomId(jsonBodySerializer.getRoomId())
                .setSceneServerId(jsonBodySerializer.getSceneServerId()));
    }
}
