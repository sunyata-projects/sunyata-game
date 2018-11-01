package org.sunyata.game.command;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.service.UserLocationService;

/**
 * Created by leo on 17/4/28.
 */
public abstract class AbstractCommandHandler implements CommandHandler {
    @Autowired
    protected AnyClientManager anyClientManager;
    @Autowired
    protected CommandService commandService;
    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected UserLocationService userLocationService;


    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return false;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {

    }

    @Override
    public final void run(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
//        String userName = request.getContext().channel().attr(Login.KEY_USER_ID).get();
//        if (!StringUtils.isEmpty(userName)) {
//            Session session = SessionManager.getSession(userName);
//            request.setSession(session);
//        }
        if (onExecuteBefore(request, response)) {
            execute(request, response);
        }
        //else {
//            response.writeAndFlush();
//        }
    }

//    public abstract String getGatewayServiceName();

    public void sendErrorMessageToUser(int userIdInGateway, int destServerId, int errorCode) throws
            Exception {
//        ClientServerInfo descClientServerInfo = commandService.getDescClientServerInfo(sourceServerId,
//                getGatewayServiceName());
//        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserErrorPacketMessage(userIdInGateway,
//                sourceServerId, destServerId, errorCode);
//        anyClientManager.forwardMessage(descClientServerInfo, toUserPacketMessage);
        anyClientManager.sendErrorMessageToUser(userIdInGateway,destServerId,errorCode);
    }

    public void sendMessageToUser(int userIdInGateway, int destServerId, String commandId, byte[]
            bytes) throws
            Exception {
//        ClientServerInfo descClientServerInfo = commandService.getDescClientServerInfo(destServerId,
//                getGatewayServiceName());
//        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserPacketMessage(userIdInGateway,
//                sourceServerId, destServerId, commandId, bytes);
//        anyClientManager.forwardMessage(descClientServerInfo, toUserPacketMessage);
        anyClientManager.sendMessageToUserByInnerGatewayServer(userIdInGateway,destServerId,commandId,bytes);
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
