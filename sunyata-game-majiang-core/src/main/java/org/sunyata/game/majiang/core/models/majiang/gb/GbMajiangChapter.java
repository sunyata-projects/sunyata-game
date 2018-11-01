package org.sunyata.game.majiang.core.models.majiang.gb;

import org.sunyata.game.majiang.core.models.majiang.AbstractMajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.ComputeFanInterface;
import org.sunyata.game.majiang.core.models.majiang.PaiPoolInterface;
import org.sunyata.game.majiang.core.models.majiang.Rules;
import org.sunyata.game.majiang.core.service.Room;

/**
 * 一局麻将的信息
 *
 * @author leo on 16/10/17.
 */
public class GbMajiangChapter extends AbstractMajiangChapter {
    public GbMajiangChapter(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface
            computeFan, GbJudgeHuService judgeHuService) {
        super(roomInfo, rule, paiPool, computeFan,judgeHuService);
    }
}
