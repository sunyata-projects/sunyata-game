package org.sunyata.game.majiang.core.models.majiang.actions.base;

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

import java.util.Arrays;
import java.util.List;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_PENG, operationWhen = OperationWhen.whenOtherChuPai)
public class PengAfterOtherChuPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult
            checkResult, Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_PENG)) {
            //operationCPGH.setIsPeng(checkResult.isPeng());
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_PENG).setPai(new int[]{chapter
                    .getCurrentChuPai().getIndex()}), pai, currentIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace userPlace, Pai pai, int index, int i) {
        boolean peng = userPlace.isPeng(pai);
        if (peng) {
            CheckResult result = new CheckResult(index, i).setActionName(OperationNames.OPT_PENG);
            return Arrays.asList(result);
        }
        return null;
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis) {
        chapter.peng(userPlace, pai);
    }
}
