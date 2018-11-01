package org.sunyata.game.majiang.core.handlers;

import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.OperationOutRet;

/**
 * @author leo on 2017/10/31.
 */
@Component(Commands.optOutRet)
public class OperationOutRetHandlerImpi implements MessageHandler<OperationOutRet, SceneUser> {
    @Override
    public boolean handler(OperationOutRet msg, SceneUser user) throws Exception {
        user.getRoom().outRet(user, msg);
        return false;
    }
}
