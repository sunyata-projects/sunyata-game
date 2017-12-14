package org.sunyata.game.majiang.core.models.majiang.jiamusi;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.*;
import org.sunyata.game.majiang.core.models.majiang.jiamusi.actions.ChiTingAfterOtherChuPaiAction;
import org.sunyata.game.majiang.core.service.Room;

import java.util.Arrays;

/**
 * 一局麻将的信息
 *
 * @author leo on 16/10/17.
 */
public class JiamusiMajiangChapter extends AbstractMajiangChapter implements Ting {

    public JiamusiMajiangChapter(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface computeFan) {
        super(roomInfo, rule, paiPool, computeFan);
    }

    @Override
    protected void initProcessMap() {
        super.initProcessMap();
        this.actionMap.add(ChiTingAfterOtherChuPaiAction.class);
    }

    @Override
    public String toString() {
        return "MajiangChapter{" +
                "userPlaces=" + Arrays.toString(userPlaces) +
                ", paiPool=" + paiPool +
                ", shouIndex=" + shouIndex +
                ", currentIndex=" + currentIndex +
                ", zhuangIndex=" + zhuangIndex +
                ", huiEr=" + huiEr +
                ", rules=" + rules +
                '}';
    }

    @Override
    public void faPaiRet(int locationIndex, String opt, int paiIndex) throws Exception {
        if (ting) {//如果当前听牌
            //只能摸啥打啥
            if (!OperationNames.OPT_OUT.equals(opt)) {
                if (this.getOperationFaPai().getPai() != paiIndex) {
                    noticeError(locationIndex, "只能发啥打啥", null);
                    return;
                }
            }
        }
        super.faPaiRet(locationIndex, opt, paiIndex);
    }

    private boolean ting;

    @Override
    public boolean isTing() {
        return ting;
    }

    @Override
    public void setIsTing(boolean t) {
        this.ting = t;
    }
}
