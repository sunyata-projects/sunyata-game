package org.sunyata.game.majiang.core.models.majiang.gb;

import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapterFactory;
import org.sunyata.game.majiang.core.models.majiang.Rules;
import org.sunyata.game.majiang.core.service.Room;

/**
 * Created by leo on 17/11/27.
 */
@Component("factory-gb")
public class GbMajiangChapterFactory extends MajiangChapterFactory {
    @Override
    public MajiangChapter createChapter(Room roomInfo) {
        Rules rule = Rules.createRules(Rules.gb, roomInfo.getConfig());
        GbJudgeHuService judgeHuService = new GbJudgeHuService(rule);
        GbComputeFan gbComputeFan = new GbComputeFan();
        return new GbMajiangChapter(roomInfo, rule, new GbPaiPool(rule), gbComputeFan,judgeHuService);
    }

}
