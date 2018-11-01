package org.sunyata.game.majiang.core.models.majiang.jiamusi;

import org.sunyata.game.majiang.core.models.majiang.AbstractPaiPool;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.majiang.Pai;
import org.sunyata.game.majiang.core.models.majiang.Rules;

/**
 * 发牌的池子
 *
 * @author leo on 2017/11/4.
 */
public class JiamusiPaiPool extends AbstractPaiPool {

    public JiamusiPaiPool(Rules rules) {
        super(rules);
//        this.rules = rules;
    }

    @Override
    public void onFaPaiEnd(MajiangChapter chapter) {
        huiEr = new Pai[]{this.getFreePai(chapter.getCurrentIndex())};
    }
}
