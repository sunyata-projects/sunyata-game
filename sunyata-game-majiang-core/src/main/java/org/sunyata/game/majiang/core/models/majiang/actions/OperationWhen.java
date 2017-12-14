package org.sunyata.game.majiang.core.models.majiang.actions;


import org.sunyata.game.EnumField;
import org.sunyata.game.EnumInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金帐户类型
 * Created by luxin on 16/7/18.
 */
public enum OperationWhen implements EnumInterface {

    @EnumField(name = "自己打牌")
    chuPaiAfterFaPai(1),

    @EnumField(name = "别人打牌")
    whenOtherChuPai(2);

    public int getValue() {
        return value;
    }

    private int value = 0;

    OperationWhen(int value) {
        this.value = value;
    }

    // Mapping difficulty to difficulty id
    private static final Map<Integer, OperationWhen> _map = new HashMap<Integer, OperationWhen>();

    static {
        for (OperationWhen difficulty : OperationWhen.values())
            _map.put(difficulty.value, difficulty);
    }

    /**
     * Get difficulty from value
     *
     * @param value Value
     * @return Difficulty
     */
    public static OperationWhen from(int value) {
        return _map.get(value);
    }
}
