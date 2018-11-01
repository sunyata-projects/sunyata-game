package org.sunyata.game.majiang.core.models.majiang.guangdong;

import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.models.majiang.JudgeHuService;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapterFactory;
import org.sunyata.game.majiang.core.models.majiang.Rules;
import org.sunyata.game.majiang.core.service.Room;

/**
 * Created by leo on 17/11/27.
 */
@Component("factory-zhongyouGD")
public class GuangdongMajiangChapterFactory extends MajiangChapterFactory {
    @Override
    public MajiangChapter createChapter(Room roomInfo) {
        Rules rule = Rules.createRules(Rules.guangDong, roomInfo.getConfig());
        JudgeHuService judgeHuService = new GuangDongJudgeHuService(rule);
        GuangDongComputeFan guangDongComputeFan = new GuangDongComputeFan();
        return new GuangDongMajiangChapter(roomInfo, rule, new GuangDongPaiPool(rule), guangDongComputeFan,
                judgeHuService);
    }
}
