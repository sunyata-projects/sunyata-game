package org.sunyata.game.room.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.ErrorCodes;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.majiang.core.exceptions.RoomFullException;
import org.sunyata.game.majiang.core.models.AllocateSeatRetInfo;
import org.sunyata.game.majiang.core.models.message.CheckJoinRoom;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserLocationInfo;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.joinRoom, routeModel = ServerCommand.lbMode)
public class JoinRoomCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(JoinRoomCommand.class);

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
        byte[] body = request.getMessage().getRawMessage().getBody();
        Room.JoinRoomReq joinRoomReq = Room.JoinRoomReq.parseFrom(body);
        String roomCheckId = joinRoomReq.getRoomCheckId();
        int userIdInGateway = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        UserLocationInfo userInfo = userLocationService.getUserLocationInfo(gatewayServerId, userIdInGateway);
        String existsCheckId = roomDomainService.loadUnEndRoomCheckId(userInfo.getUserId());
        if (existsCheckId != null) {
            if (!existsCheckId.equalsIgnoreCase(roomCheckId)) {
                anyClientManager.sendErrorMessageToUser(userIdInGateway, gatewayServerId, Integer.parseInt(ErrorCodes
                        .hasJoinRoom));
                return;
            }
        }
        try {
            AllocateSeatRetInfo allocateSeatRetInfo = roomDomainService.allocateSeat(userInfo.getUserId(), userInfo
                    .getUserName(), roomCheckId);
            CheckJoinRoom checkJoinRoom = new CheckJoinRoom().setUserId(userInfo.getUserId()).setRoomCheckId
                    (roomCheckId);

//            anyClientManager.sendSysMessage(ServiceNames.game_majiang_scene_service, allocateSeatRetInfo.getRoom()
//                    .getSceneId(), userIdInGateway, Commands.joinRoomToScene, checkJoinRoom);

            anyClientManager.sendSysMessageByInnerGateway(Commands.joinRoomToScene, checkJoinRoom,
                    allocateSeatRetInfo.getRoom().getSceneId());
        } catch (RoomFullException ex) {
            anyClientManager.sendErrorMessageToUser(userIdInGateway, gatewayServerId, Integer.parseInt(ErrorCodes
                    .roomIsFull));
        }
    }


    public boolean auth(String userName, String password) {
        return true;
    }
}
