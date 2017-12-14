package client.handlers;

import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.test.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.testCommand2Ret)
public class Test2Ret implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(Test2Ret.class);

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        logger.info("currentValue:{}", atomicInteger.incrementAndGet());
        Test.Test2RetReq test2RetReq = Test.Test2RetReq.parseFrom(request.getBody());
        logger.info("name:{}", test2RetReq.getName());
    }
}
