package org.sunyata.game.majiang.core.models;

import org.sunyata.game.majiang.core.models.room.RoomEntity;
import org.sunyata.game.majiang.core.models.room.UserRoom;

import java.io.Serializable;

/**
 * Created by leo on 17/11/21.
 */
public class AllocateSeatRetInfo implements Serializable{
    public RoomEntity getRoom() {
        return room;
    }

    public AllocateSeatRetInfo setRoom(RoomEntity room) {
        this.room = room;
        return this;
    }

    public UserRoom getUserRoom() {
        return userRoom;
    }

    public AllocateSeatRetInfo setUserRoom(UserRoom userRoom) {
        this.userRoom = userRoom;
        return this;
    }

    private RoomEntity room;
    private UserRoom userRoom;
}
