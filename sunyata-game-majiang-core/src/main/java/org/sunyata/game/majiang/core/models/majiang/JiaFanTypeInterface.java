package org.sunyata.game.majiang.core.models.majiang;

public interface JiaFanTypeInterface {
    JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace userPlace, UserPaiInfo userPaiInfo);

    int getFan();

    String getName();
}
