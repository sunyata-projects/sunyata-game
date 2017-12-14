package org.sunyata.game.test.server.commmands;

import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.AbstractCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunyata.game.contract.Commands;

/**
 * Created by leo on 17/11/1.
 */
@ServerCommand(commandId = Commands.testCommand, routeModel = ServerCommand.lbMode)
public class TestCommand extends AbstractCommandHandler {
    Logger logger = LoggerFactory.getLogger(TestCommand.class);

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        logger.info("TestServer receieve Message {}", request.getMessage().getRawMessage().getCmd());
    }

//    @Override
//    public String getGatewayServiceName() {
//        return null;
//    }
}
