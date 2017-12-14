package org.sunyata.game.majiang.core.models.room;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金帐户类型
 * Created by luxin on 16/7/18.
 */
public enum CreateRoomType implements EnumInterface {

    @EnumField(name = "为自己开房")
    MySelf(1),

    @EnumField(name = "为他人开房")
    ToOthers(2);

    public int getValue() {
        return value;
    }

    private int value = 0;

    CreateRoomType(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, CreateRoomType> _map = new HashMap<Integer, CreateRoomType>();

    static {
        for (CreateRoomType difficulty : CreateRoomType.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static CreateRoomType from(int value) {
        return _map.get(value);
    }
}
