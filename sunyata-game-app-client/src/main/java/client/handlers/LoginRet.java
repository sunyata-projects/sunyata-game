package client.handlers;

import com.googlecode.protobuf.format.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.common.Common;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.OctopusToUserRawMessage;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.loginRet)
public class LoginRet implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(LoginRet.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        if (request.getCode() == 0) {
            Common.LoginResponseMsg loginResponseMsg = Common.LoginResponseMsg.parseFrom(request.getBody());
            logger.info("登录成功:{}", JsonFormat.printToString(loginResponseMsg));
            UserManager.setCurrentUserId(loginResponseMsg.getId());
            String roomCheckId = loginResponseMsg.getRoomCheckId();

            ByteBuf buffer = Unpooled.buffer();
            Room.JoinRoomReq.Builder req = Room.JoinRoomReq.newBuilder();
            if (roomCheckId != null && StringUtils.isNotEmpty(roomCheckId)) {
                req.setRoomCheckId(roomCheckId);
                byte[] bytes = req.build().toByteArray();
                int length = bytes.length;
                buffer.writeInt(Integer.parseInt(Commands.joinRoom)).writeInt(2323232).writeFloat(1.0f)
                        .writeInt(-1)
                        .writeInt(length)
                        .writeBytes
                                (bytes);
                WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                response.writeAndFlush(frame);
            }
        }
    }
}
