package org.sunyata.game.state.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.command.CommandService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by leo on 17/11/1.
 */
@Configuration
@ComponentScan("org.sunyata.game.state")
@ConditionalOnProperty(value = "octopus.command.enabled", matchIfMissing = false)
//@EnableConfigurationProperties({ServerConfigProperties.class})
public class CommandStateConfig implements ApplicationContextAware {

    //public static final String eventKeyPrefix = "octopus_event_";
    Logger logger = LoggerFactory.getLogger(CommandStateConfig.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    public static String commandCacheKey = "octopus.command.key";
    @Value("${spring.application.name}")
    public String serviceName;

    @Autowired
    ServerConfigProperties serverConfigProperties;

    @Autowired
    CommandService commandService;

    @PreDestroy
    public void stop() {
        commandService.stop();
    }

    @PostConstruct
    public void start() throws Exception {
        commandService.start();
//        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
//        for (Object obj : beansWithAnnotation.values()) {
//            ServerCommand annotation = obj.getClass().getAnnotation(ServerCommand.class);
//            ServerCommandInfo info = new ServerCommandInfo()
//                    .setCommandId(annotation.commandId())
//                    .setCommandName(annotation.commandName())
//                    .setServiceName(serviceName)
//                    .setRouteMode(annotation.routeModel())
//                    //.setLoadBalanceModel(annotation.loadBalanceModel())
//                    .setDescription(annotation.description());
////                    .setEvent(annotation.isEvent())
////                    .setEventModel(annotation.eventModel());
//            //if (annotation.isEvent()) {
//            redisTemplate.opsForHash().put(eventKeyPrefix + annotation.commandId(), serverConfigProperties
// .getServerId(),
//                    info);
        //redisTemplate.opsForSet().add(eventKeyPrefix + annotation.commandId(), info);
//            } else {
//
//                Object o = redisTemplate.opsForHash().get(commandCacheKey, info.getCommandId());
//                if (o != null) {
//                    ServerCommandInfo remoteInfo = (ServerCommandInfo) o;
//                    if (remoteInfo != null) {
//                        if (!remoteInfo.getServiceName().equals(info.getServiceName())) {
//                            logger.error("duplicated commandId {},serviceName:{},{}", info.getCommandId(), info
//                                    .getServiceName(), remoteInfo.getServiceName());
//                            throw new Exception("duplicated commandId");
//                        }
//                    }
//                }
////                redisTemplate.opsForSet().add("octopus_event_" + info.getCommandId(), info);
//                redisTemplate.opsForHash().put(commandCacheKey, info.getCommandId(), info);
//            }
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
