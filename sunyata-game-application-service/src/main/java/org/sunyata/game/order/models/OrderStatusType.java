package org.sunyata.game.order.models;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 15/11/13.
 */
public enum OrderStatusType implements EnumInterface {
    @EnumField(name = "未处理")
    Init(0),
    @EnumField(name = "成功")
    Success(6);

    public int getValue() {
        return value;
    }

    private int value = 0;

    OrderStatusType(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, OrderStatusType> _map = new HashMap<Integer, OrderStatusType>();

    static {
        for (OrderStatusType difficulty : OrderStatusType.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static OrderStatusType from(int value) {
        return _map.get(value);
    }

}

