package org.sunyata.game.majiang.scene;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 17/4/19.
 */
@Component
@Configuration
//@ComponentScan("org.sunyata.game.majiang.core.mapper")
public class ApplicationBootStrap implements ApplicationContextAware {


    @Value("${server.port}")
    private Integer port;
    private ApplicationContext applicationContext;


    @Autowired
    SceneListenServer server;
    @Autowired
    SceneListenHandler sceneListenHandler;


    @PostConstruct
    public void start() throws Exception {
        server.setPort(port + 1);
        server.setMessageHandler(sceneListenHandler);
        server.start();
    }


    @PreDestroy
    public void stop() throws ExecutionException, InterruptedException {
        server.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
