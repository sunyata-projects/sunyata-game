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

import java.util.ArrayList;
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
            List<Pai[]> paisList = checkResult.getPais();
            if (paisList != null && paisList.size() > 0) {
                Pai[] paisArray = paisList.get(0);
                for (Pai xiaomaingGangPai : paisArray) {
                    operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_XIAO_MING_GANG).setPai(new
                            int[]{xiaomaingGangPai.getIndex()}), pai, currentIndex);
                }
            }
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
            ArrayList<Pai[]> pais1 = new ArrayList<>();
            pais1.add(paiArrays);
            result.setPais(pais1);
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
