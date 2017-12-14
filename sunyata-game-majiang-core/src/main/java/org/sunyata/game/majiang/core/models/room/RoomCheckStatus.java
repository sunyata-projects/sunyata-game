package org.sunyata.game.majiang.core.models.room;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金帐户类型
 * Created by luxin on 16/7/18.
 */
public enum RoomCheckStatus implements EnumInterface {

    @EnumField(name = "NoUse")
    NoUser(0),

    @EnumField(name = "Use")
    Use(1),

    @EnumField(name = "buffer")
    Buffer(2);

    public int getValue() {
        return value;
    }

    private int value = 0;

    RoomCheckStatus(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, RoomCheckStatus> _map = new HashMap<Integer, RoomCheckStatus>();

    static {
        for (RoomCheckStatus difficulty : RoomCheckStatus.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static RoomCheckStatus from(int value) {
        return _map.get(value);
    }
}
