package client.handlers;

import com.googlecode.protobuf.format.JsonFormat;
import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.tingPai)
public class TingPai implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(TingPai.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        byte[] body = request.getBody();
        if (body != null) {
            Room.TingPai tingPai = Room.TingPai.parseFrom(request.getBody());
            logger.info("收到听牌信息:{}", JsonFormat.printToString(tingPai));
        }
        if (request.getCode() == 0) {

        }
    }
}