package org.sunyata.game.test.server.test;

import org.sunyata.game.server.OctopusServer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author leo on 16/9/27.
 */
@Component
public class TestListenServer {
    public static final int BOSS_THREAD_NUMS = 2;
    public static final int WORKER_THREAD_NUMS = 4;

    private int port;

    private TestListenHandler messageHandler;
    private OctopusServer octopusServer;

    public void start() throws Exception {
        octopusServer = OctopusServer.createOctopusServer(port, messageHandler, BOSS_THREAD_NUMS, WORKER_THREAD_NUMS);
        octopusServer.start();
    }

    public void close() throws ExecutionException, InterruptedException {
        if(octopusServer != null){
            octopusServer.close();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMessageHandler(TestListenHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
