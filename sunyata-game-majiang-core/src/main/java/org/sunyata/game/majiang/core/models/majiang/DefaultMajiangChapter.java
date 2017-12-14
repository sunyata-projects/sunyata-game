package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.majiang.core.service.Room;

/**
 * Created by leo on 17/11/28.
 */
public class DefaultMajiangChapter extends AbstractMajiangChapter {

    public DefaultMajiangChapter(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface computeFan) {
        super(roomInfo, rule, paiPool, computeFan);
    }
}
