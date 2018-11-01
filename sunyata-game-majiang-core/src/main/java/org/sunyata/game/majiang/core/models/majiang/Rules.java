package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.majiang.core.models.majiang.gb.GbRules;
import org.sunyata.game.majiang.core.models.majiang.guangdong.ZhongyouGdRules;
import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;

import java.util.ArrayList;

/**
 * @author leo on 2017/1/17.
 */
public abstract class Rules {
    public static final String gb = "gb";
    public static final String guangDong = "zhongyouGD";
    public static Rules createRules(String name, RoomConfigInfo config) {
        switch (name) {
            case "zhongyouGD":
                return new ZhongyouGdRules(config);
            default:
                return new GbRules(config);
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

//    public abstract Map<JiaFanTypeInterface, FanInfo> getJiaFanMap();
//
//    public abstract Map<BaseFanTypeInterface, FanInfo> getBaseFanMap();

    public abstract boolean isHuiErGang();

    public int getUserMax() {
        return config.getUserMax();
    }
}
