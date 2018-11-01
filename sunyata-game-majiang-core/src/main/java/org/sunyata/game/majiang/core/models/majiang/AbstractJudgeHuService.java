package org.sunyata.game.majiang.core.models.majiang;

import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author leo
 */
public class AbstractJudgeHuService implements JudgeHuService {
    @Override
    public boolean isSpecialHuPai(UserPlace userPlace, Pai pai) {
        return false;
    }

    @Override
    public boolean isHuPaiBy(UserPlace userPlace, Pai pai) {
        return false;
    }

    @Override
    public boolean isHuPai(UserPlace userPlace, boolean isHuihuiGang, ArrayList<Pai> all, Pai[] huiEr) {
        return AgariUtils.isHuiErHuPai(all, userPlace.getShouPai().values(), huiEr);
    }


    public PaiType isYiTiaoLong(UserPlace userPlace) {
        PaiType type = isYiTiaoLong(userPlace, Pai.TONG_1);
        if (type != null) {
            return type;
        }
        type = isYiTiaoLong(userPlace, Pai.TIAO_1);
        if (type != null) {
            return type;
        }
        type = isYiTiaoLong(userPlace, Pai.WAN_1);
        return type;
    }

    private PaiType isYiTiaoLong(UserPlace userPlace, Pai first) {
        if (userPlace.getShouPai().containsKey(first.getType())) {
            Pai next = first;
            while (userPlace.getShouPaiMap().containsKey(next)) {
                next = next.nextPaiType();
                if (next == null) {
                    return first.getType();
                }
            }
        }
        return null;
    }

    public boolean isQingYiSe(UserPlace userPlace) {
        if (userPlace.getShouPai().keySet().size() == 1) {
            PaiType type = userPlace.getShouPai().keySet().iterator().next();
            return !type.isZiPai() && testAllPaiType(userPlace, type);
        }
        return false;
    }

    public boolean isZiYiSe(UserPlace userPlace) {
        ArrayListMultimap<PaiType, Pai> shouPai = userPlace.getShouPai();
        if (shouPai.keySet().size() == 1) {
            PaiType type = shouPai.keySet().iterator().next();
            return type.isZiPai() && testAllPaiType(userPlace, type);
        }
        return false;
    }

    public boolean testAllPaiType(UserPlace userPlace, PaiType type) {
        return userPlace.getPeng().stream().allMatch(pai -> type.equals(pai.getType())) &&
                userPlace.getAnGang().stream().allMatch(e -> type.equals(e.getValue().getType())) &&
                userPlace.getDaMingGang().stream().allMatch(pai -> type.equals(pai.getType())) &&
                userPlace.getXiaoMingGang().stream().allMatch(pai -> type.equals(pai.getType())) &&
                userPlace.getChi().stream().allMatch(pais -> type.equals(pais[0].getType()));
    }

    public boolean isHunYiSe(UserPlace userPlace) {
        ArrayListMultimap<PaiType, Pai> shouPai = userPlace.getShouPai();
        if (shouPai.keySet().size() == 2) {
            Optional<PaiType> optional = shouPai.keySet().stream().filter(pai -> !pai.isZiPai()).findAny();

            optional.map(type -> {
                Stream<Pai> paiStream = Stream.concat(
                        Stream.concat(userPlace.getShouPaiList().stream(), userPlace.getPeng().stream()),
                        Stream.concat(userPlace.getAnGang().stream().map(Map.Entry::getValue),
                                Stream.concat(
                                        userPlace.getDaMingGang().stream(),
                                        Stream.concat(userPlace.getXiaoMingGang().stream(), userPlace.getChi().stream()
                                                .map(pais -> pais[0]))
                                )
                        )
                );
                return paiStream.allMatch(pai -> type.equals(pai.getType()) || pai.getType().isZiPai());
            });
        }
        return false;
    }

}
