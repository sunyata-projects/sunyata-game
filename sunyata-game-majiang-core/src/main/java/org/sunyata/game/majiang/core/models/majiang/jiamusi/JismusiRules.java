package org.sunyata.game.majiang.core.models.majiang.jiamusi;

import org.apache.commons.lang.math.RandomUtils;
import org.sunyata.game.majiang.core.models.majiang.FanInfo;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.Rules;
import org.sunyata.game.majiang.core.models.majiang.BaseFanTypeInterface;
import org.sunyata.game.majiang.core.models.majiang.guangdong.GuangDongBaseFanType;
import org.sunyata.game.majiang.core.models.majiang.guangdong.GuangDongJiaFanType;
import org.sunyata.game.majiang.core.models.majiang.JiaFanTypeInterface;
import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author leo on 2017/10/24.
 */
public class JismusiRules extends Rules {
    private static final ArrayList<Pai> ALL_PAI_LIST = createAllList();
    public static final Map<JiaFanTypeInterface, FanInfo> jiaFanMap = initJiaFanMap();
    public static final Map<BaseFanTypeInterface, FanInfo> baseFanMap = initBaseFanMap();

    private static Map<JiaFanTypeInterface, FanInfo> initJiaFanMap() {
        Map<JiaFanTypeInterface, FanInfo> map = new HashMap<>();
        map.put(GuangDongJiaFanType.ZHUANG_HU, new FanInfo("庄胡", 1));
        map.put(GuangDongJiaFanType.WU_HUN_ER, new FanInfo("无鬼", 2));
        map.put(GuangDongJiaFanType.YI_TIAO_LONG, new FanInfo("一条龙", 3));
        map.put(GuangDongJiaFanType.HAIDI_LAO_YUE, new FanInfo("海底捞月", 4));
        map.put(GuangDongJiaFanType.GANG_SHANG_HUA, new FanInfo("杠上开花", 5));
        map.put(GuangDongJiaFanType.QIN_YI_SE, new FanInfo("清一色", 6));
        map.put(GuangDongJiaFanType.HUN_YI_SE, new FanInfo("混一色", 2));
        map.put(GuangDongJiaFanType.MEN_QING, new FanInfo("门清", 2));
        map.put(GuangDongJiaFanType.ZHI_YI_SHE, new FanInfo("字一色", 8));
        map.put(GuangDongJiaFanType.JIA_AN_GANG, new FanInfo("加暗杠", 0));
        map.put(GuangDongJiaFanType.JIA_MING_GANG, new FanInfo("加明杠", 0));
        map.put(GuangDongJiaFanType.DAI_GENG, new FanInfo("带跟", 1));
        return map;
    }

    private static Map<BaseFanTypeInterface, FanInfo> initBaseFanMap() {
        Map<BaseFanTypeInterface, FanInfo> map = new HashMap<>(GuangDongBaseFanType.getBaseFanMap());
        map.replace(GuangDongBaseFanType.HUI_ER_GANG, new FanInfo("自摸", 1));
        map.replace(GuangDongBaseFanType.ZI_MO, new FanInfo("自摸", 1));
        map.replace(GuangDongBaseFanType.JI_HU, new FanInfo("", 0));
        map.replace(GuangDongBaseFanType.DUI_DUI_HU, new FanInfo("对对胡", 3));
        map.replace(GuangDongBaseFanType.QI_DUI, new FanInfo("七对子", 4));
        map.replace(GuangDongBaseFanType.TIAN_HU, new FanInfo("天胡", 10));
        map.replace(GuangDongBaseFanType.DI_HU, new FanInfo("地胡", 10));
        //自摸1番，明杠1番，暗杠2番，中一个码加1番
        return map;
    }


    private static ArrayList<Pai> createAllList() {
        ArrayList<Pai> list = createOrdinalAllList();

        list.add(Pai.SANYUAN_ZHONG);
        return list;
    }

    private static ArrayList<Pai> createOrdinalAllList() {
        ArrayList<Pai> list = new ArrayList<>();

        for (int paiIndex = Pai.TONG_1.getIndex(); paiIndex <= Pai.WAN_9.getIndex(); paiIndex++) {
            Pai pai = Pai.fromIndex(paiIndex);
            list.add(pai);
        }
        return list;
    }

    private Pai[] huiErs;


    public JismusiRules(RoomConfigInfo config) {
        super(config);
    }


    @Override
    public boolean rest() {
        String binType = config.getString(RoomConfigInfo.BIAN_TYPE);
        if (binType == null) {
            return false;
        }
        switch (binType) {
            case RoomConfigInfo.BIAN_TYPE_DAN_GUI: {
                ArrayList<Pai> allPai = getAllPai();
                huiErs = new Pai[]{allPai.get(RandomUtils.nextInt(allPai.size()))};
                break;
            }
            case RoomConfigInfo.BIAN_TYPE_SHUANG_GUI: {
                ArrayList<Pai> allPai = getAllPai();
                Pai pai = allPai.get(RandomUtils.nextInt(allPai.size()));
                huiErs = new Pai[]{pai, pai.next()};
                break;
            }
            case RoomConfigInfo.BIAN_TYPE_HONG_ZHONG_BIAN: {
                huiErs = new Pai[]{Pai.SANYUAN_ZHONG};
                break;
            }
            case RoomConfigInfo.BIAN_TYPE_BAI_BAN_BIAN: {
                huiErs = new Pai[]{Pai.SANYUAN_BEI};
                break;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Pai> getAllPai() {
        return ALL_PAI_LIST;
    }


    @Override
    public boolean isHuiErGang() {
        return true;
    }

//    @Override
//    public Pai[] getHuiEr(PaiPoolInterface paiPool) {
//        return huiErs;
//    }

    @Override
    public boolean allowChi() {
        return true;
    }

    @Override
    public boolean allowFangPao() {
        return true;//;;config.getBoolean(Config.IS_TUI_DAO_HU);
    }

//    @Override
//    public boolean isZaMa() {
//        return false;
//    }

//    @Override
//    public int getZaMa() {
//        return 0;
//    }

//    @Override
//    public Map<JiaFanTypeInterface, FanInfo> getJiaFanMap() {
//        if (config.getBoolean(RoomConfigInfo.IS_TUI_DAO_HU)) {
//            return GuangDongJiaFanType.jiaFanMap;
//        }
//        return jiaFanMap;
//    }
//
//    @Override
//    public Map<BaseFanTypeInterface, FanInfo> getBaseFanMap() {
//        return baseFanMap;
//    }
//

    public int getBaoliuLength() {
        return 16;
    }
}
