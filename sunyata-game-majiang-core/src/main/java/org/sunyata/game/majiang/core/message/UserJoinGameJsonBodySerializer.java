package org.sunyata.game.majiang.core.message;

import org.sunyata.game.server.message.AbstractJsonBodySerializer;

public class UserJoinGameJsonBodySerializer extends AbstractJsonBodySerializer {
    private Integer roomId;

    public int getUserId() {
        return userId;
    }

    public UserJoinGameJsonBodySerializer setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    private int userId;


    public Integer getRoomId() {
        return roomId;
    }

    public UserJoinGameJsonBodySerializer setRoomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }
}
