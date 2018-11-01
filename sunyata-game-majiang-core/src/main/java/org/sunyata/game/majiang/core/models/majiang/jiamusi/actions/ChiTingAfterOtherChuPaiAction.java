package org.sunyata.game.majiang.core.models.majiang.jiamusi.actions;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.*;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.MajiangAction;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;
import org.sunyata.game.majiang.core.models.message.OperationCPGHInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/11/29.
 */
@Action(value = OperationNames.OPT_CHI_TING, operationWhen = OperationWhen.whenOtherChuPai)
public class ChiTingAfterOtherChuPaiAction implements MajiangAction {
    @Override
    public void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult,
                                       Pai pai, int currentIndex) {
        if (checkResult.getActionName().equals(OperationNames.OPT_CHI_TING)) {
            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI_TING).setPai(new int[]{chapter
                    .getCurrentChuPai().getIndex()}), pai, currentIndex);
        }
    }

    @Override
    public List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace
            userPlace, Pai pai, int index, int i) {
        List<CheckResult> results = new ArrayList<>();
        boolean allowChi = chapter.getRules().allowChi();
        CheckResult huResult = checkResults.stream().filter(p -> p.getActionName().equals(OperationNames.OPT_HU))
                .findFirst().orElse(null);
        if (huResult != null) {//如果胡牌则不进行此判断
            return null;
        }
        if (allowChi) {
            List<Pai[]> chiList = userPlace.isChi(pai);
            int nextIndex = chapter.getNextIndex(index);
            if (chiList.size() > 0) {//可以吃
                if (userPlace.getLocationIndex() != nextIndex) {//当前出牌人的下家不为userPlace,才可以吃听
                    //pai+userPlace.getShouPai - 任意一张牌,可胡
                    for (Pai[] chiPais : chiList) {
                        List<Pai> shouPaiCopy = new ArrayList<>(userPlace.getShouPaiList());
                        for (Pai chi : chiPais) {//把吃的牌放在门前
                            if (chi != pai) {
                                shouPaiCopy.remove(chi);
                            }
                        }
                        //shouPaiCopy如果移除任何一张,即可以听牌,则认为是吃听
                        for (int pos = 0; pos < shouPaiCopy.size(); pos++) {//试探打出哪一张可以听
                            Pai remove = shouPaiCopy.remove(pos);
                            ArrayList<Pai> tingPais = checkTing(chapter, chapter.getRules().getAllPai(), shouPaiCopy);
                            if (tingPais.size() > 0) {
                                ArrayList<Pai[]> pais = new ArrayList<>();
                                pais.add(chiPais);
                                CheckResult result = new CheckResult(index, i).setActionName(OperationNames
                                        .OPT_CHI_TING).setPais(pais).setSuggest(new int[]{remove.getIndex()});
                                results.add(result);
                            }

                        }
                    }
                }
            }
        }
        return results;
    }

    private ArrayList<Pai> checkTing(MajiangChapter chapter, ArrayList<Pai> allPai, List<Pai> shouPaiCopy) {
        ArrayList<Pai> tingPais = new ArrayList<>();

        ArrayList<Pai> testPais = new ArrayList<>(shouPaiCopy);
        testPais.add(Pai.FENG_BEI);
        int shuPaiLen = shouPaiCopy.size();

        for (int i = 0; i < allPai.size(); i++) {
            Pai pai = allPai.get(i);
            testPais.set(shuPaiLen, pai);
//            if (isQiDui(pai)) {
//                tingPais.add(pai);
            //    } else
            if (AgariUtils.isHuiErHuPai(allPai, testPais, chapter.getHuiEr())) {
                tingPais.add(pai);
            }
        }
        return tingPais;
    }

    @Override
    public void processAction(MajiangChapter majiangChapter, UserPlace userPlace, Pai pai, int locationIndex, int[]
            chis) throws InstantiationException, IllegalAccessException {
        OperationCPGHInfo operationInfo = majiangChapter.getOperationCPGH().getOperationInfo(OperationNames
                .OPT_CHI_TING);
        if (operationInfo != null) {
            majiangChapter.chi(userPlace, pai, chis);
            Ting ting = (Ting) majiangChapter;
            ting.setIsTing(true);
        }
    }
}
