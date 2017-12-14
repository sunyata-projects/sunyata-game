package org.sunyata.game.majiang.core.models.majiang;

/**
 * @author leo on 16/10/17.
 */
public enum PaiType {
    //    筒、条、万
    TONG("筒", false),
    TIAO("条", false),
    WAN("万", false),
    FENG("风", true),
    SANYUAN("三元", true);

    private final String name;
    private final boolean isZhiPai;

    PaiType(String name, boolean isZi) {
        this.name = name;
        this.isZhiPai = isZi;
    }

    public String getName() {
        return name;
    }

    public boolean isZiPai() {
        return isZhiPai;
    }
}
