//package org.sunyata.game.test.client.boss;
//
//import org.sunyata.game.server.PxClient;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ExecutionException;
//
///**
// *
// */
//@Component
//public class BossClient {
//    public static final int THREAND_NUMS = 1;
//    private PxClient pxClient;
//    private String bossAddress;
//    private int bossPort;
//    private BossClientHandler bossClientHandler;
//
//    public String getBossAddress() {
//        return bossAddress;
//    }
//
//    public BossClient setBossAddress(String bossAddress) {
//        this.bossAddress = bossAddress;
//        return this;
//    }
//
//    public int getBossPort() {
//        return bossPort;
//    }
//
//    public BossClient setBossPort(int bossPort) {
//        this.bossPort = bossPort;
//        return this;
//    }
//
//    public BossClientHandler getBossClientHandler() {
//        return bossClientHandler;
//    }
//
//    public BossClient setBossClientHandler(BossClientHandler bossClientHandler) {
//        this.bossClientHandler = bossClientHandler;
//        return this;
//    }
//
//    public BossClient() throws Exception {
//
//    }
//
//
//    public void connect() throws Exception {
//        pxClient.connectRetry();
//    }
//
//    public void close() throws ExecutionException, InterruptedException {
//        pxClient.close();
//    }
//
//    public void writeAndFlush(Object msg) {
//        pxClient.writeAndFlush(msg);
//    }
//}
