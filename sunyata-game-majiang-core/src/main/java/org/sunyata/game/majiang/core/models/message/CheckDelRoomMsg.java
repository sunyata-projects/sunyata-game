package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.majiang.core.models.SceneUserInfo;

import java.util.ArrayList;
import java.util.List;

public class CheckDelRoomMsg {
    public static final int ID = 1 + 1;

    private short sceneId;
    /**
     * 牌局id
     */
    private int roomId;

    private int userId;
    private boolean result;


    private List<SceneUserInfo> infos = new ArrayList<>();
    private boolean isEnd;


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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<SceneUserInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<SceneUserInfo> infos) {
        this.infos = infos;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public String toString() {
        return "CheckDelRoomMsg{" +
                "sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", result=" + result +
                ", infos=" + infos +
                ", isEnd=" + isEnd +
                '}';
    }
}
