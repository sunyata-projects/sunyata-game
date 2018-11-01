package org.sunyata.game.majiang.core.models.majiang.actions.base;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.CheckResult;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.UserPlace;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;
import org.sunyata.game.majiang.core.models.message.OperationCPGHInfo;
import org.sunyata.game.majiang.core.models.message.OperationFaPaiNew;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_HU, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class HuAfterFaPaiAction extends HuAfterOtherChuPaiAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult,
                                       Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_HU)) {
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_HU).setPai(new int[]{pai.getIndex()})
                    , pai, currentIndex);
        }
    }

    @Override
    public void processAction(MajiangChapter chapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis)
            throws IllegalAccessException, InterruptedException, InstantiationException {
        OperationFaPaiNew prev = chapter.getOperationPrevFaPai();
        if (prev.getOperationInfo(OperationNames.OPT_HU) != null) {
            chapter.huPai(userPlace, locationIndex, pai, -1, chapter.getOperationFaPaiIsGang());
        } else {
            throw new RuntimeException("未允许操作！" + OperationNames.OPT_HU + ",locationIndex:" + locationIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {
        boolean huPai = userPlace.isHuPai(false, chapter.getRules().getAllPai(), chapter.getHuiEr());
        if (huPai) {
            CheckResult checkResult = new CheckResult(index, i).setActionName(OperationNames.OPT_HU).setPais(null);
            return Arrays.asList(checkResult);
        }

//        if (chapter.getRules().allowFangPao()) {
//            boolean huPaiBy = userPlace.isHuPaiBy(pai);
//            if (huPaiBy) {
//                CheckResult checkResult = new CheckResult(index, i).setActionName(OperationNames.OPT_HU).setPais
// (null);
//                return Arrays.asList(checkResult);
//            }
//            //checkResult.setHu(current.isHuPaiBy(pai));
//        }
        return null;
    }
}
