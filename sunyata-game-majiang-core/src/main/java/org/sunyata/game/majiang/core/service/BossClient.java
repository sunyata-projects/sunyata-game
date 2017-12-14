package org.sunyata.game.majiang.core.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 *
 */

@Component
public class BossClient {
    private static final int THREAND_NUMS = 1;

    //private final OctopusClient pxClient;

//    public BossClient(String bossAddress, int bossPort, BossClientHandler bossClientHandler) throws Exception {
//        pxClient = OctopusClient.create(new ScenePxMsgFactory(), bossAddress, bossPort, bossClientHandler, THREAND_NUMS);
//    }

    public void connect() throws Exception {
        //pxClient.connectRetry();
    }

    public void close() throws ExecutionException, InterruptedException {
        //pxClient.close();
    }

    public void writeAndFlush(Object msg) {
        //pxClient.writeAndFlush(msg)
        //;
    }
}
