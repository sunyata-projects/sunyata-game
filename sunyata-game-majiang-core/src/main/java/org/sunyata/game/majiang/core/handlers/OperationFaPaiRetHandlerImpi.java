package org.sunyata.game.majiang.core.handlers;

import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.OperationFaPaiRet;

/**
 * @author leo on 2016/10/31.
 */
@Component(Commands.optFaPaiRet)
public class OperationFaPaiRetHandlerImpi implements MessageHandler<OperationFaPaiRet, SceneUser> {
    @Override
    public boolean handler(OperationFaPaiRet msg, SceneUser user) throws Exception {
        user.getRoom().faPaiRet(user, msg);
        return false;
    }
}
