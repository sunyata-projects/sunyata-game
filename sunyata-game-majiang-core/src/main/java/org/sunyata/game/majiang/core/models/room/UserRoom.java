package org.sunyata.game.majiang.core.models.room;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leo on 17/11/16.
 */
public class UserRoom implements Serializable {

    public Integer getId() {
        return id;
    }

    public UserRoom setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public UserRoom setRoomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public UserRoom setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserRoom setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public UserRoom setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public UserRoom setPosition(Integer position) {
        this.position = position;
        return this;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public UserRoom setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
        return this;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public UserRoom setDisable(boolean disable) {
        isDisable = disable;
        return this;
    }

    private Integer id;
    private Integer roomId;
    private Integer userId;
    private String userName;
    private Integer score;
    private Integer position;
    private Date joinDate;

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public UserRoom setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
        return this;
    }

    private String roomCheckId;
    private boolean isDisable = false;
}