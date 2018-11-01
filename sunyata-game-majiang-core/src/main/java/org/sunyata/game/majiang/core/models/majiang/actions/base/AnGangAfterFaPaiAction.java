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
import org.sunyata.game.majiang.core.models.message.OperationFaPaiNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_AN_GANG, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class AnGangAfterFaPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult,
                                       Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_AN_GANG)) {
            List<Pai[]> pais = checkResult.getPais();
            if (pais.size() > 0) {
                for (Pai[] paisArray : pais) {
//                    Pai[] anGangPais = pais.get(0);
                    operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_AN_GANG).setPai(new
                                    int[]{paisArray[0].getIndex()}), pai,
                            currentIndex);
                }
            }
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {
        int[] anGang = userPlace.checkAnGang(pai);
        List<CheckResult> results = new ArrayList<>();
        if (anGang != null && anGang.length > 0) {
            List<Pai[]> pais = new ArrayList<Pai[]>();
            CheckResult result = new CheckResult(index, i).setActionName(OperationNames.OPT_AN_GANG);
            for (int anAnGang : anGang) {
                Pai[] pais1 = {Pai.fromIndex(anAnGang)};
                pais.add(pais1);
            }
            result.setPais(pais);
            results.add(result);
        }
        return results;
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis)
            throws InstantiationException, IllegalAccessException {
        OperationFaPaiNew prev = chapter.getOperationPrevFaPai();
        //if (!ArrayUtils.isEmpty(prev.getAnGang())) {
        if (prev.getOperationInfo(OperationNames.OPT_AN_GANG) != null) {
            chapter.anGang(userPlace, pai);
        } else {
            throw new RuntimeException("未允许操作！" + OperationNames.OPT_AN_GANG + ",locationIndex:" + locationIndex);
        }
    }
}
