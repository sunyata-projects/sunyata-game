package org.sunyata.game.inner.gateway.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserCacheService;


/**
 * Created by leo on 17/11/9.
 */
@ServerCommand(commandId = Commands.loginSuccessEvent, routeModel = ServerCommand.fanoutMode)
public class LoginEventHandler extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LoginEventHandler.class);
    @Autowired
    UserCacheService userCacheService;

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
//        logger.info("登录事件接收成功:sourceServerId:{}", request.getMessage().getSourceServerId());
//        byte[] body = request.getMessage().getRawMessage().getBody();
////        OctopusSystemMessage.LoginSuccessEventMsg loginSuccessEventMsg = OctopusSystemMessage.LoginSuccessEventMsg
////                .parseFrom(body);
//        LoginSuccessEventInfo loginSuccessEventMsg = (LoginSuccessEventInfo) new LoginSuccessEventInfo().parseFrom
//                (body);
//        userCacheService.cacheUserLocation(loginSuccessEventMsg.getUserName(), loginSuccessEventMsg.getGatewayServerId(),
//                loginSuccessEventMsg.getUserIdInGateway());
//        UserLocationInfo userInfo = userCacheService.getUserLocationInfo(loginSuccessEventMsg.getUserName());
//        logger.info("用户信息缓存成功:{},{},{}", userInfo.getUserName(), userInfo.getServerId(), userInfo.getUserIdInGateway());
//        //通知用户
//        ;
//        OctopusPacketMessage toUserMessage = MessageFactory.createToUserPacketMessage(userInfo.getUserIdInGateway(),
//                userInfo.getServerId(), Commands.loginRet, "lcl".getBytes());
//        ClientServerInfo innerGatewayServerInfo = commandService.getRandomInnerGatewayServerInfo();
//        anyClientManager.checkConnect(innerGatewayServerInfo.getServiceName(), innerGatewayServerInfo.getServerId(),
//                innerGatewayServerInfo.getServerAddress(),
//                innerGatewayServerInfo.getServerPort(), () -> {
//                    anyClientManager.forwardMessageWithNoCheck(innerGatewayServerInfo.getServiceName(),
// innerGatewayServerInfo
//                            .getServerId(), toUserMessage);
//                });
        //anyClientManager.forwardMessageThroughInnerGateway(toUserMessage);
    }
}
