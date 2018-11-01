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
@Action(value = OperationNames.OPT_DA_MING_GANG, operationWhen = OperationWhen.whenOtherChuPai)
public class DaMingGangAfterOtherChuPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult
            checkResult, Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_DA_MING_GANG)) {
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_DA_MING_GANG).setPai(new
                    int[]{pai.getIndex()}).setSuggest(checkResult.getSuggest()), pai, currentIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {
        boolean daMingGang = userPlace.isDaMingGang(pai);
        if (daMingGang) {
            CheckResult result = new CheckResult(index, i).setActionName(OperationNames.OPT_DA_MING_GANG);
//            List<Pai[]> r = new ArrayList<>();
//            r.add(new Pai[]{pai});
//            result.setPais(r);
            result.setPais(null);
            return Arrays.asList(result);
        }
        return null;
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis)
            throws InstantiationException, IllegalAccessException {
        chapter.daMingGang(userPlace, pai);
    }
}
