package org.sunyata.game.login.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.server.message.LogoutJsonBodySerializer;
import org.sunyata.game.server.message.OctopusRawMessage;
import org.sunyata.game.server.message.UserOfflineJsonBodySerializer;
import org.sunyata.game.service.UserCacheService;
import org.sunyata.game.service.UserLocationInfo;
import org.sunyata.game.service.UserLocationService;


/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.logout, routeModel = ServerCommand.fanoutMode)
public class LogoutEventCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(LogoutEventCommand.class);


    @Autowired
    UserCacheService userCacheService;

    @Autowired
    UserLocationService userLocationService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Autowired
    RoomDomainService roomDomainService;

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        OctopusRawMessage rawMessage = request.getMessage().getRawMessage();
        LogoutJsonBodySerializer jsonBodySerializer = (LogoutJsonBodySerializer) new LogoutJsonBodySerializer()
                .parseFrom(rawMessage.getBody());
        if (jsonBodySerializer != null) {
            //发送离线事件
            UserLocationInfo userLocationInfo = userLocationService.getUserLocationInfo(jsonBodySerializer
                    .getGatewayServerId(), jsonBodySerializer
                    .getUserInGatewayId());
            if (userLocationInfo != null) {
                anyClientManager.sendSysMessageByInnerGateway(Commands.userOffline, new UserOfflineJsonBodySerializer()
                        .setUserId(userLocationInfo.getUserId()));
                userLocationService.removeCacheUserLocation(jsonBodySerializer.getGatewayServerId(), jsonBodySerializer
                        .getUserInGatewayId());
                userCacheService.removeCacheUserInfo(jsonBodySerializer.getGatewayServerId(), jsonBodySerializer
                        .getUserInGatewayId());
            }
        }
    }
}
