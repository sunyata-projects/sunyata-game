package org.sunyata.game.login.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.ErrorCodes;
import org.sunyata.game.contract.protobuf.common.Common;
import org.sunyata.game.exceptions.UserAndPasswordIsNotRightException;
import org.sunyata.game.login.UserLoginRetInfo;
import org.sunyata.game.login.UserService;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.service.UserCacheInfo;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;
import org.sunyata.game.store.user.UserDao;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.login, routeModel = ServerCommand.lbMode)
public class LoginCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LoginCommand.class);


    @Autowired
    ServerConfigProperties serverConfigProperties;
    @Autowired
    AnyClientManager anyClientManager;

    @Autowired
    CommandService commandService;

    @Autowired
    UserService userService;
    @Autowired
    UserCacheService userCacheService;

    @Autowired
    UserLocationService userLocationService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Autowired
    UserDao userDao;
    @Autowired
    RoomDomainService roomDomainService;

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        //OctopusPacketUserMessage userMessage = (OctopusPacketUserMessage) request.getMessage();
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        Common.LoginRequestMsg loginRequest = Common.LoginRequestMsg.parseFrom(rawMessage.getBody());
        int userInGatewayId = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
        String openId = loginRequest.getOpenId();
        String code = loginRequest.getCode();
        int type = loginRequest.getType();
        UserLoginRetInfo loginRetInfo = null;
        try {
            loginRetInfo = userService.loginByOpenId(type, openId, code);
        } catch (UserAndPasswordIsNotRightException ex) {
            anyClientManager.sendErrorMessageToUser(userInGatewayId, gatewayServerId, Integer.parseInt(ErrorCodes
                    .userAndPasswordIncorrect));
            return;
        }
        if (loginRetInfo != null) {
            UserLocationInfo userLocationInfo = new UserLocationInfo()
                    .setUserId(loginRetInfo.getId()).setUserIdInGateway(request.getMessage().getDataId())
                    .setUserName(loginRetInfo.getName())
                    .setOpenId(loginRetInfo.getOpenId())
                    .setServerId(request.getMessage().getSourceServerId());
            UserCacheInfo userCacheInfo = new UserCacheInfo();
            BeanUtils.copyProperties(loginRetInfo.getUserEntity(), userCacheInfo);
            userLocationService.cacheUserLocation(userLocationInfo);
            userCacheService.cacheUserInfo(userCacheInfo);
            String roomCheckId = roomDomainService.loadUnEndRoomCheckId(loginRetInfo.getId());
            //发送用户成功消息
            Common.LoginResponseMsg.Builder builder = Common.LoginResponseMsg.newBuilder();
            builder.setId(loginRetInfo.getId())
                    .setName(loginRetInfo.getName())
                    .setOpenId(loginRetInfo.getOpenId())
                    .setUuid(loginRetInfo.getUuid())
                    .setAvatar(loginRetInfo.getAvatar())
                    .setSex(loginRetInfo.getSex())
                    .setRoomCheckId(loginRetInfo.getRoomCheckId())
                    .setGold(loginRetInfo.getGold())
                    .setLoginToken(loginRetInfo.getLoginToken())
                    .setIp(loginRetInfo.getIp());
            if (roomCheckId != null) {
                builder.setRoomCheckId(roomCheckId);
            }
            Common.LoginResponseMsg loginResponseMsg = builder.build();
//            OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserPacketMessage(
//                    request.getMessage().getDataId(),
//                    serverConfigProperties.getServerId(),
//                    request.getMessage().getSourceServerId(),
//                    Commands.loginRet,
//                    loginResponseMsg.toByteArray());
            //anyClientManager.forwardMessageThroughInnerGateway(toUserPacketMessage);
            anyClientManager.sendMessageToUser(request.getMessage().getDataId(), request.getMessage()
                    .getSourceServerId(), Commands.loginRet, loginResponseMsg.toByteArray());

        }
//        OctopusPacketMessage toSystemMessage = MessageFactory.createToSystemPacketJsonBodyMessage(-1,
// serverConfigProperties
//                .getServerId(), Commands.loginSuccessEvent, new LoginSuccessEventInfo().setUserIdInGateway(request
// .getMessage()
//                .getDataId()).setGatewayServerId(request.getMessage().getSourceServerId()).setUserName(openId));
        //anyClientManager.forwardMessageThroughInnerGateway(toSystemMessage);
    }



    public boolean auth(String userName, String password) {
        return true;
    }
}
