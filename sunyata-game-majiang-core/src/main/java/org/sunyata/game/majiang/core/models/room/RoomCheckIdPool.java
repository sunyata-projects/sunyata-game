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

    public int getState() {
        return state;
    }

    public RoomCheckIdPool setState(int state) {
        this.state = state;
        return this;
    }

    String id;
    int state;
}
