package org.sunyata.game.majiang.core.models.majiang.gb;

import org.sunyata.game.majiang.core.models.majiang.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 叠加番	1	庄胡	庄家胡牌
 * 1	自摸	自摸胡牌
 * 2	无“混儿”	牌中无“混儿”
 * 3	一条龙	和牌时手牌中凑齐一种花色的 “1--9”为“一条龙”	例：1到9条，加上任意一坎牌和一对相同的牌
 * 4	本混儿龙	只有一个同花色的混儿，按照无混儿计算
 * 2	大钓（即全球人）	吃碰12张牌，只剩一钓和牌。
 * 2	捉五魁	和牌时只和“五万”一张牌的牌型
 * 4	海底捞月	拿到海底牌最后一张时和牌称为“海底捞月”	注意：海底捞月的这张牌抓到后如果不和，必须打出去，不得换其他牌打出（可以选择放弃抓“海底牌”）。如果这张牌给别人放了铳，也要给别人加1番。
 * 5	杠上开花	听牌时开杠补得的牌为自摸牌称为“杠上开花”
 * 6	清一色	和牌的14张均为一种花色时称为“清一色”	例：1、2、3万+2、3、4万+5、6、7万+6、7、8万+9万x2
 * 2	 混一色	胡牌的14张只有一种花色和字牌。	例：1、2、3万+2、3、4万+北风x3+发财x3+9万x2
 * 2	门清	没有吃碰明杠过的牌型。
 * 40	混杠	摸齐四个“混儿牌”时自动和牌。（40番，不分庄家闲家） 	不分闲家
 * 8	字一色	胡牌中只有字牌。	北风x3+东风x3+南风x3+发财x3+红中x2
 *
 * @author leo on 2017/11/4.
 */
