package org.sunyata.game.majiang.core.models.majiang.guangdong;

import org.sunyata.game.majiang.core.models.majiang.AbstractPaiPool;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.Rules;

/**
 * 发牌的池子
 *
 * @author leo on 2017/11/4.
 */
public class GuangDongPaiPool extends AbstractPaiPool {

    /**
     * 桌面剩余牌的信息
     */
//    private final ArrayList<Pai> pais = new ArrayList<>();
//
    private ZhongyouGdRules rules;
    public GuangDongPaiPool(Rules rules) {
        super(rules);
        this.rules = (ZhongyouGdRules) rules;
        //this.rules = rules;
    }


//    public void start() {
//        //发牌
//        pais.addAll(rules.getAllPai());
//        pais.addAll(rules.getAllPai());
//        pais.addAll(rules.getAllPai());
//        pais.addAll(rules.getAllPai());
//        //乱序
//        for (int i = 0; i < pais.size(); i++) {
//            int randomIndex = RandomUtils.nextInt(pais.size());
//
//            Pai temp = pais.get(i);
//            pais.set(i, pais.get(randomIndex));
//            pais.set(randomIndex, temp);
//        }
////        initTest();
//    }

    void initializeHuier() {

    }

//    public void clear() {
//        pais.clear();
//    }

//    public Pai get(int index) {
//        return pais.get(index);
//    }

//    public int size() {
//        return pais.size();
//    }

//    public Pai getFreePai(int locationIndex) {
//        int index = pais.size() - 1;
//        return index > -1 ? pais.remove(index) : null;
//    }
//
//    public Pai getFreeGangPai() {
//        for (int i = 0; i < pais.size(); i++) {
//            Pai pai = pais.get(i);
//            if (pai != null) {
//                pais.set(i, null);
//                return pai;
//            }
//        }
//        return null;
//    }

    private int[][] faPaiss;

//    public void faPai(int index, UserPlace userPlace) {
//        if (faPaiss != null) {
//            int[] faPais = faPaiss[index];
//            for (int i = 0; i < 13; i++) {
//                Pai pai = Pai.fromIndex(faPais[i]);
//                if (!pais.remove(pai)) {
//                    throw new RuntimeException("错误的牌" + pai + ",index:" + index + ",i:" + i);
//                }
//                userPlace.addShouPai(pai);
//            }
//        } else {
//            for (int j = 0; j < 13; j++) {
//                userPlace.addShouPai(getFreePai());
//            }
//        }
//
//    }

    public void initTest() {
        faPaiss = new int[][]{
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 31, 31, 31},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 20, 20, 20},
        };

//        faPaiss = new int[][]{
//                new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
//                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 20, 20, 20},
//        };
    }

//    public ArrayList<Pai> getLeftPai() {
//        return pais;
//    }

    public Pai[] getHuiEr() {
       return rules.getHuiEr(this);
    }


}
