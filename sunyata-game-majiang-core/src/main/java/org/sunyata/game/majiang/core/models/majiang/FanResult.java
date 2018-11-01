package org.sunyata.game.majiang.core.models.majiang;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 算翻结果
 *
 * @author leo on 2017/11/5.
 */
public class FanResult {
    //雀頭
    private Pai queTou;
    //顺子
    private Pai[] shunZi;
    //刻子
    private ArrayList<Pai> keZi = new ArrayList<>();

    private String baseFanName;

    private ArrayList<JiaFanTypeInterface> jiaFans = new ArrayList<>();

    private int fan;

    private String fanString;

    private int[] huiErBian;


    public FanResult() {

    }

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public void setFanString(String fanString) {
        this.fanString = fanString;
    }


    public String getFanString() {
        return fanString;
    }

    public boolean isDanDiao(Pai pai) {
        if (pai.equals(queTou)) {
            return true;
        }
        for (Pai p : keZi) {
            if (p.equals(pai)) {
                return true;
            }
        }
        for (Pai p : shunZi) {
            if (pai.equals(p.nextPaiType())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDuiDuiHu(UserPaiInfo userPaiInfo) {
        return !hasSunZi() && !userPaiInfo.hasChi();
    }

    private boolean hasSunZi() {
        return this.shunZi.length > 0;
    }

    public Pai getQueTou() {
        return queTou;
    }

    public void setQueTou(Pai queTou) {
        this.queTou = queTou;
    }

    public Pai[] getShunZi() {
        return shunZi;
    }

    public void setShunZi(Pai[] shunZi) {
        this.shunZi = shunZi;
    }

    public ArrayList<Pai> getKeZi() {
        return keZi;
    }

    public void setKeZi(Pai[] keZi) {
        if (keZi != null && keZi.length > 0) {
            if (this.keZi == null) {
                this.keZi = new ArrayList<>();
            }
            for (Pai pai : keZi) {
                this.keZi.add(pai);
            }
        }
        //this.keZi = keZi;
    }

    public String getBaseFanName() {
        return baseFanName;
    }

    public void setBaseFanName(String baseFanName) {
        this.baseFanName = baseFanName;
    }

    public ArrayList<JiaFanTypeInterface> getJiaFans() {
        return jiaFans;
    }


    public int[] getHuiErBian() {
        return huiErBian;
    }

    public void setHuiErBian(int[] huiErBian) {
        this.huiErBian = huiErBian;
    }


    @Override
    public String toString() {
        return "FanResult{" +
                "queTou=" + queTou +
                ", shunZi=" + Arrays.toString(shunZi) +
                //", keZi=" + Arrays.toString(keZi) +
                ", baseFanType=" + baseFanName +
                ", jiaFans=" + jiaFans +
                ", fan=" + fan +
                ", fanString='" + fanString + '\'' +
                ", huiErBian=" + Arrays.toString(huiErBian) +
                '}';
    }
}