public enum GbJiaFanType implements JiaFanTypeInterface {
    Da_Si_Xi("大四喜", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
            Pai[] shunZi = fanResult.getShunZi();
            if (shunZi != null && shunZi.length > 0) {
                return null;
            }
            int fengKeZiNums = 0;
            //ArrayList<Pai> shouPai = userPlace.getShouPai();
            long pengCount = userPlace.getPeng().stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += pengCount;
            List<Pai> anGang = userPlace.getAnGang().stream().map(Map.Entry::getValue)
                    .collect(Collectors.toCollection(ArrayList::new));
            long anGangCount = anGang.stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += anGangCount;

            long daMingGangCount = userPlace.getDaMingGang().stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += daMingGangCount;

            long xiaoMingGangCount = userPlace.getXiaoMingGang().stream().filter(p -> p.getType() == PaiType.FENG)
                    .count();
            fengKeZiNums += xiaoMingGangCount;

            if (fanResult.getKeZi() != null) {
                long shouPaiKeZiCount = fanResult.getKeZi().stream().filter(p -> p.getType() == PaiType.FENG)
                        .count();

                fengKeZiNums += shouPaiKeZiCount;
            }
            if (fengKeZiNums == 4) {
                resultInfo.setNums(1);
                resultInfo.addWithOut(GbJiaFanType.Quan_Feng_Ke, GbJiaFanType.Meng_Feng_Ke, GbJiaFanType.San_Feng_Ke,
                        GbJiaFanType.Yao_Jiu_Ke, GbJiaFanType.Peng_Peng_Hu);
                return resultInfo;
            }
            //计
//            Pai queTou = fanResult.getQueTou();
//            if (queTou != null) {
//                if (queTou.getType().isZiPai()) {
//
//                }
//            }
            //不计

            return null;
        }

    },
    Da_San_Yuan("大三元", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
            ArrayList<Pai> pais = new ArrayList<>();
            pais.addAll(userPlace.getPeng());
            pais.addAll(userPaiInfo.getXiaoMingGang());
            pais.addAll(userPaiInfo.getDaMingGang());
            pais.addAll(userPaiInfo.getAnGang());
            long count = pais.stream().filter(p -> p.getType() == PaiType.SANYUAN).count();
            if (count != 3) {
                return resultInfo;
            }
            //不计
            resultInfo.addWithOut(GbJiaFanType.Shuang_Jian_Ke, GbJiaFanType.Jian_Ke);
            return resultInfo;
        }
    },
    Shi_San_Yao("十三幺", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isShiSanYao(userPlace)) {
                JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
                resultInfo.setNums(1);
                resultInfo.addWithOut(GbJiaFanType.Wu_Men_Qi, GbJiaFanType.Dan_Diao_Jiang, GbJiaFanType.Men_Qian_Qing,
                        GbJiaFanType.Hun_Yao_Jiu);
                return resultInfo;
            }
            return null;
        }
    },
    Lu_Yi_Se("绿一色", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
            if (onlyGreen(userPaiInfo.getAnGang()) &&
                    onlyGreen(userPaiInfo.getDaMingGang()) &&
                    onlyGreen(userPaiInfo.getXiaoMingGang()) &&
                    onlyGreen(userPaiInfo.getPeng()) &&
                    onlyGreen(userPaiInfo.getChiPais()) &&
                    onlyGreen(fanResult.getKeZi()) &&
                    onlyGreen(fanResult.getShunZi()) &&
                    onlyGreen(Collections.singletonList(fanResult.getQueTou()))) {
                return resultInfo.setNums(1);
            }
//            if (judgeHuService.isShiSanYao(userPlace)) {
//                JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
//                resultInfo.setNums(1);
//                resultInfo.addWithOut(GbJiaFanType.Wu_Men_Qi, GbJiaFanType.Dan_Diao_Jiang, GbJiaFanType.Men_Qian_Qing,
//                        GbJiaFanType.Hun_Yao_Jiu);
//                return resultInfo;
//            }
            return null;
        }

        final List<Pai> greenPais = Arrays.asList(Pai.TIAO_2, Pai.TIAO_3, Pai.TIAO_4, Pai.TIAO_6, Pai.TIAO_8, Pai
                .SANYUAN_FA);

        private boolean onlyGreen(List<Pai> pais) {
            if (pais == null || pais.size() == 0) {
                return true;
            }
            return pais.stream().allMatch(p -> greenPais.contains(p));
        }

        private boolean onlyGreen(Pai[] pais) {
            if (pais == null || pais.length == 0) {
                return true;
            }
            return Arrays.stream(pais).allMatch(p -> greenPais.contains(p));
        }
    },
    Jiu_Lian_Bao_Deng("九莲宝灯", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
            if (userPaiInfo.getShouPai().size() != 14) {//不能吃、碰、杠牌，形成门清九面听的牌型
                return resultInfo;
            }
            //一种花色
            Set<PaiType> paiTypes = userPlace.getShouPai().keySet();
            if (paiTypes.size() == 1) {
                PaiType type = paiTypes.iterator().next();
                if (!type.isZiPai()) {
                    return resultInfo;
                }
            }
            //1112345678999
            PaiType yiTiaoLong = judgeHuService.isYiTiaoLong(userPlace);//肯定是一条龙
            if (yiTiaoLong != null) {
                //3个1
                long c3 = userPlace.getShouPaiList().stream().filter(p -> p.getDian() == 1).count();
                //3个9
                long c9 = userPlace.getShouPaiList().stream().filter(p -> p.getDian() == 9).count();
                if (c3 != 3 || c9 != 9) {
                    return resultInfo;
                }
            }
            resultInfo.addWithOut(GbJiaFanType.Qing_Yi_Se, GbJiaFanType.Yao_Jiu_Ke, GbJiaFanType.Men_Qian_Qing);
            return resultInfo.setNums(1);
        }
    },
    Lian_Qi_Dui("连七对", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (!judgeHuService.isQiDui(userPlace)) {//是七对
                return null;
            }
            if (userPlace.getShouPai().keySet().size() == 1) {//必须是门前清，所以从手牌里判断是否为清一色
                PaiType type = userPlace.getShouPai().keySet().iterator().next();
                if (type.isZiPai()) {
                    return null;
                }
            }
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
            if ((fromXToY(userPaiInfo.getShouPai(), 1, 7)) || fromXToY(userPaiInfo.getShouPai(), 2, 8) ||
                    fromXToY(userPaiInfo.getShouPai(), 3, 9)) {
                resultInfo.setNums(1);
                resultInfo.addWithOut(GbJiaFanType.Qing_Yi_Se, GbJiaFanType.Qi_Dui, GbJiaFanType.Dan_Diao_Jiang,
                        GbJiaFanType.Men_Qian_Qing);
            }
            return null;
        }

        private boolean fromXToY(List<Pai> pais, int dianFrom, int dianTo) {
            for (Integer dian = dianFrom; dian <= dianTo; dian++) {
                Integer finalDian = dian;
                Pai pai = pais.stream().filter(p -> Objects.equals(p.getDian(), finalDian)).findFirst().orElse(null);
                if (pai == null) {
                    return false;
                }
            }
            return true;
        }
    },
    Si_Gang("四杠", 88) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            int size = userPaiInfo.getAnGang().size();
            int size1 = userPaiInfo.getDaMingGang().size();
            int size2 = userPaiInfo.getXiaoMingGang().size();
            int c = size + size1 + size2;
            if (c < 4) {
                return null;
            }
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(2);
            resultInfo.addWithOut(GbJiaFanType.Dan_Diao_Jiang, GbJiaFanType.Peng_Peng_Hu);
            return resultInfo;
        }

        private boolean fromXToY(List<Pai> pais, int dianFrom, int dianTo) {
            for (Integer dian = dianFrom; dian <= dianTo; dian++) {
                Integer finalDian = dian;
                Pai pai = pais.stream().filter(p -> Objects.equals(p.getDian(), finalDian)).findFirst().orElse(null);
                if (pai == null) {
                    return false;
                }
            }
            return true;
        }
    },
    /**
     * 88番结束*
     */

    /*64番开始*/
    Xiao_Si_Xi("小四喜", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);

            if (fanResult.getQueTou().getType() != PaiType.FENG) {//风做将牌
                return null;
            }
            int fengKeZiNums = 0;
            //ArrayList<Pai> shouPai = userPlace.getShouPai();
            long pengCount = userPlace.getPeng().stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += pengCount;
            List<Pai> anGang = userPlace.getAnGang().stream().map(Map.Entry::getValue)
                    .collect(Collectors.toCollection(ArrayList::new));
            long anGangCount = anGang.stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += anGangCount;

            long daMingGangCount = userPlace.getDaMingGang().stream().filter(p -> p.getType() == PaiType.FENG).count();
            fengKeZiNums += daMingGangCount;

            long xiaoMingGangCount = userPlace.getXiaoMingGang().stream().filter(p -> p.getType() == PaiType.FENG)
                    .count();
            fengKeZiNums += xiaoMingGangCount;

            if (fanResult.getKeZi() != null) {
                long shouPaiKeZiCount = fanResult.getKeZi().stream().filter(p -> p.getType() == PaiType.FENG)
                        .count();

                fengKeZiNums += shouPaiKeZiCount;
            }
            if (fengKeZiNums == 3) {//风刻数等于3
                resultInfo.setNums(1);
                resultInfo.addWithOut(GbJiaFanType.San_Feng_Ke, GbJiaFanType.Yao_Jiu_Ke);
                return resultInfo;
            }
            return null;
        }
    },
    Xiao_San_Yuan("小三元", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            ArrayList<Pai> pais = new ArrayList<>();

            pais.addAll(userPaiInfo.getPeng());
            pais.addAll(userPaiInfo.getXiaoMingGang());
            pais.addAll(userPaiInfo.getDaMingGang());
            pais.addAll(fanResult.getKeZi());

            pais.addAll(userPaiInfo.getAnGang());

            long count = pais.stream().filter(p -> p.getType() == PaiType.SANYUAN).count();
            if (count != 2) {
                return null;
            }
            if (fanResult.getQueTou().getType() != PaiType.SANYUAN) {
                return null;
            }
            JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(1);
            //不计
            resultInfo.addWithOut(GbJiaFanType.Shuang_Jian_Ke, GbJiaFanType.Jian_Ke);
            return resultInfo;
        }
    },

    Zi_Yi_Se("字一色", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isZiYiSe(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Peng_Peng_Hu, GbJiaFanType
                        .Quan_Dai_Yao, GbJiaFanType.Yao_Jiu_Ke);
            }
            return null;
        }

    },
    Yi_Se_Shuang_Long_Hui("一色双龙会", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            /*
            * 如果一色双龙会，必然满足胡牌要求
            * */
            if (fanResult.getQueTou().getDian() != 5) {//5为奖牌
                return null;
            }
            //同一花色
            if (!judgeHuService.isQingYiSe(userPlace)) {
                return null;
            }
            //123*2 789*2
            //获取手牌里顺子的数量和明牌中吃的数量为4；
            Pai[] shunZi = fanResult.getShunZi();
            ArrayList<Pai> chiFirst = userPlace.getChiFirst();
            int oneC = obtainOneOrSevenCount(Arrays.stream(shunZi), 1) + obtainOneOrSevenCount(chiFirst.stream(), 1);
            int sevenC = obtainOneOrSevenCount(Arrays.stream(shunZi), 7) + obtainOneOrSevenCount(chiFirst.stream(), 7);
            if (oneC != 2 || sevenC != 2) {
                return null;
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Ping_Hu, GbJiaFanType.Qi_Dui,
                    GbJiaFanType.Qing_Yi_Se, GbJiaFanType.Yi_Ban_Gao, GbJiaFanType.Lao_Shao_Fu, GbJiaFanType.Wu_Zi);
        }

        public int obtainOneOrSevenCount(Stream<Pai> stream, int dian) {
            if (stream != null) {
                long count = stream.filter(p -> p.getDian() == dian).count();
                return (int) count;
            }
            return 0;
        }
    },
    Qing_Yao_Jiu("清幺九", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            int dian = fanResult.getQueTou().getDian();
            if (dian != 1 && dian != 9) {
                return null;
            }
            List<Pai> keZi = fanResult.getKeZi();
            ArrayList<Pai> peng = userPaiInfo.getPeng();
            ArrayList<Pai> anGang = userPaiInfo.getAnGang();
            ArrayList<Pai> daMingGang = userPaiInfo.getDaMingGang();
            ArrayList<Pai> xiaoMingGang = userPaiInfo.getXiaoMingGang();
            if (matchYaoJiu(keZi != null ? keZi.stream() : null) && matchYaoJiu(peng.stream())
                    && matchYaoJiu(anGang.stream())
                    && matchYaoJiu(daMingGang.stream())
                    && matchYaoJiu(xiaoMingGang.stream())) {
                return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Peng_Peng_Hu, GbJiaFanType
                        .Quan_Dai_Yao, GbJiaFanType.Yao_Jiu_Ke, GbJiaFanType.Wu_Zi, GbJiaFanType.Si_Gui_Yi);
            }
            return null;
        }

        public boolean matchYaoJiu(Stream<Pai> paiStream) {
            if (paiStream == null) {
                return true;
            }
            boolean b = paiStream.allMatch(p -> p.getDian() == 1 || p.getDian() == 9);
            return b;
        }
    },
    Si_An_Ke("四暗刻", 64) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            ArrayList<Pai> peng = userPaiInfo.getPeng();
            ArrayList<Pai> daMingGang = userPaiInfo.getDaMingGang();
            ArrayList<Pai> xiaoMingGang = userPaiInfo.getXiaoMingGang();
            if ((peng != null && peng.size() > 0) || (daMingGang != null && daMingGang.size() > 0) ||
                    (xiaoMingGang != null && xiaoMingGang.size() > 0)) {
                return null;
            }

            List<Pai> keZi = fanResult.getKeZi();
            ArrayList<Pai> anGang = userPaiInfo.getAnGang();
            int keZiC = keZi == null ? 0 : keZi.size();
            int angGangC = anGang == null ? 0 : anGang.size();
            if ((keZiC + angGangC) == 4) {
                return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Men_Qian_Qing, GbJiaFanType
                        .Peng_Peng_Hu);
            }
            return null;
        }
    },
    /*64番结束*/

    /*48番开始*/
    Yi_Se_Si_Tong_Shun("一色四同顺", 48) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            boolean qingYiSe = judgeHuService.isQingYiSe(userPlace);
            if (!qingYiSe) {
                return null;
            }
            ArrayList<Pai> chiFirst = new ArrayList<>(userPlace.getChiFirst());
            Pai[] shunZi = fanResult.getShunZi();
            chiFirst.addAll(Arrays.asList(shunZi));
            if (chiFirst.size() > 0) {
                boolean b = chiFirst.stream().allMatch(p -> p.getIndex() == chiFirst.get(0).getIndex());
                if (!b) {
                    return null;
                }
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Yi_Se_San_Jie_Gao, GbJiaFanType
                    .Yi_Ban_Gao, GbJiaFanType.Si_Gui_Yi);
        }
    },
    Yi_Se_Si_Jie_Gao("一色四节高", 48) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (fanResult.getQueTou() == null) {
                return null;
            }
            ArrayList<Pai> peng = userPaiInfo.getPeng();
            ArrayList<Pai> anGang = userPaiInfo.getAnGang();
            ArrayList<Pai> daMingGang = userPaiInfo.getDaMingGang();
            ArrayList<Pai> xiaoMingGang = userPaiInfo.getXiaoMingGang();
            ArrayList<Pai> keZi = fanResult.getKeZi();
            ArrayList<Pai> a = new ArrayList<>();
            a.addAll(peng);
            a.addAll(anGang);
            a.addAll(daMingGang);
            a.addAll(xiaoMingGang);
            a.addAll(keZi);
            a.sort(Comparator.comparingInt(Pai::getIndex));
            if (a.size() > 0) {
                Pai pai = a.get(0);
                if (!pai.getType().isZiPai()) {
                    boolean b = a.stream().allMatch(p -> p.getType() == pai.getType());//
                    if (b) {//同色
                        for (int i = 0; i < a.size() - 1; i++) {
                            Pai curr = a.get(i);
                            Pai next = a.get(i + 1);
                            if (curr.getIndex() != (next.getIndex() - 1)) {
                                return null;
                            }
                        }
                    }
                }
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Yi_Se_San_Tong_Shun, GbJiaFanType
                    .Peng_Peng_Hu);
        }
    },
    /*48番结束*/
    /*32番开始*/
    Yi_Se_Si_Bu_Gao("一色四步高", 32) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (fanResult.getQueTou() == null) {
                return null;
            }
            ArrayList<Pai> chiFirst = userPlace.getChiFirst();
            Pai[] shunZi = fanResult.getShunZi();
            List<Pai> pais = Arrays.asList(shunZi);
            ArrayList<Pai> a = new ArrayList<>();
            a.addAll(chiFirst);
            a.addAll(pais);
            a.sort(Comparator.comparingInt(Pai::getIndex));
            if (a.size() > 0) {
                Pai pai = a.get(0);
                if (!pai.getType().isZiPai()) {
                    boolean b = a.stream().allMatch(p -> p.getType() == pai.getType());//
                    if (b) {//同色
                        for (int i = 0; i < a.size() - 1; i++) {
                            Pai curr = a.get(i);
                            Pai next = a.get(i + 1);
                            if ((curr.getIndex() != (next.getIndex() - 1)) || curr.getIndex() != (next.getIndex() -
                                    2)) {
                                return null;
                            }
                        }
                    }
                }
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Lian_Liu, GbJiaFanType.Lao_Shao_Fu);
        }
    },
    Hun_Yao_Jiu("混幺九", 32) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (fanResult.getQueTou() == null) {
                return null;
            }
            int dian = fanResult.getQueTou().getDian();
            if (dian != 1 && dian != 9) {
                return null;
            }
            List<Pai> keZi = fanResult.getKeZi();
            ArrayList<Pai> peng = userPaiInfo.getPeng();
            ArrayList<Pai> anGang = userPaiInfo.getAnGang();
            ArrayList<Pai> daMingGang = userPaiInfo.getDaMingGang();
            ArrayList<Pai> xiaoMingGang = userPaiInfo.getXiaoMingGang();
            if (matchYaoJiu(keZi != null ? keZi.stream() : null) && matchYaoJiu(peng.stream())
                    && matchYaoJiu(anGang.stream())
                    && matchYaoJiu(daMingGang.stream())
                    && matchYaoJiu(xiaoMingGang.stream())) {
                return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Peng_Peng_Hu, GbJiaFanType
                        .Quan_Dai_Yao, GbJiaFanType.Yao_Jiu_Ke, GbJiaFanType.Wu_Zi, GbJiaFanType.Si_Gui_Yi);
            }
            return null;
        }

        public boolean matchYaoJiu(Stream<Pai> paiStream) {
            if (paiStream == null) {
                return true;
            }
            boolean b = paiStream.allMatch(p -> p.getDian() == 1 || p.getDian() == 9 || p.getDian() == 10);
            return b;
        }


    },

    San_Gang("三杠", 32) {
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            return new JiaFanComputeResultInfo().setNums(userPaiInfo.getAnGang().size() + userPlace.getDaMingGang()
                    .size() + userPlace.getXiaoMingGang().size() == 3 ? 1 : 0);

        }

    },
    /*32番结束*/
    /*24番开始*/
    Qi_Dui("七对", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
        }
    },

    Qi_Xin_Bu_Kao("七星不靠", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiXingBuKao(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
        }
    },
    Qing_Yi_Se("清一色", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQingYiSe(userPlace)) {
                JiaFanComputeResultInfo computeResultInfo = new JiaFanComputeResultInfo().setNums(1);
                computeResultInfo.addWithOut(GbJiaFanType.Wu_Zi);
            }
            return null;
        }
    },
    Yi_Se_San_Tong_Shun("一色三同顺", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            boolean qingYiSe = judgeHuService.isQingYiSe(userPlace);
            if (!qingYiSe) {
                return null;
            }
            ArrayList<Pai> chiFirst = new ArrayList<>(userPlace.getChiFirst());
            Pai[] shunZi = fanResult.getShunZi();
            chiFirst.addAll(Arrays.asList(shunZi));
            if (chiFirst.size() >= 3) {
                boolean b = chiFirst.stream().allMatch(p -> p.getDian() == chiFirst.get(0).getDian());
                if (!b) {
                    return null;
                }
            } else {
                return null;
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Yi_Se_San_Jie_Gao, GbJiaFanType
                    .Yi_Ban_Gao);
        }
    },
    Yi_Se_San_Jie_Gao("一色三节高", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            boolean qingYiSe = judgeHuService.isQingYiSe(userPlace);
            if (!qingYiSe) {
                return null;
            }
            ArrayList<Pai> gang = new ArrayList<>(userPaiInfo.getAnGang());
            gang.addAll(userPaiInfo.getXiaoMingGang());
            gang.addAll(userPaiInfo.getDaMingGang());
            gang.addAll(userPaiInfo.getPeng());
            gang.addAll(fanResult.getKeZi());
            if (gang.size() > 0) {
//                boolean b = gang.stream().allMatch(p -> p.getIndex() == chiFirst.get(0).getIndex());
//                if (!b) {
//                    return null;
//                }
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Yi_Se_San_Tong_Shun);
        }
    },


    /*24番结束*/
    Lian_Liu("连六", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            boolean qingYiSe = judgeHuService.isQingYiSe(userPlace);
            if (!qingYiSe) {
                return null;
            }
            ArrayList<Pai> chiFirst = new ArrayList<>(userPlace.getChiFirst());
            Pai[] shunZi = fanResult.getShunZi();
            chiFirst.addAll(Arrays.asList(shunZi));
            if (chiFirst.size() > 0) {
                boolean b = chiFirst.stream().allMatch(p -> p.getIndex() == chiFirst.get(0).getIndex());
                if (!b) {
                    return null;
                }
            }
            return new JiaFanComputeResultInfo().setNums(1).addWithOut(GbJiaFanType.Peng_Peng_Hu);
        }
    },


    Si_Gui_Yi("四归一", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },

    Wu_Zi("无字", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },

    Lao_Shao_Fu("老少副", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },

    Yi_Ban_Gao("一般高", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },

    Ping_Hu("平和", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },


    Quan_Dai_Yao("全带幺", 24) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            if (judgeHuService.isQiDui(userPlace)) {
                return new JiaFanComputeResultInfo().setNums(1);
            }
            return null;
