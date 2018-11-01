package org.sunyata.game.majiang.core.models.majiang;

import java.util.ArrayList;

public interface JudgeHuService {
    boolean isHuPaiBy(UserPlace userPlace, Pai pai);

    boolean isSpecialHuPai(UserPlace userPlace, Pai pai);

    boolean isHuPai(UserPlace userPlace, boolean isHuihuiGang, ArrayList<Pai> all, Pai[] huiEr);
}
