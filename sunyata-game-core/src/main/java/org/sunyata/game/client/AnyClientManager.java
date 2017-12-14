package org.sunyata.game.client;

import com.google.common.collect.ArrayListMultimap;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.ServerConfigProperties;
import org.sunyata.game.command.CommandService;
import org.sunyata.game.server.message.JsonBodySerializer;
import org.sunyata.game.server.message.MessageFactory;
import org.sunyata.game.server.message.OctopusPacketMessage;
import org.sunyata.game.service.ClientServerInfo;
import org.sunyata.game.service.ServerLocation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@Component
public final class AnyClientManager implements ApplicationContextAware {
    private static final int FRAME_TIME_SPAN = 2000;
    private static final int RUN_QUEUE_MAX = 1024;

    private static final Logger logger = LoggerFactory.getLogger(AnyClientManager.class);

    private ApplicationContext applicationContext;
    private final ConcurrentHashMap<String, AnyClient> map = new ConcurrentHashMap<>();
    private final ArrayListMultimap<String, OctopusPacketMessage> mapCallbacks = ArrayListMultimap.create();

    @Autowired
    @Qualifier("defaultCommandService")
    CommandService commandService;
    @Autowired
    ServerLocation serverLocation;

    @Autowired
    ServerConfigProperties serverConfigProperties;
    KeyedObjectPool<ClientServerInfo, AnyClient> objectPool = null;

    @Autowired
    public AnyClientManager(AnyClientFactory anyClientFactory) {
        ///super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
        GenericKeyedObjectPoolConfig genericKeyedObjectPoolConfig = new GenericKeyedObjectPoolConfig();
        genericKeyedObjectPoolConfig.setMaxTotalPerKey(1);
        objectPool = new GenericKeyedObjectPool<>(anyClientFactory, genericKeyedObjectPoolConfig);
        //start();
    }
//    @Autowired
//    public AnyClientManager(MessageQueueService messageQueueService) {
//        queueService = messageQueueService;
//        doDispatch();
//    }

//    public void checkConnect(String serverName, int serverId, String address, int port, Runnable callback) throws
//            Exception {
//        String key = generateKey(serverName, serverId);
//        queueService.enQueue(() -> {
//            AnyClient client = map.get(key);
//            if (client == null) {
//                mapCallbacks.put(key, callback);
//                add(serverName, serverId, address, port);
//            } else {
//                if (client.isConnect()) {
//                    callback.run();
//                } else {
//                    mapCallbacks.put(key, callback);
//                }
//            }
//        });
//    }

    //    private void add(String serviceName, int serverId, String address, int port) {
//        try {
//            String key = generateKey(serviceName, serverId);
//            AnyClientHandler anyClientHandler = new AnyClientHandler(() -> runConnectCallback(key));
//            applicationContext.getAutowireCapableBeanFactory().autowireBean(anyClientHandler);
//            AnyClient client = new AnyClient(serviceName, serverId, address, port, anyClientHandler);
//            anyClientHandler.setClient(client);
//            client.connect();
//            map.put(key, client);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void runConnectCallback(ClientServerInfo key) throws Exception {
//
//        AnyClient client = map.get(key.toString());
//        List<OctopusPacketMessage> callbacks = mapCallbacks.get(key.toString());
//        for (int i = 0; i < callbacks.size(); i++) {
//            OctopusPacketMessage msg = callbacks.get(i);
//            forwardMessage(key, msg);
//            mapCallbacks.remove(key.toString(), msg);
//        }
//        client.setConnect(true);
//    }

//    String generateKey(String serviceName, int serverId) {
//        return serviceName + String.valueOf(serverId);
//    }

//    public void forwardMessageWithNoCheck(String serviceName, int serverId, OctopusPacketMessage msg) {
//        AnyClient client = map.get(generateKey(serviceName, serverId));
//        client.writeAndFlush(msg);
//    }

//    public void forwardMessageWithNoCheck(OctopusPacketMessage msg) {
//        Object[] objects = map.values().toArray();
//        Object object = objects[(RandomUtils.nextInt(map.size()))];
//        AnyClient anyClient = (AnyClient) object;
//        anyClient.writeAndFlush(msg);
//    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//
//    public void onDisconnect(String serviceName, int serverId) {
//        if (map.containsKey(generateKey(serviceName, serverId))) {
//            map.remove(generateKey(serviceName, serverId));
//        }
//    }
//
//    public AnyClient create(ClientServerInfo key) throws Exception {
//        AnyClientHandler anyClientHandler = new AnyClientHandler(() -> {
//            try {
//                runConnectCallback(key);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        applicationContext.getAutowireCapableBeanFactory().autowireBean(anyClientHandler);
//        AnyClient client = new AnyClient(key, anyClientHandler);
//        anyClientHandler.setClient(client);
////        int i = atomicInteger.incrementAndGet();
////        logger.info("anyClient实例数量:{}", i);
//        return client;
//    }

