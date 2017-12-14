package org.sunyata.game.majiang.core.models.message;

public class ChapterStartMsg{
    public static final int ID = RoomEndMsg.ID + 1;

    private int roomId;



    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ChapterEndMsg{" +
                "roomId=" + roomId +
                '}';
    }
}
