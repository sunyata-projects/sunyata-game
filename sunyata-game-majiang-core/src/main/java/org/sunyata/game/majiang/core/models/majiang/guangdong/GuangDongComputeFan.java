package org.sunyata.game.majiang.core.models.majiang.guangdong;

import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
import org.sunyata.game.majiang.core.models.majiang.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author leo on 2017/1/18.
 */
public class GuangDongComputeFan implements ComputeFanInterface {
    private MajiangChapter chapter;
    private JudgeHuService judgeHuService;
    private ChapterEndResult endResult;

//    public GuangDongComputeFan(MajiangChapter chapter,
//                               int huPaiIndex, int fangPaoIndex, boolean isGangShangHua) {
//        this.chapter = chapter;
//        endResult = new ChapterEndResult();
//        endResult.setHuPai(huPaiIndex != -1);
//        endResult.setZhuangIndex(chapter.getZhuangIndex());
//        endResult.setHuPaiIndex(huPaiIndex);
//        boolean isZiMo = fangPaoIndex == -1;
//        endResult.setZiMo(isZiMo);
//        endResult.setGangShangHua(isGangShangHua);
//        endResult.setFangPaoIndex(fangPaoIndex);
//        endResult.setLastPai(chapter.isLastPai());
//        endResult.setLeft((ArrayList<Pai>) chapter.getLeftPai().clone());
//        endResult.setHuiEr(chapter.getHuiEr());
//        endResult.setUserPaiInfos(
//                Stream.of(chapter.getUserPlaces()).map(u -> new UserPaiInfo(
//                        chapter.getRules().getAllPai(), chapter.getHuiEr(), u,
//                        u.getLocationIndex() == huPaiIndex,
//                        u.getLocationIndex() == chapter.getZhuangIndex(),
//                        isZiMo
//                )).toArray(UserPaiInfo[]::new)
//        );
//    }

    public ComputeFanInterface build(MajiangChapter chapter,
                                     int huPaiIndex, int fangPaoIndex, boolean isGangShangHua, JudgeHuService
                                             judgeHuService) {
        this.chapter = chapter;
        this.judgeHuService = judgeHuService;
        endResult = new ChapterEndResult();
        endResult.setHuPai(huPaiIndex != -1);
        endResult.setZhuangIndex(chapter.getZhuangIndex());
        endResult.setHuPaiIndex(huPaiIndex);
        boolean isZiMo = fangPaoIndex == -1;
        endResult.setZiMo(isZiMo);
        endResult.setGangShangHua(isGangShangHua);
        endResult.setFangPaoIndex(fangPaoIndex);
        endResult.setLastPai(chapter.isLastPai());
        endResult.setLeft((ArrayList<Pai>) chapter.getLeftPai().clone());
        endResult.setHuiEr(chapter.getHuiEr());
        endResult.setUserPaiInfos(
                Stream.of(chapter.getUserPlaces()).map(u -> new UserPaiInfo(
                        chapter.getRules().getAllPai(), chapter.getHuiEr(), u,
                        u.getLocationIndex() == huPaiIndex,
                        u.getLocationIndex() == chapter.getZhuangIndex(),
                        isZiMo
                )).toArray(UserPaiInfo[]::new)
        );
        return this;
    }

    @Override
    public void executeScore(ChapterEndResult endResult) {
        int score = endResult.getFanNums();
        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        int huPaiIndex = endResult.getHuPaiIndex();
        int fangPaoIndex = endResult.getFangPaoIndex();
        for (int i = 0; i < userPaiInfos.length; i++) {
            userPaiInfos[i].setFan(score);
        }
        if (fangPaoIndex == -1) {
            for (int i = 0; i < userPaiInfos.length; i++) {
                if (i != huPaiIndex) {
                    userPaiInfos[i].setScore(-score);
                }
            }
            userPaiInfos[huPaiIndex].setScore(score * 3);
        } else {
            userPaiInfos[fangPaoIndex].setScore(-score);
            userPaiInfos[huPaiIndex].setScore(score);
        }
        computeGuaFengXiaYu();
    }

    public GuangDongComputeFan() {

    }

    public ChapterEndResult compute() {
        if (endResult.isHuPai()) {
            UserPlace[] userPlaces = chapter.getUserPlaces();

            UserPlace userPlace = userPlaces[endResult.getHuPaiIndex()];

            UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();

            computeUser(userPaiInfos[endResult.getHuPaiIndex()], userPlace);
        }
        return endResult;
    }

    private void computeUser(UserPaiInfo userPaiInfo, UserPlace userPlace) {
        FanResult[] fanResults = userPaiInfo.getFanResults();
        if (userPaiInfo.getFanResults() != null) {
            for (FanResult result : userPaiInfo.getFanResults()) {
                computeFanResult(result, endResult, userPlace, userPaiInfo);
            }
            Optional<FanResult> max = Arrays.stream(fanResults).max(Comparator.comparingInt(FanResult::getFan));
            userPaiInfo.setMaxFanResult(max.orElseGet(() -> null));
        }
    }

