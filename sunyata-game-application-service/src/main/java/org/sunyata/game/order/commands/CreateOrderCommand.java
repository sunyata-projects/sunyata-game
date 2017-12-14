package org.sunyata.game.order.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.order.services.OrderDomainService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.service.UserCacheService;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.createOrder, routeModel = ServerCommand.lbMode)
public class CreateOrderCommand extends AbstractCommandHandler {

    Logger logger = LoggerFactory.getLogger(CreateOrderCommand.class);


    @Autowired
    ServerConfigProperties serverConfigProperties;
    @Autowired
    AnyClientManager anyClientManager;

    @Autowired
    CommandService commandService;

    @Autowired
    OrderDomainService orderDomainService;
    @Autowired
    UserCacheService userCacheService;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {

    }



    public boolean auth(String userName, String password) {
        return true;
    }
}
