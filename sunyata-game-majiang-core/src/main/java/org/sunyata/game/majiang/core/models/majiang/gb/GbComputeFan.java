package org.sunyata.game.majiang.core.models.majiang.gb;

import org.sunyata.game.majiang.core.models.majiang.*;
import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author leo on 2017/1/18.
 */
public class GbComputeFan implements ComputeFanInterface {
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

    final int baseScore = 8;

    @Override
    public void executeScore(ChapterEndResult endResult) {
        int score = endResult.getFanNums();
        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        int huPaiIndex = endResult.getHuPaiIndex();
        int fangPaoIndex = endResult.getFangPaoIndex();
        //设置番数
        for (int i = 0; i < userPaiInfos.length; i++) {
            userPaiInfos[i].setFan(score);//设置番数
        }
        //设置分数
        if (fangPaoIndex == -1) {//自摸
            for (int i = 0; i < userPaiInfos.length; i++) {
                if (i != huPaiIndex) {
                    userPaiInfos[i].setScore(-(score + baseScore));
                }
            }
            userPaiInfos[huPaiIndex].setScore((score + baseScore) * 3);
        } else {//别人点炮
            for (int i = 0; i < userPaiInfos.length; i++) {
                if (i != huPaiIndex && i != fangPaoIndex) {
                    userPaiInfos[i].setScore(-baseScore);
                } else if (i == huPaiIndex) {
                    userPaiInfos[huPaiIndex].setScore((baseScore * 3) + score);
                } else if (i == fangPaoIndex) {
                    userPaiInfos[fangPaoIndex].setScore(-(score + baseScore));
                }
            }
        }
        computeGuaFengXiaYu();
    }

    public GbComputeFan() {

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
        GbBaseFanType baseFanType;

//        if (!userPlace.hasAllOut()) {
//            baseFanType = userPaiInfo.isZhuang() ? GbBaseFanType.TIAN_HU : GbBaseFanType.DI_HU;
//        } else if (userPlace.isHuiErGang(chapter.getRules().isHuiErGang(), chapteResult.getHuiEr())) {
//            baseFanType = GbBaseFanType.HUI_ER_GANG;
////        } else if (userPlace.isQiDui()) {
////            baseFanType = GbBaseFanType.QI_DUI;
//        } else if (fanResult.isDuiDuiHu(userPaiInfo)) {
//            baseFanType = GbBaseFanType.DUI_DUI_HU;
//        } else if (userPaiInfo.isZiMo()) {
//            baseFanType = GbBaseFanType.ZI_MO;
//        } else {
//            baseFanType = GbBaseFanType.JI_HU;
//        }

        //FanInfo baseFanInfo = GbBaseFanType.getBaseFanMap().get(baseFanType);


        //fanResult.setBaseFanName(baseFanType.getName());
        StringBuilder sb = new StringBuilder();

        //int fan = baseFanInfo.getScore();
        //sb.append(baseFanInfo.getName());

        int fan = 0;
        List<JiaFanComputeResultInfo> computeResultInfos = new ArrayList<>();
        for (Map.Entry<JiaFanTypeInterface, FanInfo> entry : GbJiaFanType.getJiaFanMap().entrySet()) {
            JiaFanTypeInterface jiaFan = entry.getKey();
            FanInfo jiaFanInfo = entry.getValue();
            JiaFanComputeResultInfo compute = jiaFan.compute(fanResult, chapteResult, userPlace, userPaiInfo);
            if (compute != null && compute.getNums() > 0) {
                compute.setJiaFan(jiaFan);
                compute.setJiaFanInfo(jiaFanInfo);
                compute.setDisable(false);
                computeResultInfos.add(compute);
            }
        }
        //对不计进行处理
        for (JiaFanComputeResultInfo computeResultInfo : computeResultInfos) {
            List<JiaFanTypeInterface> withOuts = computeResultInfo.getWithOuts();
            if (withOuts != null) {
                for (JiaFanTypeInterface jiaFanTypeInterface : withOuts) {
                    JiaFanComputeResultInfo withOut = computeResultInfos.stream().filter(p -> p.getJiaFan()
                            .getName().equalsIgnoreCase(jiaFanTypeInterface
                                    .getName())).findFirst().orElse(null);
                    if (withOut != null) {
                        withOut.setDisable(true);
                    }
                }
            }
        }
        for (JiaFanComputeResultInfo computeResultInfo : computeResultInfos) {
            if (computeResultInfo.isDisable()) {
                continue;
            }
            for (int i = 0; i < computeResultInfo.getNums(); i++) {
                fanResult.getJiaFans().add(computeResultInfo.getJiaFan());
                fan += computeResultInfo.getJiaFanInfo().getScore();
            }
            if (computeResultInfo.getNums() == 1) {
                sb.append(' ');
                sb.append(computeResultInfo.getJiaFanInfo().getName());
            } else if (computeResultInfo.getNums() > 2) {
                sb.append(' ');
                sb.append(computeResultInfo.getJiaFanInfo().getName()).append("x").append(computeResultInfo.getNums());
            }
        }

        fanResult.setFan(fan);
        fanResult.setFanString(sb.toString());
    }

    private void computeGuaFengXiaYu() {
//        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
//        for (int i = 0; i < userPaiInfos.length; i++) {
//            UserPaiInfo userPaiInfo = userPaiInfos[i];
//            int score = userPaiInfo.getAnGang().size() * 2
//                    + userPaiInfo.getDaMingGang().size()
//                    + userPaiInfo.getXiaoMingGang().size();
//
//            for (int j = 0; j < userPaiInfos.length; j++) {
//                UserPaiInfo ohterUserPaiInfo = userPaiInfos[j];
//                if (i != j) {
//                    ohterUserPaiInfo.setScore(ohterUserPaiInfo.getScore() - score);
//                    ohterUserPaiInfo.setGuaFengXiaYu(ohterUserPaiInfo.getGuaFengXiaYu() - score);
//                    userPaiInfo.setScore(userPaiInfo.getScore() + score);
//                    userPaiInfo.setGuaFengXiaYu(userPaiInfo.getGuaFengXiaYu() + score);
//                }
//            }
//        }
    }

//
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