//            if (userPlace.getShouPaiMap().size() == 14) {
//                boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
//                        .stream().filter(v -> v.size() == 2).count() == 7;
//                if (b) {
//                    return new JiaFanComputeResultInfo().setNums(1);
//                }
//            }
//            return null;
        }
    },

    Wu_Men_Qi("五门齐", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            int i = userPlace.existShouPai(chapterResult.getHuiEr()) ? 1 : 0;
            return new JiaFanComputeResultInfo().setNums(i);
        }
    },

    Dan_Diao_Jiang("单调将", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            int i = userPlace.existShouPai(chapterResult.getHuiEr()) ? 1 : 0;
            return new JiaFanComputeResultInfo().setNums(i);
        }
    },

    Men_Qian_Qing("门前清", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            boolean isMengPing = !userPaiInfo.hasChi() && !userPaiInfo.hasPeng() && !userPaiInfo.hasGang();
            return new JiaFanComputeResultInfo().setNums(isMengPing ? 1 : 0);
        }


    },

    Shuang_Jian_Ke("双箭刻", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            int i = userPlace.existShouPai(chapterResult.getHuiEr()) ? 1 : 0;
            return new JiaFanComputeResultInfo().setNums(i);
        }
    },

    Jian_Ke("箭刻", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            int i = userPlace.existShouPai(chapterResult.getHuiEr()) ? 1 : 0;
            return new JiaFanComputeResultInfo().setNums(i);
        }
    },

    Quan_Feng_Ke("圈风刻", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            int i = userPlace.existShouPai(chapterResult.getHuiEr()) ? 1 : 0;
            return new JiaFanComputeResultInfo().setNums(i);
        }
    },

    Meng_Feng_Ke("门风刻", 3) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo userPaiInfo) {
//            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
//            return new JiaFanComputeResultInfo().setNums(judgeHuService.isYiTiaoLong() != null ? 1 : 0);
            return null;

        }

    },

    San_Feng_Ke("三风刻", 4) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
