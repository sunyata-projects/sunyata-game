package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.server.message.Message;
import org.sunyata.game.majiang.core.models.NoticeType;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.MajiangChapterMsg;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;
import org.sunyata.game.majiang.core.models.message.OperationFaPaiNew;
import org.sunyata.game.majiang.core.service.Room;

import java.util.ArrayList;

/**
 * 一局麻将的信息
 *
 * @author leo on 16/10/17.
 */
public interface MajiangChapter {


    void start();

    void startNext() throws IllegalAccessException, InstantiationException;

    void faPai(boolean isSendMessage, boolean isGang) throws InstantiationException, IllegalAccessException;

    void outRet(int locationIndex, int paiIndex) throws InstantiationException, IllegalAccessException, Exception;

    void faPaiRet(int locationIndex, String opt, int paiIndex) throws IllegalAccessException, InstantiationException,
            Exception;

    void cpghRet(int locationIndex, String opt, int[] chi) throws IllegalAccessException, InstantiationException,
            Exception;

    Rules getRules();


    MajiangChapterMsg toMessage(int myLocationIndex);

    void sendMessage(int locationIndex, Message msg);

    void sync(String opt, Pai... pais);


    void syncChi(String opt, Pai pai, int... pais);

    void sync(String opt, int... pais);

    boolean isOperationTimeOut(long now);

    void syncOptTime();


    /**
     * 对其他用户隐藏牌的同步方式
     */
    void syncHidePai(String opt, int... pais);

    boolean isStart();

    ArrayList<Pai> getLeftPai();

    int getZhuangIndex();

    UserPlace[] getUserPlaces();


    Pai[] getHuiEr();

    /**
     * 是否最后一张牌
     */
    boolean isLastPai();


    void updateUser(SceneUser sceneUser);

    int getChapterNums();

    int getLeftChapterNums();

    PaiPoolInterface getPaiPool();

    Room getRoom();

    void stopCPGH(boolean isSync);

    void huPai(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex, boolean isGangShangHua);

    void daMingGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException;

    void peng(UserPlace userPlace, Pai pai);

    void chi(UserPlace userPlace, Pai pai, int[] chi);

    void nextCPGHOrStartNext() throws InstantiationException, IllegalAccessException;

    void chuPai(UserPlace userPlace, int locationIndex, Pai pai) throws Exception;

    void xiaoMingGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException;

    void anGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException;

    OperationFaPaiNew getOperationFaPai();

    int getWaitCurrentIndex();

    boolean getOperationFaPaiIsGang();

    Pai getCurrentChuPai();

    OperationCPGH getOperationCPGH();

    int getNextIndex(int curIndex);

    void noticeError(int locationIndex, String key, String[] args);

    void notice(int locationIndex, String key, String[] args, NoticeType noticeType);
}
