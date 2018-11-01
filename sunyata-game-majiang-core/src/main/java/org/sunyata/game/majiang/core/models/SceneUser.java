package org.sunyata.game.majiang.core.models;

import com.googlecode.protobuf.format.JsonFormat;
import org.sunyata.game.client.AnyClientManager;
import org.sunyata.game.server.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.majiang.core.models.message.GameUserInfo;
import org.sunyata.game.majiang.core.service.RoomImpi;

import java.util.Arrays;

/**
 * @author leo on 16/10/7.
 */
public class SceneUser {
    Logger logger = LoggerFactory.getLogger(SceneUser.class);
    private int userId;
    private String userName;
    private int locationIndex;
    private int sessionId = -1;
    private int gatewayId = -1;
    private boolean joinGame;

    private String avatar;
    /**
     * 0:女生,1:男生,2:未知
     */
    private int sex;
    private int gold;
    private int score;

    private RoomImpi room;
    /**
     * 在同一个圈内,胡牌次数!
     */
    private int quanHuNums;

    //    @Autowired
//    private SceneService sceneService;
    private boolean online;

    private String ip;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;

    public SceneUser() {

    }

    public GameUserInfo toMessage() {
        GameUserInfo m = new GameUserInfo();
        m.setUserId(userId);
        m.setSex(sex);
        m.setAvatar(avatar);
        m.setLocationIndex(locationIndex);
        m.setUserName(userName);
        m.setGold(gold);
        m.setScore(score);
        m.setOnline(online);
        m.setIp(ip);
        return m;
    }


    @Autowired
    AnyClientManager anyClientManager;

    public void sendMessage(Message msg) {
        if (gatewayId > -1) {
            //sceneService.sendMessage(gatewayId, sessionId, msg);
            byte[] bytes = msg.toBytes();
            try {
                if (msg.getMessageType() == 1) {
                    throw new Exception("aaaa");
                }
                logger.info("向客户端发送消息:messageType:{},sessionId:{},gatewayId:{}", msg.getMessageType(), sessionId,
                        gatewayId);
                com.google.protobuf.Message pbMsg = (com.google.protobuf.Message) msg.toPbObject();
                if (pbMsg != null) {
                    logger.info("向客户端发送消息:{}", JsonFormat.printToString(pbMsg));
                }
                if (msg.getMessageType() == 0) {
                    logger.error("错误:{}", msg.getClass().getTypeName());
                }
                anyClientManager.sendMessageToUserByInnerGatewayServer(sessionId, gatewayId, String.valueOf(msg.getMessageType()), bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public void noticeError(String key) {
        noticeError(key, new String[0]);
    }

    public void noticeError(String key, String[] args) {
        notice(key, args, NoticeType.ERROR, false);
    }

    public void noticeMain(String key) {
        noticeMain(key, new String[0]);
    }

    public void noticeMain(String key, String[] args) {
        notice(key, args, NoticeType.MAIN, false);
    }

    public void notice(String key, String[] args, NoticeType type) {
        notice(key, args, type, false);
    }

    public void notice(String key, String[] args, NoticeType type, boolean reboot) {
        if (joinGame) {
            //sendMessage(new Notice(key, args, type.ordinal(), reboot));
            Room.Notice.Builder builder = Room.Notice.newBuilder();
            byte[] bytes = builder.setKey(key).addAllArgs(Arrays.asList(args)).setType(type.ordinal()).setRebot
                    (reboot).build()
                    .toByteArray();
            try {
                anyClientManager.sendMessageToUserByInnerGatewayServer(sessionId, gatewayId, Commands.notice, bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setGatewayId(int gatewayId) {
        this.gatewayId = gatewayId;
    }

    public int getGatewayId() {
        return gatewayId;
    }

    public RoomImpi getRoom() {
        return room;
    }

    public void setRoom(RoomImpi room) {
        this.room = room;
    }

    public boolean isJoinGame() {
        return joinGame;
    }

    public void setJoinGame(boolean joinGame) {
        this.joinGame = joinGame;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getQuanHuNums() {
        return quanHuNums;
    }

    public void setQuanHuNums(int quanHuNums) {
        this.quanHuNums = quanHuNums;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "SceneUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", locationIndex=" + locationIndex +
                ", sessionId=" + sessionId +
                ", gatewayId=" + gatewayId +
                ", joinGame=" + joinGame +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", gold=" + gold +
                ", score=" + score +
                ", room=" + room +
                ", quanHuNums=" + quanHuNums +
                ", online=" + online +
                ", ip='" + ip + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public void removeScore(int scoreNums) {
        score -= scoreNums;
    }

    public void addScore(int scoreNums) {
        score += scoreNums;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }
}
