package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
import org.sunyata.game.majiang.core.models.majiang.guangdong.BaseFanType;
import org.sunyata.game.majiang.core.models.majiang.guangdong.JiaFanType;
import org.sunyata.game.majiang.core.models.majiang.guangdong.ZhongyouGdRules;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author leo on 2017/1/17.
 */
public abstract class Rules  {
    public static Rules createRules(String name, RoomConfigInfo config) {
        switch (name) {
            case "zhongyouGD":
                return new ZhongyouGdRules(config);
            default:
                return new ZhongyouGdRules(config);
        }
    }

    public int getAllPaiLength() {
        return allPaiLength;
    }

    protected int allPaiLength;

    public abstract boolean rest();

    public RoomConfigInfo getConfig() {
        return config;
    }

//    public Rules setConfig(RoomConfigInfo config) {
//        this.config = config;
//        return this;
//    }

    protected RoomConfigInfo config;
    private int baoliuLength = 12;


    private int shouTimeMillisecond = 15000;

    public Rules(RoomConfigInfo config) {
        this.config = config;
    }

    public int getBaoliuLength() {
        return baoliuLength;
    }

    public int getShouTimeMillisecond() {
        return shouTimeMillisecond;
    }

    public void setShouTimeMillisecond(int shouTimeMillisecond) {
        this.shouTimeMillisecond = shouTimeMillisecond;
    }

    public abstract ArrayList<Pai> getAllPai();

    //public abstract Pai[] getHuiEr(PaiPoolInterface paiPool);

    public abstract boolean allowChi();

    public abstract boolean allowFangPao();

    //public abstract boolean isZaMa();

//    public abstract int getZaMa();

    public abstract Map<JiaFanType, FanInfo> getJiaFanMap();

    public abstract Map<BaseFanType, FanInfo> getBaseFanMap();

    public abstract boolean isHuiErGang();

    public int getUserMax() {
        return config.getUserMax();
    }
}
