package org.sunyata.game.majiang.core.handlers;

import org.springframework.stereotype.Component;
import org.sunyata.game.contract.Commands;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.message.ChapterStartMsg;

/**
 * @author leo on 16/10/18.
 */
@Component(Commands.gameChapterStart)
public class GameStartHandlerImpi implements MessageHandler<ChapterStartMsg, SceneUser> {
    @Override
    public boolean handler(ChapterStartMsg msg, SceneUser user) throws Exception {
        user.getRoom().chapterStart(user);
        return false;
    }
}
