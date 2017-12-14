package org.sunyata.game.room.commands;

import org.sunyata.game.command.CommandService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.command.AbstractCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.ServiceNames;
import org.sunyata.game.majiang.core.models.message.JoinGameToSceneMsg;
import org.sunyata.game.majiang.core.models.room.RoomEntity;
import org.sunyata.game.majiang.core.models.room.UserRoom;
import org.sunyata.game.majiang.core.service.RoomDomainService;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.joinGame, routeModel = ServerCommand.lbMode)
public class JoinGameCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(JoinGameCommand.class);


    @Autowired
    RoomDomainService roomDomainService;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    @Autowired
    AnyClientManager anyClientManager;
    @Autowired
    CommandService commandService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }


    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        int userIdInGateway = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        UserLocationInfo userInfo = userLocationService.getUserLocationInfo(gatewayServerId, userIdInGateway);
        UserRoom userRoom = roomDomainService.loadUserRoom(userInfo.getUserId(), false);
        if (userRoom != null) {
            RoomEntity roomEntity = roomDomainService.loadRoomById(userRoom.getRoomId());
            anyClientManager.sendSysMessage(ServiceNames.game_majiang_scene_service, roomEntity.getSceneId(),
                    userIdInGateway, Commands.joinGameToScene, new JoinGameToSceneMsg().setGatewayServerId
                            (gatewayServerId).setUserId(userRoom.getUserId()).setUserIdInGateway(userIdInGateway));
        }
    }



    public boolean auth(String userName, String password) {
        return true;
    }
}
