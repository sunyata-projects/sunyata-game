package org.sunyata.game.majiang.core.models;

/**
 * @author leo on 2017/12/24.
 */
public class UserInfo {
    private int userId;
    private int locationIndex;
//    private int sessionId;
//    private int gatewayId;

    private String userName;
    private String avatar;
    /**
     * 0:女生,1:男生,2:未知
     */
    private int sex;
    private int gold;
    private String ip;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;
    private int score;



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
//
//    public int getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(int sessionId) {
//        this.sessionId = sessionId;
//    }
//
//    public int getGatewayId() {
//        return gatewayId;
//    }
//
//    public void setGatewayId(int gatewayId) {
//        this.gatewayId = gatewayId;
//    }

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
        return "UserInfo{" +
                "userId=" + userId +
                ", locationIndex=" + locationIndex +
//                ", sessionId=" + sessionId +
//                ", gatewayId=" + gatewayId +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", gold=" + gold +
                ", ip='" + ip + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
