package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.majiang.core.models.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckJoinRoomMsg  {
    public static final int ID = 1 + 1;

    private short sceneId;
    /**
     * 牌局id
     */
    private int roomId;
    /**
     * 创建用户id
     */
    private int createUserId;
    /**
     * 用户数
     */
    private int userMax;
    /**
     * 牌局uuid
     */
    private String uuid;
    /**
     * 用户0
     */
    private int user0;
    /**
     * 用户1
     */
    private int user1;
    /**
     * 用户2
     */
    private int user2;
    /**
     * 用户3
     */
    private int user3;


    private int score0;
    private int score1;
    private int score2;
    private int score3;
    /**
     * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
     */
    private String roomCheckId;

    private int joinSessionId;
    private int joinGatewayId;
    private int joinUserId;


    private List<UserInfo> userInfos = new ArrayList<>();
    private Map<String, String> options = new TreeMap<>();


    public short getSceneId() {
        return sceneId;
    }

    public void setSceneId(short sceneId) {
        this.sceneId = sceneId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUserMax() {
        return userMax;
    }

    public void setUserMax(int userMax) {
        this.userMax = userMax;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getUser0() {
        return user0;
    }

    public void setUser0(int user0) {
        this.user0 = user0;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public int getUser3() {
        return user3;
    }

    public void setUser3(int user3) {
        this.user3 = user3;
    }

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public void setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
    }


    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public int getJoinSessionId() {
        return joinSessionId;
    }

    public void setJoinSessionId(int joinSessionId) {
        this.joinSessionId = joinSessionId;
    }

    public int getJoinGatewayId() {
        return joinGatewayId;
    }

    public void setJoinGatewayId(int joinGatewayId) {
        this.joinGatewayId = joinGatewayId;
    }

    public int getJoinUserId() {
        return joinUserId;
    }

    public void setJoinUserId(int joinUserId) {
        this.joinUserId = joinUserId;
    }

    public int getScore0() {
        return score0;
    }

    public void setScore0(int score0) {
        this.score0 = score0;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }


    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CheckJoinRoomMsg{" +
                "sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", createUserId=" + createUserId +
                ", userMax=" + userMax +
                ", uuid='" + uuid + '\'' +
                ", user0=" + user0 +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", user3=" + user3 +
                ", score0=" + score0 +
                ", score1=" + score1 +
                ", score2=" + score2 +
                ", score3=" + score3 +
                ", roomCheckId='" + roomCheckId + '\'' +
                ", joinSessionId=" + joinSessionId +
                ", joinGatewayId=" + joinGatewayId +
                ", joinUserId=" + joinUserId +
                ", userInfos=" + userInfos +
                ", options=" + options +
                '}';
    }
}
