package client.handlers;

import com.googlecode.protobuf.format.JsonFormat;
import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.contract.protobuf.room.Room;

import java.util.Objects;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.majiangChapterInfo)
public class SyncOpt implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(SyncOpt.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        if (request.getCode() == 0) {
            Room.SyncOpt majiangChapterInfo = Room.SyncOpt.parseFrom(request.getBody());
            if (UserManager.getCurrentLocationIndex() == majiangChapterInfo.getIndex()) {
                if (Objects.equals(majiangChapterInfo.getOpt(), OperationNames.OPT_FA)) {

                }
            }
//            ByteBuf buffer = Unpooled.buffer();
//            Room.JoinGameReq.Builder createRoomReq = Room.JoinGameReq.newBuilder();
//            byte[] bytes = createRoomReq.build().toByteArray();
//            int length = bytes.length;
//            buffer.writeInt(Integer.parseInt(Commands.joinGame)).writeInt(2323232).writeFloat(1.0f)
//                    .writeInt(-1)
//                    .writeInt(length)
//                    .writeBytes
//                            (bytes);
//            WebSocketFrame frame = new BinaryWebSocketFrame(buffer);
//            response.writeAndFlush(frame);
//            private String userName;
//            private String avatar;
//            /**
//             * 0:女生,1:男生,2:未知
//             */
//            private int sex;
//            private int gold;
//            private int score;
//            private int locationIndex;
//            private int userId;
//            private boolean online;
//            private String ip;
//            String encode = Json.encode(majiangChapterInfo);
            logger.info("收到同步操作信息:{}", JsonFormat.printToString(majiangChapterInfo));
        }
    }
}
