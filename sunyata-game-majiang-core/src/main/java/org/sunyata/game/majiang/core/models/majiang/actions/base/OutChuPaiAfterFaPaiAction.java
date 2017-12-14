package org.sunyata.game.majiang.core.models.majiang.actions.base;

import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;

/**
 * Created by leo on 17/11/28.
 */
@Action(value = OperationNames.OPT_OUT, operationWhen = OperationWhen.chuPaiAfterFaPai)
public class OutChuPaiAfterFaPaiAction extends OutAfterOtherChuPaiAction {
}
