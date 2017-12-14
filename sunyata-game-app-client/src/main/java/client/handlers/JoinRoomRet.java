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
@Component(Commands.joinRoomRet)
public class JoinRoomRet implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(GameUserInfo.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        if (request.getCode() == 0) {
            Room.JoinRoomRes joinRoomRes = Room.JoinRoomRes.parseFrom(request.getBody());
            logger.info("加入房间成功信息:{}", JsonFormat.printToString(joinRoomRes));
//            ByteBuf buffer = Unpooled.buffer();
            int destServerId = joinRoomRes.getDestServerId();
            UserManager.setRoomDestServerId(destServerId);
            Room.JoinGameReq.Builder joinGameReq = Room.JoinGameReq.newBuilder();
//            byte[] bytes = createRoomReq.build().toByteArray();
//            int length = bytes.length;
//            buffer.writeInt(Integer.parseInt(Commands.joinGame)).writeInt(2323232).writeFloat(1.0f)
//                    .writeInt(-1)
//                    .writeInt(length)
//                    .writeBytes
//                            (bytes);
//            WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
            Utils.sendMessage(Commands.joinGame, joinGameReq.build(), response);
        }
    }
}
