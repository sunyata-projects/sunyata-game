package client.handlers;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.server.message.OctopusToUserRawMessage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.errorCommand)
public class ErrorCommandRet implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(ErrorCommandRet.class);

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        int code = request.getCode();
        logger.info("code:{}", code);
    }
}
