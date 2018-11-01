package org.sunyata.game.majiang.core.models.majiang.actions;

import org.sunyata.game.majiang.core.models.majiang.CheckResult;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.UserPlace;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by leo on 17/11/28.
 */
public class MajiangActionManager {
    //    public static void generateOperation(List<CheckResult> checkResults, OperationCPGH operationCPGH) {
//
//    }
    public static void convertToOperationCPGH(OperationWhen operationWhen, MajiangChapter chapter, List<Class<?
            extends MajiangAction>> actionMap, OperationCPGH operationCPGH, CheckResult checkResult, Pai pai, int
                                                      currentIndex) throws IllegalAccessException,
            InstantiationException {
        List<Class<? extends MajiangAction>> collect = actionMap.stream().filter(p -> p.getAnnotation(Action.class)
                != null && p.getAnnotation(Action.class).operationWhen() == operationWhen).collect(Collectors.toList());
        for (Class<? extends MajiangAction> actionClass : collect) {
            MajiangAction majiangAction = actionClass.newInstance();
            majiangAction.convertToOperationCPGH(chapter, operationCPGH, checkResult, pai, currentIndex);
        }
    }

    public static List<CheckResult> generateOperation(OperationWhen operationWhen, MajiangChapter chapter,
                                                      List<Class<? extends MajiangAction>> actionMap,
                                                      UserPlace userPlace, Pai pai,
                                                      int index, int i) throws IllegalAccessException,
            InstantiationException {

        List<CheckResult> checkResults = new ArrayList<>();
        List<Class<? extends MajiangAction>> collect = actionMap.stream().filter(p -> p.getAnnotation
                (Action.class) != null && p.getAnnotation(Action.class)
                .operationWhen() == operationWhen).collect(Collectors.toList());
        for (Class<? extends MajiangAction> actionClass : collect) {
            MajiangAction majiangAction = actionClass.newInstance();
            List<CheckResult> checkResultsRet = majiangAction.generationAction(checkResults, chapter, userPlace, pai,
                    index, i);
            if (checkResultsRet != null) {
                checkResults.addAll(checkResultsRet);
            }
        }
        return checkResults;
        //checkResult排序
    }
}