    //    public void forwardMessageWithNoCheck(String serviceName, Integer serverId, OctopusPacketMessage msg) {
//        AnyClient client = map.get(generateKey(serviceName, serverId));
//        logger.info("发送消息:CommandId:{}", msg.getRawMessage().getCmd());
//        client.writeAndFlush(msg);
//        logger.info("发送消息完毕:CommandId:{}", msg.getRawMessage().getCmd());
//    }
    //private final ReadWriteLock keyLock = new ReentrantReadWriteLock(true);

//    AnyClient getClient(ClientServerInfo key) throws Exception {
//        AnyClient client = map.get(key.toString());
//        if (client != null && client.isActive()) {
//            return client;
//        } else {
//            map.remove(key.toString());
//        }
//        Lock lock = keyLock.readLock();
//        lock.lock();
//        try {
//            AnyClient ret = map.get(key.toString());
//            if (ret == null) {
//                client = create(key);
//                map.put(key.toString(), client);
//                client.connect();
//                client.setConnect(true);
//                return client;
//            } else if (!ret.isActive()) {
//                map.remove(key.toString());
//                return null;
//            } else {
//                return ret;
//            }
//        } catch (Exception ex) {
//            map.remove(key.toString());
//            return null;
//        } finally {
//            lock.unlock();
//        }
//    }

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void forwardMessage(ClientServerInfo clientServerInfo, OctopusPacketMessage msg) throws Exception {
        if (clientServerInfo == null) {
            throw new Exception("clientServerInfo不能为空");
        }
//        run(() -> {
        AnyClient client = null;
        try {
            client = objectPool.borrowObject(clientServerInfo);
        } catch (Exception e) {
            logger.error("借取时发生错误", e);
        }
        try {
//            logger.info("发送消息:CommandId:{}", msg.getRawMessage().getCmd());
            if (client != null) {
                client.writeAndFlush(msg);
            }
//            logger.info("发送消息完毕:CommandId:{},msgCount:{}", msg.getRawMessage().getCmd(), atomicInteger
//                    .incrementAndGet());
        } finally {
            try {
                objectPool.returnObject(clientServerInfo, client);
            } catch (Exception e) {
                logger.error("规还时发生错误", e);
            }
        }
        //});
//        this.checkConnect(clientServerInfo.getServiceName(), clientServerInfo.getServerId(),
//                clientServerInfo.getServerAddress(), clientServerInfo.getServerPort(),
//                () -> {
//                    forwardMessageWithNoCheck(clientServerInfo.getServiceName(), clientServerInfo.getServerId(), msg);
//                });
    }

//    public void forwardMessageThroughInnerGateway(Runnable runnable) throws Exception {
//        ClientServerInfo randomInnerGatewayServerInfo = commandService.getRandomInnerGatewayServerInfo();
//        this.checkConnect(randomInnerGatewayServerInfo.getServiceName(), randomInnerGatewayServerInfo.getServerId(),
//                randomInnerGatewayServerInfo.getServerAddress(), randomInnerGatewayServerInfo.getServerPort(),
//                runnable);
//    }


//    public void forwardMessageThroughInnerGateway(OctopusPacketMessage toSystemMessage) throws Exception {
//        ClientServerInfo randomInnerGatewayServerInfo = commandService.getRandomInnerGatewayServerInfo();
//        this.checkConnect(randomInnerGatewayServerInfo.getServiceName(), randomInnerGatewayServerInfo.getServerId(),
//                randomInnerGatewayServerInfo.getServerAddress(), randomInnerGatewayServerInfo.getServerPort(),
//                () -> {
//                    forwardMessageWithNoCheck(randomInnerGatewayServerInfo.getServiceName(),
//                            randomInnerGatewayServerInfo.getServerId(), toSystemMessage);
//                });
//    }


    public void sendMessageToUser(int userIdInGateway, int destServerId, String commandId, byte[] bytes) throws
            Exception {
        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserPacketMessage(
                userIdInGateway,
                serverConfigProperties.getServerId(),
                destServerId,
                commandId,
                bytes);
        ClientServerInfo randomInnerGatewayServerInfo = serverLocation.getRandomInnerGatewayServerInfo();
        forwardMessage(randomInnerGatewayServerInfo, toUserPacketMessage);
    }


    public void sendErrorMessageToUser(int userIdInGateway, int destServerId, int errorCode) throws
            Exception {
        OctopusPacketMessage toUserPacketMessage = MessageFactory.createToUserErrorPacketMessage(
                userIdInGateway,
                serverConfigProperties.getServerId(),
                destServerId, errorCode);
        ClientServerInfo randomInnerGatewayServerInfo = serverLocation.getRandomInnerGatewayServerInfo();
        forwardMessage(randomInnerGatewayServerInfo, toUserPacketMessage);
    }

    public void sendSysMessage(String destServiceName, int destServerId, int userIdInGateway, String commandId,
                               JsonBodySerializer
                                       serializer) throws
            Exception {
        OctopusPacketMessage toSystemPacketJsonBodyMessage = MessageFactory.createToSystemPacketJsonBodyMessage
                (userIdInGateway, serverConfigProperties.getServerId(), commandId, serializer);
        ClientServerInfo descClientServerInfo = serverLocation.getDescClientServerInfo(destServerId, destServiceName);
//        this.checkConnect(descClientServerInfo.getServiceName(), descClientServerInfo.getServerId(),
//                descClientServerInfo.getServerAddress(), descClientServerInfo.getServerPort(),
//                () -> {
        forwardMessage(descClientServerInfo, toSystemPacketJsonBodyMessage);
        //});
    }


    public void sendSysMessageByInnerGateway(String commandId, JsonBodySerializer serializer) throws Exception {
        OctopusPacketMessage toSystemPacketJsonBodyMessage = MessageFactory.createToSystemPacketJsonBodyMessage
                (-1, serverConfigProperties.getServerId(), commandId, serializer);
        ClientServerInfo randomInnerGatewayServerInfo = serverLocation.getRandomInnerGatewayServerInfo();
        forwardMessage(randomInnerGatewayServerInfo, toSystemPacketJsonBodyMessage);
    }

//    @Override
//    protected void threadMethod(int frameCount, long time, long passedTime) {
//
//    }
//
//    @Override
//    protected void errorHandler(Throwable e) {
//        logger.error("业务异常", e);
//    }
}
