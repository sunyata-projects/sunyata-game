package org.sunyata.game.majiang.core.models.majiang.jiamusi.actions;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.CheckResult;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.UserPlace;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.MajiangAction;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;
import org.sunyata.game.majiang.core.models.message.OperationCPGHInfo;

import java.util.List;

/**
 * Created by leo on 17/11/29.
 */
@Action(value = OperationNames.OPT_CHI_TING, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class TingChuPaiAfterFaPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult,
                                       Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_CHI_TING)) {
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI_TING).setPai(new int[]{chapter
                    .getCurrentChuPai().getIndex()}), pai, currentIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace userPlace, Pai pai, int index, int i) {
        return null;
    }

    @Override
    public void processAction(MajiangChapter majiangChapter, UserPlace userPlace, Pai pai, int locationIndex, int[]
            chis) throws InstantiationException, IllegalAccessException {

    }
}
