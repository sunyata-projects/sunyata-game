package org.sunyata.game.majiang.core.models.room;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by leo on 17/11/16.
 */
public class RoomEntity implements Serializable {

    private Integer id;
    private String orderId;
    private String roomCheckId;

    private Integer createUserId;
    private String uuid;

    private Integer sceneId;

    private Integer userMax;
    private Integer chapterNums;

    private Date createDate;

    private Date startDate;

    private Date endDate;
    private Integer status;
    private CreateRoomType createRoomType;


    private String config;
    private List<UserRoom> userRooms;

    public String getConfig() {
        return config;
    }

    public RoomEntity setConfig(String config) {
        this.config = config;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public RoomEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public RoomEntity setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public RoomEntity setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
        return this;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public RoomEntity setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public RoomEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public RoomEntity setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    public Integer getUserMax() {
        return userMax;
    }

    public RoomEntity setUserMax(Integer userMax) {
        this.userMax = userMax;
        return this;
    }

    public Integer getChapterNums() {
        return chapterNums;
    }

    public RoomEntity setChapterNums(Integer chapterNums) {
        this.chapterNums = chapterNums;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public RoomEntity setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public RoomEntity setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public RoomEntity setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public RoomEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public CreateRoomType getCreateRoomType() {
        return createRoomType;
    }

    public RoomEntity setCreateRoomType(CreateRoomType createRoomType) {
        this.createRoomType = createRoomType;
        return this;
    }

    public void setUserRooms(List<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }

    public List<UserRoom> getUserRooms() {
        return userRooms;
    }
}