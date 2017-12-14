package org.sunyata.game.majiang.core.models.majiang;

import java.util.ArrayList;

/**
 * 发牌的池子
 *
 * @author leo on 2016/11/4.
 */
public interface PaiPoolInterface {

    void start();

    void clear();

    Pai get(int index);

    int size();

    Pai getFreePai();

    Pai getFreeGangPai();


    void faPai(int index, UserPlace userPlace);


    ArrayList<Pai> getLeftPai();

    Pai[] getHuiEr();

    void onFaPaiEnd();

}
