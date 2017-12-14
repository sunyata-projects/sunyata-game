package org.sunyata.game.majiang.core.models.majiang.actions;

import org.sunyata.game.majiang.core.models.majiang.CheckResult;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.UserPlace;
import org.sunyata.game.majiang.core.models.message.OperationCPGH;

import java.util.List;

/**
 * Created by leo on 17/11/28.
 */
public interface MajiangAction {
    //String getActionName();

    //OperationWhen getOperationWhen();

    void convertToOperationCPGH(MajiangChapter chapter, OperationCPGH operationCPGH, CheckResult checkResult, Pai pai, int currentIndex);

    List<CheckResult> generationAction(List<CheckResult> checkResults, MajiangChapter chapter, UserPlace userPlace, Pai pai,
                                       int index, int i);

    void processAction(MajiangChapter majiangChapter, UserPlace userPlace, Pai pai, int locationIndex, int[] chis) throws Exception;
}