    private void computeFanResult(FanResult fanResult, ChapterEndResult chapteResult, UserPlace userPlace,
                                  UserPaiInfo userPaiInfo) {
        GuangDongBaseFanType baseFanType;
        GuangDongJudgeHuService guangDongJudgeHuService = (GuangDongJudgeHuService) judgeHuService;
        if (!userPlace.hasAllOut()) {
            baseFanType = userPaiInfo.isZhuang() ? GuangDongBaseFanType.TIAN_HU : GuangDongBaseFanType.DI_HU;
        } else if (userPlace.isHuiErGang(chapter.getRules().isHuiErGang(), chapteResult.getHuiEr())) {
            baseFanType = GuangDongBaseFanType.HUI_ER_GANG;

        } else if (guangDongJudgeHuService.isQiDui(userPlace)) {
            baseFanType = GuangDongBaseFanType.QI_DUI;
        } else if (fanResult.isDuiDuiHu(userPaiInfo)) {
            baseFanType = GuangDongBaseFanType.DUI_DUI_HU;
        } else if (userPaiInfo.isZiMo()) {
            baseFanType = GuangDongBaseFanType.ZI_MO;
        } else {
            baseFanType = GuangDongBaseFanType.JI_HU;
        }
        FanInfo baseFanInfo = GuangDongBaseFanType.getBaseFanMap().get(baseFanType);


        fanResult.setBaseFanName(baseFanType.getName());
        StringBuilder sb = new StringBuilder();

        int fan = baseFanInfo.getScore();
        sb.append(baseFanInfo.getName());
        for (Map.Entry<JiaFanTypeInterface, FanInfo> entry : GuangDongJiaFanType.jiaFanMap.entrySet()) {
            JiaFanTypeInterface jiaFan = entry.getKey();
            FanInfo jiaFanInfo = entry.getValue();
            JiaFanComputeResultInfo compute = jiaFan.compute(fanResult, chapteResult, userPlace, userPaiInfo);
            for (int i = 0; i < compute.getNums(); i++) {
                fanResult.getJiaFans().add(jiaFan);
                fan += jiaFanInfo.getScore();
            }
            if (compute.getNums() == 1) {
                sb.append(' ');
                sb.append(jiaFanInfo.getName());
            } else if (compute.getNums() > 2) {
                sb.append(' ');
                sb.append(jiaFanInfo.getName()).append("x").append(compute.getNums());
            }
        }
        fanResult.setFan(fan);
        fanResult.setFanString(sb.toString());
    }

    private void computeGuaFengXiaYu() {
        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        for (int i = 0; i < userPaiInfos.length; i++) {
            UserPaiInfo userPaiInfo = userPaiInfos[i];
            int score = userPaiInfo.getAnGang().size() * 2
                    + userPaiInfo.getDaMingGang().size()
                    + userPaiInfo.getXiaoMingGang().size();

            for (int j = 0; j < userPaiInfos.length; j++) {
                UserPaiInfo ohterUserPaiInfo = userPaiInfos[j];
                if (i != j) {
                    ohterUserPaiInfo.setScore(ohterUserPaiInfo.getScore() - score);
                    ohterUserPaiInfo.setGuaFengXiaYu(ohterUserPaiInfo.getGuaFengXiaYu() - score);
                    userPaiInfo.setScore(userPaiInfo.getScore() + score);
                    userPaiInfo.setGuaFengXiaYu(userPaiInfo.getGuaFengXiaYu() + score);
                }
            }
        }
    }


//    private int computeJiaFan(GuangDongJiaFanType jiaFan) {
//        FanInfo fanInfo = chapter.getRules().getJiaFanMap().get(jiaFan);
//        return fanInfo == null ? 0 : fanInfo.getScore();
//    }

    public int zaMa() {
        return zaMaScore();
    }

    private int zaMaScore() {
        Rules rules = chapter.getRules();
        PaiPoolInterface paiPool = chapter.getPaiPool();
        if (rules.getConfig().getInt(RoomConfigInfo.MAI_MA) != 0) {

            endResult.setZaMaType(rules.getConfig().getInt(RoomConfigInfo.MAI_MA));
            int zaMa = rules.getConfig().getInt(RoomConfigInfo.MAI_MA);
            if (zaMa == -1) {

                Pai freePai = paiPool.getFreePai(chapter.getCurrentIndex());
                int zamaScore = zaMaYIMa(freePai);
                endResult.setZaMaPai(new int[]{freePai.getIndex()});
                endResult.setZaMaFan(zamaScore);
                return zamaScore;
            } else {
                int zamaScore = 0;
                List<Pai> zaMaPai = new ArrayList<>();
                for (int i = 0; i < zaMa; i++) {
                    Pai freePai = paiPool.getFreePai(chapter.getCurrentIndex());
                    if (freePai != null) {
                        if (freePai.getDian() == 1 ||
                                freePai.getDian() == 5 ||
                                freePai.getDian() == 9 || freePai.equals(Pai.SANYUAN_ZHONG)) {
                            zaMaPai.add(freePai);
                            zamaScore += 2;
                        }
                    }
                }
                endResult.setZaMaPai(zaMaPai.stream().mapToInt(Pai::getIndex).toArray());
                endResult.setZaMaFan(zamaScore);
                return zamaScore;
            }
        }
        return 0;
    }

    private int zaMaYIMa(Pai freePai) {
        if (freePai != null) {
            //  //一码全中一万   一筒    一条   就是一番一直到九万  九筒   九条  是9番      中  发   白是10番    最高10番
            if (PaiType.SANYUAN.equals(freePai.getType())) {
                return 10;
            } else {
//                if (freePai.getDian() == 1) {
//                    return 10;
//                }
                return freePai.getDian();
            }
        }
        return 0;
    }
}
