package org.sunyata.game.majiang.core.models.room;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金帐户类型
 * Created by luxin on 16/7/18.
 */
public enum RoomStatusType implements EnumInterface {

    @EnumField(name = "初始")
    Init(0),
    @EnumField(name = "已开始")
    Start(1),
    @EnumField(name = "结束")
    End(2);

    public int getValue() {
        return value;
    }

    private int value = 0;

    RoomStatusType(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, RoomStatusType> _map = new HashMap<Integer, RoomStatusType>();

    static {
        for (RoomStatusType difficulty : RoomStatusType.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static RoomStatusType from(int value) {
        return _map.get(value);
    }
}
