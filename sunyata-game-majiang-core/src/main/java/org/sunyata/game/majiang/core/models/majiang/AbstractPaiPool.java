package org.sunyata.game.majiang.core.models.majiang;

import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/11/28.
 */
public class AbstractPaiPool implements PaiPoolInterface {

    protected Pai[] huiEr;
    /**
     * 桌面剩余牌的信息
     */
    protected List<List<Pai>> pais = new ArrayList<List<Pai>>();
    protected ArrayList<Pai> allPais = new ArrayList<>();

    protected Rules rules;

    public AbstractPaiPool(Rules rules) {
        this.rules = rules;
    }


    @Override
    public void start() {
        this.rules.rest();
        int userMax = rules.getUserMax();
        pais.clear();
        for (int locationIndex = 0; locationIndex < userMax; locationIndex++) {
            pais.add(new ArrayList<>());
        }
        //发牌
        allPais.clear();
        allPais.addAll(rules.getAllPai());
        allPais.addAll(rules.getAllPai());
        allPais.addAll(rules.getAllPai());
        allPais.addAll(rules.getAllPai());
        disorder();
        mockCard();
        dispatchCard();
    }

    @Override
    public void mockCard() {
        int[][] mockCards = getMockCards();
        int userMax = rules.getUserMax();
        for (int locationIndex = 0; locationIndex < userMax; locationIndex++) {
            List<Pai> pais = this.pais.get(locationIndex);
            for (int i = 0; i < mockCards[locationIndex].length; i++) {
                int paiIndex = mockCards[locationIndex][i];
                Pai pai = Pai.fromIndex(paiIndex);
                int allPaisIndex = allPais.indexOf(pai);
                allPais.remove(allPaisIndex);
                pais.add(pai);
            }
        }

    }

    @Override
    public void dispatchCard() {
//        int len = allPais.size() - rules.getBaoliuLength();
        int userMax = rules.getUserMax();
//        for (int i = 0; i < len; i++) {
//            for (int locationIndex = 0; locationIndex < userMax; locationIndex++) {
//                pais.get(locationIndex).add(allPais.get(i));
//            }
//        }
        //int i = 0;
        while (allPais.size() > rules.getBaoliuLength()) {
            for (int locationIndex = 0; locationIndex < userMax; locationIndex++) {
                //pais.get(locationIndex).add(allPais.get(i));
                Pai remove = allPais.remove(allPais.size() - 1);
                pais.get(locationIndex).add(remove);
            }
        }
    }

    @Override
    public void disorder() {
        //乱序
        for (int i = 0; i < allPais.size(); i++) {
            int randomIndex = RandomUtils.nextInt(allPais.size());

            Pai temp = allPais.get(i);
            allPais.set(i, allPais.get(randomIndex));
            allPais.set(randomIndex, temp);
        }
//        initTest();
    }

    @Override
    public void clear() {
        pais.clear();
        allPais.clear();
    }

    //public Pai get(int index) {
//        return pais.get(index);
//    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < pais.size(); i++) {
            size += pais.get(i).size();
        }
        //return _pais.size();
        return size;
    }

    @Override
    public Pai getFreePai(int locationIndex) {
        //int index = _pais.size() - 1;
        //return index > -1 ? _pais.remove(index) : null;
        //int index = pais.get(locationIndex).size() - 1;
        int index = pais.get(locationIndex).size() > 0 ? 0 : -1;
        return index > -1 ? pais.get(locationIndex).remove(index) : null;
    }

    @Override
    public Pai getFreeGangPai(int locationIndex) {
//        for (int i = 0; i < _pais.size(); i++) {
//            Pai pai = _pais.get(i);
//            if (pai != null) {
//                _pais.set(i, null);
//                return pai;
//            }
//        }
//        return null;
//        Pai pai = pais.get(locationIndex).get(0);
//        if (pai != null) {
//            pais.get(locationIndex).set(0, null);
//            return pai;
//        }
//        return null;
        //int index = _pais.size() - 1;
        //return index > -1 ? _pais.remove(index) : null;
        //int index = pais.get(locationIndex).size() - 1;
        int index = pais.get(locationIndex).size() > 0 ? 0 : -1;
        return index > -1 ? pais.get(locationIndex).remove(index) : null;
    }

    private int[][] faPaiss;

    @Override
    public void faPai(int index, UserPlace userPlace) {
        if (faPaiss != null) {
            int[] faPais = faPaiss[index];
            for (int i = 0; i < 13; i++) {
                Pai pai = Pai.fromIndex(faPais[i]);
                if (!pais.get(index).remove(pai)) {
                    throw new RuntimeException("错误的牌" + pai + ",index:" + index + ",i:" + i);
                }
                userPlace.addShouPai(pai);
            }
        } else {
            for (int j = 0; j < 13; j++) {
                userPlace.addShouPai(getFreePai(index));
            }
        }

    }

    public int[][] getMockCards() {
//        int[][] result = new int[][]{
//                new int[]{0, 1, 2, 3, 4, 6, 6, 7, 7, 31, 31, 31, 31, 8, 8, 9, 7, 6},
//                new int[]{7, 6, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
//                new int[]{0, 1, 2, 3, 4, 5, 22, 22, 8, 20, 20, 20, 20},
//        };
//        return result;
//        int[][] result = new int[][]{//大四喜
//                new int[]{27, 27, 27, 28, 28, 28, 29, 29, 29, 30, 30, 30, 31, 31, 8, 9, 7, 6},
//                new int[]{7, 6, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
//                new int[]{0, 1, 2, 3, 4, 5, 22, 22, 8, 20, 20, 20, 20},
//        };
//        return result;
//        int[][] result = new int[][]{//十三幺
//                new int[]{27, 28, 29, 30, 31, 32, 33, 0, 8, 9, 17, 18, 26, 0, 8, 9, 7, 6},
//                new int[]{7, 6, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
//                new int[]{0, 1, 2, 3, 4, 5, 22, 22, 8, 20, 20, 20, 20},
//        };
//        return result;
        /*int[][] result = new int[][]{//十三幺
                new int[]{0, 0, 8, 8, 9, 9, 17, 17, 18,18, 26, 26, 30, 30, 8, 9, 7, 6},
                new int[]{7, 6, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
                new int[]{0, 1, 2, 3, 4, 5, 22, 22, 8, 20, 20, 20, 20},
        };
        return result;*/
        int[][] result = new int[][]{//一色四同顺
                new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 0, 0, 8, 9, 7, 6},
                new int[]{7, 6, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
                new int[]{0, 1, 2, 3, 4, 5, 22, 22, 8, 20, 20, 20, 20},
        };
        return result;
    }

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

    @Override
    public ArrayList<Pai> getLeftPai() {
        ArrayList<Pai> result = new ArrayList<>();
        for (int i = 0; i < rules.getUserMax(); i++) {
            result.addAll(pais.get(i));
        }
        return result;
    }

    @Override
    public Pai[] getHuiEr() {
        //计算会儿
        //return rules.getHuiEr(this);
        return null;
    }

    @Override
    public void onFaPaiEnd(MajiangChapter chapter) {
    }
}
