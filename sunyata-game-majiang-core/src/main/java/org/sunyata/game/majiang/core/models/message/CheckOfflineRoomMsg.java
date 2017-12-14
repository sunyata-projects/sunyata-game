package org.sunyata.game.majiang.core.models.message;

public class CheckOfflineRoomMsg {
    public static final int ID = CheckDelRoomMsg.ID + 1;
//
//    private int sceneId;
    /**
     * 牌局id
     */
    private int roomId;

    private int userId;
//
//    private int joinSessionId;
//    private int joinGatewayId;


//    public int getSceneId() {
//        return sceneId;
//    }
//
//    public void setSceneId(int sceneId) {
//        this.sceneId = sceneId;
//    }

    public int getRoomId() {
        return roomId;
    }

    public CheckOfflineRoomMsg setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public CheckOfflineRoomMsg setUserId(int userId) {
        this.userId = userId;
        return this;
    }


//    public int getJoinSessionId() {
//        return joinSessionId;
//    }
//
//    public void setJoinSessionId(int joinSessionId) {
//        this.joinSessionId = joinSessionId;
//    }

//    public int getJoinGatewayId() {
//        return joinGatewayId;
//    }
//
//    public void setJoinGatewayId(int joinGatewayId) {
//        this.joinGatewayId = joinGatewayId;
//    }


    @Override
    public String toString() {
        return "CheckExitRoomMsg{" +
                //"sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", userId=" + userId +
                //", joinSessionId=" + joinSessionId +
                //", joinGatewayId=" + joinGatewayId +
                '}';
    }
}
