package org.sunyata.game.majiang.core.models.message;

public class RoomEndMsg {
    //public static final int ID = CheckJoinRoomRetMsg.ID + 1;
    public static final int ID = 1 + 1;

    private int roomId;
    private int crateUserId;

    public int getCrateUserId() {
        return crateUserId;
    }

    public void setCrateUserId(int crateUserId) {
        this.crateUserId = crateUserId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomEndMsg{" +
                "roomId=" + roomId +
                ", crateUserId=" + crateUserId +
                '}';
    }
}