//            PaiType paiType = userPlace.isYiTiaoLong();
//            if (chapterResult.getHuiEr() == null) {
//                return new JiaFanComputeResultInfo().setNums(0);
//            }
//            boolean isBenHunErLong = paiType != null && chapterResult.getHuiEr() != null && paiType.equals
//                    (chapterResult.getHuiEr()[0].getType());
            //return new JiaFanComputeResultInfo().setNums(isBenHunErLong ? 1 : 0);
            return null;

        }
    },

    Yao_Jiu_Ke("幺九刻", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            return new JiaFanComputeResultInfo().setNums((userPlace.getShouPaiList().size() == 2) ? 1 : 0);
        }
    },

    Peng_Peng_Hu("碰碰和", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            ArrayList<Pai> shouPai = userPlace.getShouPaiList();
            Pai pai = shouPai.get(shouPai.size() - 1);
            boolean isZuoWuKui = Pai.WAN_5.equals(pai) && fanResult.isDanDiao(pai);
            return new JiaFanComputeResultInfo().setNums(isZuoWuKui ? 1 : 0);
        }

    },


    HUN_YI_SE("混一色", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            GbJudgeHuService judgeHuService = (GbJudgeHuService) userPlace.getJudgeHuService();
            return new JiaFanComputeResultInfo().setNums(judgeHuService.isHunYiSe(userPlace) ? 1 : 0);
        }


    },

    HAIDI_LAO_YUE("海底捞月", 4) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            return new JiaFanComputeResultInfo().setNums(chapterResult.isLastPai() ? 1 : 0);
        }


    },

    GANG_SHANG_HUA("杠上开花", 5) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            return new JiaFanComputeResultInfo().setNums(chapterResult.isGangShangHua() ? 1 : 0);
        }


    },
