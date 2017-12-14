package org.sunyata.game.majiang.core.models.majiang.actions.base;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.*;
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
@Action(value = OperationNames.OPT_XIAO_MING_GANG, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class XiaoMingGangAfterFaPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult,
                                       Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_XIAO_MING_GANG)) {
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_XIAO_MING_GANG).setPai(new
                    int[]{chapter.getCurrentChuPai().getIndex()}), pai, currentIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {

        List<Pai> pais = userPlace.checkXiaoMingGang(pai);
        if (pais != null && pais.size() > 0) {
            CheckResult result = new CheckResult(index, i).setActionName(OperationNames.OPT_XIAO_MING_GANG);
            Pai[] paiArrays = new Pai[pais.size()];
            paiArrays = pais.toArray(paiArrays);
            result.setPais(paiArrays);
            return Arrays.asList(result);
        }
        return null;
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis)
            throws InstantiationException, IllegalAccessException {
        chapter.xiaoMingGang(userPlace, pai);

    }
}
