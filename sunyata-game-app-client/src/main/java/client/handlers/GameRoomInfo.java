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

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.gameRoomInfo)
public class GameRoomInfo implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(GameRoomInfo.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        if (request.getCode() == 0) {
            Room.GameRoomInfo gameRoomInfo = Room.GameRoomInfo.parseFrom(request.getBody());
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
//            String encode = Json.encode(gameUserInfo);
            logger.info("收到房间信息通知:gameRoomInfo:{}", JsonFormat.printToString(gameRoomInfo));
//            Room.GameUserInfo gameUserInfo = gameRoomInfo.getSceneUserList().stream().filter(p -> p.getUserId() ==
//                    UserManager.getCurrentUserId())
//                    .findFirst().orElse(null);

//            if (gameRoomInfo == null) {
//                return;
//            }
            List<Room.GameUserInfo> sceneUserList = gameRoomInfo.getSceneUserList();
            Room.GameUserInfo gameUserInfo = sceneUserList.stream().filter(p -> p.getUserId() == UserManager
                    .getCurrentUserId()).findFirst().orElse(null);
            if (gameRoomInfo != null) {
                UserManager.setCurrentLocationIndex(gameUserInfo.getLocationIndex());
            }
            Room.MajiangChapter chapter = gameRoomInfo.getChapter();
            if (chapter != null && gameRoomInfo.getStart()) {
                int currentLocationIndex = chapter.getCurrentIndex();
                if (currentLocationIndex != UserManager.getCurrentLocationIndex()) {
                    return;
                }
                Room.OperationCPGH optFaPai = chapter.getOptFaPai();
                if (optFaPai != null && optFaPai.getOperationInfoList().size() > 0) {
                    test(optFaPai, Commands.optFaPaiRet, response);
                }

                Room.OperationCPGH optCPGH = chapter.getOptCPGH();
                if (optCPGH != null && optCPGH.getOperationInfoList().size() > 0) {
                    test(optCPGH, Commands.optCPGHRet, response);
                }
            }
        }
    }

    public void test(Room.OperationCPGH operationInfos, String targetCommandName, ChannelHandlerContext response) throws IOException {
        Room.OperationFaPaiRetReq.Builder builder = Room.OperationFaPaiRetReq.newBuilder();
        builder.setPai(operationInfos.getPai());
        for (Room.OperationInfo info : operationInfos.getOperationInfoList()) {

            if (info.getOpt().equals(OperationNames.OPT_HU)) {
                builder.setOpt(OperationNames.OPT_HU);
                logger.info("客户端和:{}", JsonFormat.printToString(operationInfos));
            } else if (info.getOpt().equals(OperationNames.OPT_AN_GANG)) {
                builder.setOpt(OperationNames.OPT_AN_GANG);
                logger.info("客户端暗杠杠:{}", JsonFormat.printToString(operationInfos));
            } else if (info.getOpt().equals(OperationNames.OPT_XIAO_MING_GANG)) {
                builder.setOpt(OperationNames.OPT_XIAO_MING_GANG);
                logger.info("客户端小明杠:{}", JsonFormat.printToString(operationInfos));
            } else {//否则随便出一张牌
                builder.setOpt(OperationNames.OPT_OUT);
                logger.info("客户端打牌:{}", JsonFormat.printToString(operationInfos));
            }
            Utils.sendMessage(targetCommandName, builder.build(), response);
            break;
        }

    }
}