/*
    MEN_QING("门清", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            boolean isMengPing = !userPaiInfo.hasChi() && !userPaiInfo.hasPeng() && !userPaiInfo.hasGang();
            return new JiaFanComputeResultInfo().setNums(isMengPing ? 1 : 0);
        }


    },*/

    AN_GANG("暗杠", 2) {
        @Override
        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
                userPlace, UserPaiInfo
                                                       userPaiInfo) {
            return new JiaFanComputeResultInfo().setNums(userPlace.getAnGang().size() == 1 ? 1 : 0);
        }


    };

    //    DAI_GENG("带跟", 1) {
//        public JiaFanComputeResultInfo compute(FanResult fanResult, ChapterEndResult chapterResult, UserPlace
//                userPlace, UserPaiInfo
//                                                       userPaiInfo) {
//            return new JiaFanComputeResultInfo().setNums(userPlace.getGengCount());
//        }
//
//
//    };
    static Map<JiaFanTypeInterface, FanInfo> jiaFanMap = null;

    public static final Map<JiaFanTypeInterface, FanInfo> getJiaFanMap() {
        if (jiaFanMap == null) {
            jiaFanMap = Arrays.stream(GbJiaFanType.values()).collect(Collectors.toMap(r -> r, v -> new FanInfo(v.name,
                    v.fan)));
        }
        return jiaFanMap;
    }

    @Override
    public int getFan() {
        return fan;
    }

    @Override
    public String getName() {
        return name;
    }

    public int fan;
    public String name;
//    private int type;

    GbJiaFanType(String name, int fan) {
//        this.type = type;
        this.fan = fan;
        this.name = name;
    }
}
