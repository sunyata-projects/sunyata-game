package org.sunyata.game.majiang.core.models.majiang.gb;

import org.sunyata.game.majiang.core.models.majiang.*;

import java.util.stream.Collectors;

public class GbJudgeHuService extends AbstractJudgeHuService {
    private final Rules rule;

    public GbJudgeHuService(Rules rule) {
        this.rule = rule;
    }


    @Override
    public boolean isSpecialHuPai(UserPlace userPlace, Pai pai) {
        //十三幺
        if (isShiSanYao(userPlace)) {
            return true;
        }
        if (isQiDui(userPlace)) {
            return true;
        }
        return false;
    }

    public boolean isQiDui(UserPlace userPlace) {
        if (userPlace.getShouPaiMap().size() == 14) {
            boolean b = userPlace.getShouPai().values().stream().collect(Collectors.groupingBy(r -> r)).values()
                    .stream().filter(v -> v.size() == 2).count() == 7;
            return b;
        }
        return false;
    }

    public boolean isShiSanYao(UserPlace userPlace) {
        JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
        //手牌里有1,9,字牌
        boolean b = userPlace.getShouPaiList().stream().allMatch(p -> p.getDian() == 1 || p.getDian() == 9 || p
                .getType().isZiPai());
        if (!b) {
            return false;
        }
        //字牌数量等于7或8
        long ziCount = userPlace.getShouPaiList().stream().filter(p -> p.getType().isZiPai()).count();
        if (!(ziCount == 7 || ziCount == 8)) {
            return false;
        }

        long tong1Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.TONG_1).count();
        long tong9Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.TONG_9).count();

        long tiao1Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.TIAO_1).count();
        long tiao9Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.TIAO_9).count();

        long wan1Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.WAN_1).count();
        long wan9Count = userPlace.getShouPaiList().stream().filter(p -> p == Pai.WAN_9).count();
        return tong1Count >= 1 && tong9Count >= 1 && tiao1Count >= 1 && tiao9Count >= 1 && wan1Count >= 1 &&
                wan9Count >= 1;
    }

    public boolean isQiXingBuKao(UserPlace userPlace) {
        JiaFanComputeResultInfo resultInfo = new JiaFanComputeResultInfo().setNums(0);
        boolean b = exitOnePai(userPlace, Pai.FENG_DONG, Pai.FENG_NAN, Pai.FENG_XI, Pai.FENG_BEI, Pai.SANYUAN_ZHONG,
                Pai.SANYUAN_FA, Pai.SANYUAN_BEI);
        if (!b) {
            return false;
        }
        boolean b1 = exitAtMostOnePai(userPlace, 1, 4, 7, 2, 5, 8, 3, 6, 9);
        if (!b1) {
            return false;
        }
        return true;
    }

    private boolean exitOnePai(UserPlace userPlace, Pai... pais) {
        for (int i = 0; i < pais.length; i++) {
            int i1 = paiCount(userPlace, pais[i]);
            if (i1 != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean exitAtMostOnePai(UserPlace userPlace, int... pais) {
        int c = 0;
        for (int i = 0; i < pais.length; i++) {
            int i1 = paiCountByDian(userPlace, i);
            if (i1 > 1) {
                return false;
            }
            c += i1;
        }
        if (c == 7) {
            return true;
        }
        return false;
    }

    private int paiCountByDian(UserPlace userPlace, int i) {
        long count = userPlace.getShouPaiList().stream().filter(p -> p.getDian() == i).count();
        return Math.toIntExact(count);
    }

    private int paiCount(UserPlace userPlace, Pai pai) {
        long count = userPlace.getShouPaiList().stream().filter(p -> p.getIndex() == pai.getIndex()).count();
        return Math.toIntExact(count);
    }
}
