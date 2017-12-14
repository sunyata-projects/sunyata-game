package org.sunyata.game.majiang.core.models.message;

public class CheckExitRoomMsg {
    public static final int ID = CheckJoinRoomMsg.ID + 1;

    private int sceneId;
    /**
     * 牌局id
     */
    private int roomId;

    private int userId;

    private int joinSessionId;
    private int joinGatewayId;
    private boolean result;


    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckExitRoomMsg{" +
                "sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", joinSessionId=" + joinSessionId +
                ", joinGatewayId=" + joinGatewayId +
                ", result=" + result +
                '}';
    }
}
