package org.sunyata.game.majiang.core.models.majiang.guangdong;

import org.sunyata.game.majiang.core.models.majiang.*;
import org.sunyata.game.majiang.core.service.Room;

/**
 * 一局麻将的信息
 *
 * @author leo on 16/10/17.
 */
public class GuangDongMajiangChapter extends AbstractMajiangChapter {
    public GuangDongMajiangChapter(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface
            computeFan, JudgeHuService judgeHuService) {
        super(roomInfo, rule, paiPool, computeFan, judgeHuService);
    }
}
