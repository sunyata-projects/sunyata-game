package org.sunyata.game.majiang.core.models.majiang.guangdong;

import org.sunyata.game.majiang.core.models.majiang.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuangDongJudgeHuService extends AbstractJudgeHuService {
    private final Rules rule;

    public GuangDongJudgeHuService(Rules rule) {
        this.rule = rule;
    }

    @Override
    public boolean isHuPaiBy(UserPlace userPlace, Pai pai) {
        return isSpecialHuPai(userPlace, pai) || AgariUtils.isHuPai(userPlace.getShouPai().values(), pai);
    }

    @Override
    public boolean isHuPai(UserPlace userPlace, boolean isHuihuiGang, ArrayList<Pai> all, Pai[] huiEr) {
        return isHuiErGang(userPlace, isHuihuiGang, huiEr) || isQiDui(userPlace) || super.isHuPai(userPlace,
                isHuihuiGang, all, huiEr);
    }

    public boolean isQiDui(UserPlace userPlace) {
        if (userPlace.getShouPaiMap().size() == 14) {
            return userPlace.getShouPaiMap().asMap().values().stream().filter(v -> v.size() == 2).count() == 7;
        }
        return false;
    }

    public boolean isQiDui(UserPlace userPlace, Pai pai) {
        if (userPlace.getShouPaiMap().size() == 14) {
            Stream<Pai> concat = Stream.concat(userPlace.getShouPai().values().stream(), Stream.of(pai));
            return concat.collect(Collectors.groupingBy(r -> r)).values().stream().filter(v -> v.size() == 2).count()
                    == 7;
        }
        return false;
    }

    public boolean isHuiErGang(UserPlace userPlace, boolean isHuihuiGang, Pai huiEr[]) {
        if (!isHuihuiGang || huiEr == null || huiEr.length != 1) {
            return false;
        }
        return userPlace.getShouPaiCount(huiEr[0]) == 4;
    }

    @Override
    public boolean isSpecialHuPai(UserPlace userPlace, Pai pai) {
        return true;
    }

    public boolean isDaSiXi(UserPlace userPlace) {
        //ArrayList<Pai> shouPai = userPlace.getShouPai();
        boolean pengFind = userPlace.getPeng().stream().allMatch(p -> p.getType() != PaiType.FENG);
        if (pengFind) {
            return false;
        }
        List<Pai> anGang = userPlace.getAnGang().stream().map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
        boolean anGangFlag = anGang.stream().allMatch(p -> p.getType() != PaiType.FENG);
        if (anGangFlag) {
            return false;
        }

        boolean daMingGangFlag = userPlace.getDaMingGang().stream().allMatch(p -> p.getType() != PaiType.FENG);
        if (daMingGangFlag) {
            return false;
        }

        boolean xiaoMingGangFlag = userPlace.getXiaoMingGang().stream().allMatch(p -> p.getType() != PaiType.FENG);
        if (xiaoMingGangFlag) {
            return false;
        }
        ArrayList<Pai[]> chi = userPlace.getChi();
        if (chi != null && chi.size() > 0) {
            return false;
        }
        return true;
    }


}
