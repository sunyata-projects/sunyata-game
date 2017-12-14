package org.sunyata.game.majiang.scene.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.message.OperationOutRet;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.majiang.core.service.RoomLockKeys;
import org.sunyata.game.majiang.core.service.RoomService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.optOutRet, routeModel = ServerCommand.directMode)
public class OptOutRetCommand extends AbstractCommandHandler  implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(OptOutRetCommand.class);


    @Autowired
    RoomDomainService roomDomainService;
    @Autowired
    RoomLockKeys roomLockKeys;
    @Autowired
    LockService lockService;

    @Autowired
    RoomService roomService;
    private ApplicationContext applicationContext;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }


    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        Room.OperationOutRetReq operationOutRetReq = Room.OperationOutRetReq.parseFrom(request.getMessage()
                .getRawMessage().getBody());
        int userIdInGateway = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        //SceneUser sceneUser = roomService.getSceneUser(userIdInGateway, gatewayServerId);
        //RoomImpi room = sceneUser.getRoom();
        OperationOutRet operationOutRet = new OperationOutRet();
        operationOutRet.setPai(operationOutRetReq.getPai());
        //room.outRet(sceneUser, operationOutRet);
        Object bean = applicationContext.getBean(Commands.optOutRet);
        roomService.handler((MessageHandler) bean, operationOutRet, userIdInGateway, gatewayServerId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public boolean auth(String userName, String password) {
        return true;
    }
}
