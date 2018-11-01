package org.sunyata.game.majiang.core.models.majiang;

/**
 * @author leo on 2017/1/18.
 */
public interface ComputeFanInterface {
    ChapterEndResult compute();

    //void computeGuaFengXiaYu();

//    public int zaMa();

    ComputeFanInterface build(MajiangChapter guangDongMajiangChapter, int huPaiLocationIndex, int fangPaoIndex, boolean
            isGangShangHua,JudgeHuService judgeHuService);

    void executeScore(ChapterEndResult endResult);
}
