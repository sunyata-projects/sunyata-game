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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.optFaPai)
public class OptFaPai implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(OptFaPai.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        Room.OperationCPGH operationFaPai = Room.OperationCPGH.parseFrom(request.getBody());


        logger.info("收到发牌信息:{}", JsonFormat.printToString(operationFaPai));

        if (request.getCode() == 0) {
            if (UserManager.getCurrentLocationIndex() == operationFaPai.getIndex()) {
                List<Room.OperationInfo> operationInfoList = operationFaPai.getOperationInfoList();
                Room.OperationFaPaiRetReq.Builder builder = Room.OperationFaPaiRetReq.newBuilder();
                builder.setPai(operationFaPai.getPai());
                if (operationInfoList.size() > 0) {
                    Room.OperationInfo operationInfo = operationInfoList.get(0);
                    if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_AN_GANG)) {
                        builder.setOpt(OperationNames.OPT_AN_GANG);
                        Utils.sendMessage(Commands.optFaPaiRet, builder.build(), response);
                        logger.info("客户端暗杠:{}", JsonFormat.printToString(operationInfo));
                    } else if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_XIAO_MING_GANG)) {
                        builder.setOpt(OperationNames.OPT_XIAO_MING_GANG);
                        Utils.sendMessage(Commands.optFaPaiRet, builder.build(), response);
                        logger.info("客户端小明杠:{}", JsonFormat.printToString(operationInfo));
                    } else if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_HU)) {
                        builder.setOpt(OperationNames.OPT_HU);
                        Utils.sendMessage(Commands.optFaPaiRet, builder.build(), response);
                        logger.info("客户端胡牌:{}", JsonFormat.printToString(operationInfo));
                    } else {
                        logger.info("客户端未能识别操作类型:{}", JsonFormat.printToString(operationInfo));
                    }
                } else {
                    builder.setOpt(OperationNames.OPT_OUT);
                    Room.OperationFaPaiRetReq build = builder.build();
                    Executors.newCachedThreadPool().submit(() -> {
                        //try {
//                            //Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        try {
                            Utils.sendMessage(Commands.optFaPaiRet, build, response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    logger.info("客户端打牌:{}", JsonFormat.printToString(build));
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
        }
    }
}
