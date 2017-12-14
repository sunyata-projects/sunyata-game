package org.sunyata.game.majiang.core.handlers;

import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.OperationCPGHRet;

@Component(Commands.optCPGHRet)
public class OperationCPGHRetHandlerImpi implements MessageHandler<OperationCPGHRet, SceneUser> {
    @Override
    public boolean handler(OperationCPGHRet msg, SceneUser user) throws Exception {
        user.getRoom().cpghRet(user, msg);
        return false;
    }
}