package org.sunyata.game.test.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.sunyata.game.test.server.test.TestListenHandler;
import org.sunyata.game.test.server.test.TestListenServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 17/4/19.
 */
@Component
@Configuration
public class ApplicationBootStrap implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${server.port}")
    Integer port;

    @Autowired
    TestListenServer server;
    @Autowired
    TestListenHandler testListenHandler;


    @PostConstruct
    public void start() throws Exception {
        server.setPort(port + 1);
        server.setMessageHandler(testListenHandler);
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
