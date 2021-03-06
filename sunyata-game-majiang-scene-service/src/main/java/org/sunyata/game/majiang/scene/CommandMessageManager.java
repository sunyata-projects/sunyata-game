package org.sunyata.game.majiang.scene;

import org.sunyata.game.MessageProcessor;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.command.CommandHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by leo on 17/11/8.
 */
@Component("majiang-scene-message-processor")
public class CommandMessageManager implements MessageProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Override
    public void handler(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
        Object o = beansWithAnnotation.values().stream().filter(p -> {
            ServerCommand annotation = p.getClass().getAnnotation(ServerCommand.class);
            if (annotation.commandId().equals(String.valueOf(request.getMessage().getRawMessage().getCmd()))) {
                return true;
            }
            return false;
        }).findFirst().orElse(null);
        CommandHandler commandHandler = (CommandHandler) o;
        if (commandHandler != null) {
            commandHandler.run(request, response);
        }
    }
}
