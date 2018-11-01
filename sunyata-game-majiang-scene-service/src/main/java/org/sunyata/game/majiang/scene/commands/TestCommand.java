package org.sunyata.game.majiang.scene.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sunyata.game.ServerCommand;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.command.AbstractCommandHandler;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.majiang.core.service.RoomDomainService;
import org.sunyata.game.majiang.core.service.RoomLockKeys;
import org.sunyata.game.majiang.core.service.RoomService;
import org.sunyata.game.server.OctopusPacketRequest;
import org.sunyata.game.server.OctopusPacketResponse;
import org.sunyata.game.state.manager.config.RandomUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 17/11/7.
 */
@ServerCommand(commandId = Commands.testCommand, routeModel = ServerCommand.lbMode)
public class TestCommand extends AbstractCommandHandler implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(TestCommand.class);


    @Autowired
    RoomDomainService roomDomainService;
    @Autowired
    RoomLockKeys roomLockKeys;
    @Autowired
    LockService lockService;

    @Autowired
    RoomService roomService;
    @Autowired
    AnyClientManager anyClientManager;
    private ApplicationContext applicationContext;

    @Override
    public boolean onExecuteBefore(OctopusPacketRequest request, OctopusPacketResponse response) {
        return true;
    }

    ExecutorService executorService;

    public TestCommand() {
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void execute(OctopusPacketRequest request, OctopusPacketResponse response) throws Exception {
        int userIdInGateway = request.getMessage().getDataId();
        int gatewayServerId = request.getMessage().getSourceServerId();
//        Test.TestReq testReq = Test.TestReq.parseFrom(request.getMessage().getRawMessage().getBody());
        //logger.info("TestCommand:name:{},id:{}", testReq.getName(), testReq.getId());
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    int i1 = RandomUtils.nextInt(1000);
                    String randomString = RandomUtils.createRandomString(i1);
                    testRec(userIdInGateway, gatewayServerId, finalI + "-----" + randomString);
                    return null;
                }
            });
            //  });

//            executorService.submit(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    test2Rec(userIdInGateway, gatewayServerId);
//                    return null;
//                }
//            });


        }
    }

    public void testRec(int userIdInGateway, int gatewayServerId, String i) throws Exception {
        //for (int i = 0; i < 100; i++) {
        //Test.Test2RetReq.Builder builder = Test.Creat.newBuilder();
        Room.CreateRoomRes.Builder builder = Room.CreateRoomRes.newBuilder();
//        int i = RandomUtils.nextInt(1000);
        Room.CreateRoomRes.Builder builder1 = builder.setRoomCheckId(i);
        anyClientManager.sendMessageToUserByInnerGatewayServer(userIdInGateway, gatewayServerId, Commands.testCommandRet, builder1
                .build().toByteArray());
        //}
    }

    //    public void test2Rec(int userIdInGateway, int gatewayServerId) throws Exception {
//        //for (int i = 0; i < 100; i++) {
//        Test.TestRetReq.Builder builder = Test.TestRetReq.newBuilder();
//        Test.TestRetReq.Builder builder1 = builder.setId(2121).setName("dslfsdkfjlsdf");
//        anyClientManager.sendMessageToUserByInnerGatewayServer(userIdInGateway, gatewayServerId, Commands.testCommandRet, builder1
//                .build().toByteArray());
//        //}
//    }
//
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean auth(String userName, String password) {
        return true;
    }
}
