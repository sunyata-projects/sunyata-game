package org.sunyata.game.majiang.core.models.majiang;

import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.models.majiang.guangdong.GuangDongComputeFan;
import org.sunyata.game.majiang.core.models.majiang.guangdong.GuangDongMajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.guangdong.GuangDongPaiPool;
import org.sunyata.game.majiang.core.service.Room;

/**
 * Created by leo on 17/11/27.
 */
@Component
public class MajiangChapterFactory {
    public static MajiangChapter createMajiangChapter(Room roomInfo, String rules) {
        Rules rule = Rules.createRules(rules, roomInfo.getConfig());
        GuangDongComputeFan guangDongComputeFan = new GuangDongComputeFan();
        MajiangChapter guangDongMajiangChapter = new GuangDongMajiangChapter(roomInfo, rule, new GuangDongPaiPool(rule),
                guangDongComputeFan);
        return guangDongMajiangChapter;
    }
}
