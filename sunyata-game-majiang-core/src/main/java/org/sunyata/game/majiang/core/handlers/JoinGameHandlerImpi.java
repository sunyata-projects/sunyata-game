package org.sunyata.game.majiang.core.handlers;

import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.GameJoinRoom;

/**
 * @author leo on 16/10/18.
 */
@Component(Commands.joinGameToScene)
public class JoinGameHandlerImpi implements MessageHandler<GameJoinRoom, SceneUser> {
    @Override
    public boolean handler(GameJoinRoom msg, SceneUser user) throws Exception {
        user.getRoom().joinGame(user);
        user.getRoom().chapterStart(user);
        return false;
    }
}
