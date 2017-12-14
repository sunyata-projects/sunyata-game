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
import org.sunyata.game.majiang.core.models.message.OperationFaPaiRet;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.majiang.core.service.RoomLockKeys;
import org.sunyata.game.majiang.core.service.RoomService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.optFaPaiRet, routeModel = ServerCommand.directMode)
public class OptFaPaiRetCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(OptFaPaiRetCommand.class);


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
        Room.OperationFaPaiRetReq optFaPaiRetReq = Room.OperationFaPaiRetReq.parseFrom(request.getMessage()
                .getRawMessage().getBody());
        int userIdInGateway = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        //SceneUser sceneUser = roomService.getSceneUser(userIdInGateway, gatewayServerId);
//        RoomImpi room = sceneUser.getRoom();
        OperationFaPaiRet operationFaPaiRet = new OperationFaPaiRet();
        operationFaPaiRet.setOpt(optFaPaiRetReq.getOpt());
        operationFaPaiRet.setPai(optFaPaiRetReq.getPai());
        //room.faPaiRet(sceneUser, operationFaPaiRet);
        Object bean = applicationContext.getBean(Commands.optFaPaiRet);
        roomService.handler((MessageHandler) bean, operationFaPaiRet, userIdInGateway, gatewayServerId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean auth(String userName, String password) {
        return true;
    }
}
