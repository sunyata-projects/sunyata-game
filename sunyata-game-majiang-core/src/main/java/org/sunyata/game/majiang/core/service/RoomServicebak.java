//package org.sunyata.game.majiang.core.service;
//
//import FrameQueueContainer;
//import Json;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
//import org.sunyata.game.majiang.core.models.room.RoomEntity;
//import org.sunyata.game.majiang.core.models.SceneUser;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by leo on 17/11/21.
// */
//@Component
//public class RoomService extends FrameQueueContainer implements ApplicationContextAware {
//    private static final int FRAME_TIME_SPAN = 33;
//    private static final int RUN_QUEUE_MAX = 1024;
//
//    private static final Logger log = LoggerFactory.getLogger(RoomService.class);
//
//    private RoomAsyncService roomAsyncService = new RoomAsyncService(2);
//    private ApplicationContext applicationContext;
//    private HashMap<Integer, RoomImpi> map = new HashMap<>();
//    private HashMap<Integer, SceneUser> sceneUserMap = new HashMap<>();
//    public void checkJoinRoom(RoomEntity roomEntity) {
//        //创建房间
//        run(() -> {
//            RoomImpi room = map.get(roomEntity.getId());
//            if (room == null) {
//                RoomConfigInfo config = Json.decodeValue(roomEntity.getConfig(),RoomConfigInfo.class);
//                //RoomConfigInfo config = new Config(msg.getOptions());
//                room = new RoomImpi(roomAsyncService, config);
//
//                //RoomEntity roomInfo = new RoomInfo(room, rulesName);
//                //BeanUtils.copyProperties(msg, roomInfo);
//                room.setRoomInfo(roomInfo);
//
//                room.start();
//                applicationContext.getAutowireCapableBeanFactory().autowireBean(room);
//                map.put(msg.getRoomId(), room);
//            }
//            //进入房间成功
//            SceneUser joinSceneUser = null;
//
//            List<SceneUser> sceneUsers = new ArrayList<>();
//            for (UserInfo userInfo : msg.getUserInfos()) {
//                SceneUser sceneUser = sceneUserMap.get(userInfo.getUserId());
//                if (sceneUser == null) {
//                    sceneUser = new SceneUser();
//                    sceneUser.setJoinGame(false);
//                    applicationContext.getAutowireCapableBeanFactory().autowireBean(sceneUser);
//                }
//                sceneUser.setRoom(room);
//                sceneUser.setLocationIndex(userInfo.getLocationIndex());
//                sceneUser.setUserName(userInfo.getUserName());
//                sceneUser.setAvatar(userInfo.getAvatar());
//                sceneUser.setSex(userInfo.getSex());
//                sceneUser.setGold(userInfo.getGold());
//                sceneUser.setUserId(userInfo.getUserId());
//                sceneUser.setIp(userInfo.getIp());
//                sceneUser.setLongitude(userInfo.getLongitude());
//                sceneUser.setLatitude(userInfo.getLatitude());
//                if (userInfo.getUserId() == msg.getJoinUserId()) {
//                    sceneUser.setSessionId(msg.getJoinSessionId());
//                    sceneUser.setGatewayId(msg.getJoinGatewayId());
//                    joinSceneUser = sceneUser;
//                }
//                sceneUsers.add(sceneUser);
//
//                if (msg.getUser0() == userInfo.getUserId()) {
//                    sceneUser.setScore(msg.getScore0());
//                } else if (msg.getUser1() == userInfo.getUserId()) {
//                    sceneUser.setScore(msg.getScore1());
//                } else if (msg.getUser2() == userInfo.getUserId()) {
//                    sceneUser.setScore(msg.getScore2());
//                } else if (msg.getUser3() == userInfo.getUserId()) {
//                    sceneUser.setScore(msg.getScore3());
//                }
//
//                sceneUserMap.put(userInfo.getUserId(), sceneUser);
//            }
//
//
//            long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(msg.getJoinSessionId(), msg.getJoinGatewayId());
//            gatewayIdUnionSessionIdMap.put(gatewayIdUnionSessionId, room);
//            gatewayIdUnionSessionIdUserMap.put(gatewayIdUnionSessionId, joinSceneUser);
//            room.join(joinSceneUser, sceneUsers, (r) -> {
//                //进入房间成功
//                CheckJoinRoomRetMsg ret = new CheckJoinRoomRetMsg();
//                ret.setRoomId(msg.getRoomId());
//                ret.setJoinUserId(msg.getJoinUserId());
//                ret.setJoinGatewayId(msg.getJoinGatewayId());
//                ret.setJoinSessionId(msg.getJoinSessionId());
//                ret.setSucccess(r);
//                bossClient.writeAndFlush(ret);
//            });
//        });
//    }
//
//
//    public RoomService() {
//        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//
//    }
//
//    @Override
//    protected void threadMethod(int frameCount, long time, long passedTime) {
//
//    }
//
//    @Override
//    protected void errorHandler(Throwable e) {
//        log.error("严重异常", t);
//    }
//}
