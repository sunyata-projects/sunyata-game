package org.sunyata.game.majiang.core.models.room;

import org.apache.commons.lang3.StringUtils;
import org.sunyata.core.json.Json;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.majiang.core.models.majiang.Rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by leo on 17/11/17.
 */
public class RoomConfigInfo implements Serializable {
    public static final String CHAPTER_MAX = "chapterMax";
    public static final String CHAPTER_NUMS = "chapterNums";
    public static final String IS_HUIER = "IS_HUIER";
    public static final String MAI_MA = "maiMa";


    public static final String IS_TUI_DAO_HU = "isTuiDaoHu";
    public static final String BIAN_TYPE = "bianType";


    public static final String BIAN_TYPE_HONG_ZHONG_BIAN = "hongZhongBian";
    public static final String BIAN_TYPE_BAI_BAN_BIAN = "baiBanBian";
    public static final String BIAN_TYPE_DAN_GUI = "danGui";
    public static final String BIAN_TYPE_SHUANG_GUI = "shuangGui";
    public static final String BIAN_TYPE_TUI_DAO_HU = "tuiDaoHu";
    public static final String ruleName = "ruleName";
    private CreateRoomType createRoomType;
    //    public RoomConfigInfo(Map<String, String> options) {
    ////        if (options instanceof TreeMap) {
    ////            this.options = options;
    ////        } else {
    ////            this.options = new TreeMap<>(options);
    ////        }
    //    }


    public String getRuleName() {
        String ruleName = getString(RoomConfigInfo.ruleName);
        if (StringUtils.isEmpty(ruleName)) {
            ruleName = Rules.gb;
        }
        return ruleName;
    }

    public RoomConfigInfo fromProtobufReq(Room.CreateRoomReq roomReq) {
        List<Room.KeyValueInfo> ruleIdsList = roomReq.getRuleIdsList();
        configs = ruleIdsList.stream().map(p -> new KeyValueInfo().setKey(p.getKey()).setValue(p
                .getValue())).collect(Collectors
                .toList());
        KeyValueInfo userMax = getValue("userMax");
        KeyValueInfo chapterNums = getValue("chapterNums");
        KeyValueInfo createType = getValue("createType");
        if (userMax != null) {
            this.setUserMax(Integer.parseInt(userMax.getValue()));
        }
        if (chapterNums != null) {
            this.setChapterNums(Integer.parseInt(chapterNums.getValue()));
        }
        if (createType != null) {
            this.setCreateRoomType(CreateRoomType.from(Integer.parseInt(createType.getValue())));
        } else {
            this.setCreateRoomType(CreateRoomType.MySelf);
        }
        return this;
    }

    private int userMax;
    private int chapterNums;

    public KeyValueInfo getValue(String key) {
        return configs.stream().filter(p -> p.getKey().equals(key)).findFirst().orElse(null);
    }

    public String getString(String key) {
        KeyValueInfo value = getValue(key);
        return value == null ? null : value.getValue();

    }


    public String getString(String key, String defaultValue) {
        KeyValueInfo value = getValue(key);
        if (value == null) {
            return defaultValue;
        }
        return value.getValue();

    }


    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        KeyValueInfo value = getValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return NumberUtils.toInt(value.getValue(), defaultValue);
        }
    }


    List<KeyValueInfo> configs = new ArrayList<>();

    public int getUserMax() {
        return userMax;
    }

    public RoomConfigInfo setUserMax(int userMax) {
        this.userMax = userMax;
        return this;
    }

    public int getChapterNums() {
        return chapterNums;
    }

    public RoomConfigInfo setChapterNums(int chapterNums) {
        this.chapterNums = chapterNums;
        return this;
    }


    public List<KeyValueInfo> getConfigs() {
        return configs;
    }

    public RoomConfigInfo setConfigs(List<KeyValueInfo> configs) {
        this.configs = configs;
        return this;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        KeyValueInfo value = getValue(key);
        if (value == null) {
            return defaultValue;
        } else {
            return BooleanUtils.toBoolean(value.getValue(), "true", "false");
        }
    }

    public void setCreateRoomType(CreateRoomType createRoomType) {
        this.createRoomType = createRoomType;
    }

    public CreateRoomType getCreateRoomType() {
        return createRoomType;
    }

    public RoomConfigInfo fromJsonString(String config) {
        return Json.decodeValue(config, this.getClass());
    }


    public static class KeyValueInfo {
        public String getKey() {
            return key;
        }

        public KeyValueInfo setKey(String key) {
            this.key = key;
            return this;
        }

        public String getValue() {
            return value;
        }

        public KeyValueInfo setValue(String value) {
            this.value = value;
            return this;
        }

        String key;
        String value;
    }
}
