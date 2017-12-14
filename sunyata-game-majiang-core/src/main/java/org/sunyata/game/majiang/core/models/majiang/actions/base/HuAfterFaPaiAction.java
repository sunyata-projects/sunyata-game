package org.sunyata.game.majiang.core.models.majiang.actions.base;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.UserPlace;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.message.OperationFaPaiNew;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_HU, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class HuAfterFaPaiAction extends HuAfterOtherChuPaiAction {
    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis) {
        OperationFaPaiNew prev = chapter.getOperationFaPai();
        if (prev.getOperationInfo(OperationNames.OPT_HU) != null) {
            chapter.huPai(userPlace, locationIndex, pai, -1, chapter.getOperationFaPaiIsGang());
        } else {
            throw new RuntimeException("未允许操作！" + OperationNames.OPT_HU + ",locationIndex:" + locationIndex);
        }
    }
}
