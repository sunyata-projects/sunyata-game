package client;

import client.handlers.*;
import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;

import java.util.Objects;

/**
 * Created by leo on 17/11/8.
 */
@Component
public class CommandMessageManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    Logger logger = LoggerFactory.getLogger(CommandMessageManager.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

//        ObjectPool.NonBlocking()
        this.applicationContext = applicationContext;
    }


    public void handler(OctopusToUserRawMessage request, ChannelHandlerContext ctx) throws Exception {
        //Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
//        Object bean = applicationContext.getBean(String.valueOf(request.getCmd()));
//        if (bean == null) {
//            logger.error("命令没有被实现:CommandId:{}", request.getCmd());
//        }
        String cmd = String.valueOf(request.getCmd());

        if (Objects.equals(cmd, Commands.loginRet)) {
            new LoginRet().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.joinRoomRet)) {
            new JoinRoomRet().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.gameRoomInfo)) {
            new GameRoomInfo().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.gameUserInfo)) {
            new GameUserInfo().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.majiangChapterInfo)) {
            new MajiangChapterInfo().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.optFaPai)) {
            new OptFaPai().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.optCPGH)) {
            new OptCPGH().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.tingPai)) {
            new TingPai().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.syncOpt)) {
            new SyncOpt().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.syncOptTime)) {
            new SyncOptTime().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.testCommandRet)) {
            new TestRet().run(request, ctx);
        } else if (Objects.equals(cmd, Commands.errorCommand)) {
            new ErrorCommandRet().run(request, ctx);
        } else {
            logger.info("命令没有被处理command:{},errCode:{}", request.getCmd(), request.getCode());
        }


//        ClientCommandHandler commandHandler = (ClientCommandHandler) bean;
//        assert commandHandler != null;
//        commandHandler.run(request, ctx);
        logger.info("command:{},errCode:{}", request.getCmd(), request.getCode());


    }
}
