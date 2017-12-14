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

import java.util.List;
import java.util.Objects;

/**
 * Created by leo on 17/11/24.
 */
@Component(Commands.optCPGH)
public class OptCPGH implements ClientCommandHandler {
    Logger logger = LoggerFactory.getLogger(OptCPGH.class);

    @Override
    public void run(OctopusToUserRawMessage request, ChannelHandlerContext response) throws Exception {
        Room.OperationCPGH operationFaPai = Room.OperationCPGH.parseFrom(request.getBody());
        logger.info("别人打牌,自己可以吃碰杠和:{}", JsonFormat.printToString(operationFaPai));

        if (request.getCode() == 0) {

            logger.info("收到发牌信息:{}", JsonFormat.printToString(operationFaPai));
            if (UserManager.getCurrentLocationIndex() == operationFaPai.getIndex()) {
                List<Room.OperationInfo> operationInfoList = operationFaPai.getOperationInfoList();
                Room.OperationFaPaiRetReq.Builder builder = Room.OperationFaPaiRetReq.newBuilder();
                builder.setPai(operationFaPai.getPai());
                if (operationInfoList.size() > 0) {
                    Room.OperationInfo operationInfo = operationInfoList.get(0);
                    if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_CHI)) {
                        builder.setOpt(OperationNames.OPT_CHI);
                        Utils.sendMessage(Commands.optCPGHRet, builder.build(), response);
                        logger.info("别人打牌我来吃:{}", JsonFormat.printToString(operationInfo));
                    } else if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_DA_MING_GANG)) {
                        builder.setOpt(OperationNames.OPT_DA_MING_GANG);
                        Utils.sendMessage(Commands.optCPGHRet, builder.build(), response);
                        logger.info("别人打牌我来大明杠:{}", JsonFormat.printToString(operationInfo));
                    } else if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_PENG)) {
                        builder.setOpt(OperationNames.OPT_PENG);
                        Utils.sendMessage(Commands.optCPGHRet, builder.build(), response);
                        logger.info("别人打牌我来碰:{}", JsonFormat.printToString(operationInfo));
                    } else if (Objects.equals(operationInfo.getOpt(), OperationNames.OPT_HU)) {
                        builder.setOpt(OperationNames.OPT_HU);
                        Utils.sendMessage(Commands.optCPGHRet, builder.build(), response);
                        logger.info("别人打牌我来和:{}", JsonFormat.printToString(operationInfo));
                    } else {
                        builder.setOpt(OperationNames.OPT_GUO);
                        Utils.sendMessage(Commands.optCPGHRet, builder.build(), response);
                        logger.info("别人打牌我来过:{}", JsonFormat.printToString(operationInfo));
                    }
                } else {
                    logger.info("发来的吃碰和牌信息为空");
                }
            }
        }
    }
}
