package org.sunyata.game.test.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.server.message.OctopusInRawMessage;
import org.sunyata.game.server.message.OctopusRawMessage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class ApplicationBootStrap implements ApplicationContextAware {


    private ApplicationContext applicationContext;


    @Autowired
    TestClient client;
    @Autowired
    TestClientHandler testClientHandler;



    @PostConstruct
    public void start() throws Exception {
//        handler = new GameServerHandler();
//        server = new GameServer();
//        server.setBossThreadNums(4);
//        server.setPort(57002);
//        server.setWebSocket(true);
//        server.setWorkerThreadNums(8);
//        server.setZipSize(256);
//        server.setEncryptValue(11);
//        server.setHandler(handler);
//        server.init();
//        TestListenServer server = new TestListenServer();
        //client.start("127.0.0.1", 57002, testClientHandler);
//
//        boxxClient.setBossAddress("127.0.0.1").setBossPort(57002).setBossClientHandler(bossClientHandler);
//        boxxClient.init();
//        boxxClient.connect();


        client.setBossAddress("127.0.0.1").setBossPort(57002).setBossClientHandler(testClientHandler);
        client.init();
        client.connect();

        OctopusRawMessage msg = new OctopusInRawMessage();
        byte[] bytes = "lcl".getBytes();
        msg.setCmd(15001);
        msg.setSerial(2323232);
        msg.setVersion(1.0f);
//        msg.setLength(bytes.length);
        msg.setBody(bytes);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(55003).writeInt(2323232).writeFloat(1.0f).writeInt(bytes.length).writeBytes
                (bytes);
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        client.writeAndFlush(msg);

        //Thread.sleep(10000);
//        OctopusPacketRawMessage msg = new OctopusPacketRawMessage();
//        byte[] bytes = "lcl".getBytes();
//        msg.setCmd(15001);
//        msg.setSerial(2323232);
//        msg.setVersion(1.0f);
//        msg.setLength(bytes.length);
//        msg.setBody(bytes);
//        client.writeAndFlush(msg);
    }


    @PreDestroy
    public void stop() throws ExecutionException, InterruptedException {
        client.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
