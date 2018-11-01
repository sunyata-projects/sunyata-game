package org.sunyata.game.majiang.core.models.majiang.actions.base;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.*;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.MajiangAction;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;
import org.sunyata.game.majiang.core.models.message.OperationCPGHInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_CHI, operationWhen = OperationWhen.whenOtherChuPai)
public class ChiAfterOtherChuPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult
            checkResult, Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_CHI)) {
            //int[] ints = MajiangUtils.toIndexByDyadicArray(checkResult.getPais());
            List<Pai[]> pais = checkResult.getPais();
            for (int i = 0; i < pais.size(); i++) {
                int[] ints = MajiangUtils.toIndex(pais.get(i));
                if (ints != null && ints.length > 0) {//有吃的牌
                    operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI).setPai(ints), pai,
                            currentIndex);
                }
            }
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {
        int nextIndex = chapter.getNextIndex(chapter.getCurrentIndex());
        if (!Objects.equals(nextIndex, index)) {
            return null;
        }
        List<CheckResult> results = new ArrayList<>();
        if (chapter.getRules().allowChi()) {
            List<Pai[]> chi = userPlace.isChi(pai);
            if (chi != null && chi.size() > 0) {
                results.add(new CheckResult(index, i).setActionName(OperationNames.OPT_CHI).setPais(chi));
//                for (Pai[] paiArray : chi) {
//                    results.add(new CheckResult(index, i).setActionName(OperationNames.OPT_CHI).setPais(chi));
//
//                }
                return results;
            }
        }
        return null;
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis) {
        chapter.chi(userPlace, pai, chis);
    }
}
