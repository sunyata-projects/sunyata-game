package org.sunyata.game.majiang.core.models.room;

/**
 * Created by leo on 17/11/17.
 */
public class RoomCheckIdPool {
    public String getId() {
        return id;
    }

    public RoomCheckIdPool setId(String id) {
        this.id = id;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public RoomCheckIdPool setStatus(int status) {
        this.status = status;
        return this;
    }

    String id;
    int status;
}
