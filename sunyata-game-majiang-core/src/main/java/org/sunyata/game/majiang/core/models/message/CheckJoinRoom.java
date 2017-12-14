package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractJsonBodySerializer;

public class CheckJoinRoom extends AbstractJsonBodySerializer {
    private int userId;

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public CheckJoinRoom setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
        return this;
    }

    private String roomCheckId;
    public CheckJoinRoom setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getUserId() {
        return userId;
    }
}