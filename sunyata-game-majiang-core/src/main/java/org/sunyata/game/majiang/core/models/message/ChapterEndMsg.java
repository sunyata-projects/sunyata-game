package org.sunyata.game.majiang.core.models.message;

import java.util.Map;

public class ChapterEndMsg {
    public static final int ID = CheckOfflineRoomMsg.ID + 1;

    private int roomId;

    private Map<Integer, Integer> userScoreMap;





    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Map<Integer, Integer> getUserScoreMap() {
        return userScoreMap;
    }

    public void setUserScoreMap(Map<Integer, Integer> userScoreMap) {
        this.userScoreMap = userScoreMap;
    }

    @Override
    public String toString() {
        return "ChapterEndMsg{" +
                "roomId=" + roomId +
                ", userScoreMap=" + userScoreMap +
                '}';
    }
}
