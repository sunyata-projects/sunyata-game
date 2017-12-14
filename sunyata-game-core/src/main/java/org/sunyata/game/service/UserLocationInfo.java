package org.sunyata.game.service;

import java.io.Serializable;

/**
 * Created by leo on 17/11/14.
 */
public class UserLocationInfo implements Serializable {

    String userName;
    int serverId;
    int userIdInGateway;
    int userId;
    String openId;
    int roomId;


    public int getRoomId() {
        return roomId;
    }

    public UserLocationInfo setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }
    public String getOpenId() {
        return openId;
    }

    public UserLocationInfo setOpenId(String openId) {
        this.openId = openId;
        return this;
    }
    public String getUserName() {
        return userName;
    }

    public UserLocationInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int getServerId() {
        return serverId;
    }

    public UserLocationInfo setServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public int getUserIdInGateway() {
        return userIdInGateway;
    }

    public UserLocationInfo setUserIdInGateway(int userId) {
        this.userIdInGateway = userId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserLocationInfo setUserId(int userId) {
        this.userId = userId;
        return this;
    }



}
