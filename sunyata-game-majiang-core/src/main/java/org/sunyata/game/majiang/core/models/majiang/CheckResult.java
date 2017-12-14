package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.contract.majiang.OperationNames;

/**
 * @author leo on 2016/10/31.
 */
public class CheckResult {

    String actionName;
    Pai[] pais;
    private int locationIndex;
    private int sequence;

    public int[] getSuggest() {
        return suggest;
    }

    public CheckResult setSuggest(int[] suggest) {
        this.suggest = suggest;
        return this;
    }

    private int[] suggest;
    public CheckResult(int locationIndex,int sequence){
        this.locationIndex = locationIndex;
        this.sequence = sequence;
    }
    public String getActionName() {
        return actionName;
    }

    public CheckResult setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public Pai[] getPais() {
        return pais;
    }

    public CheckResult setPais(Pai[] pais) {
        this.pais = pais;
        return this;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public CheckResult setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
        return this;
    }

    public int getSequence() {
        return sequence;
    }

    public CheckResult setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }



//    private List<Pai[]> chi;
//    private boolean isPeng;
//    private boolean isGang;
//    private boolean isHu;
//    private int locationIndex;
//    private int sequence;


//    List<CheckResultItem> items = new ArrayList<CheckResultItem>();
//
//    static class CheckResultItem {
//        String actionName;
//        Pai[] pais;
//        private int locationIndex;
//        private int sequence;
//    }
//
    public boolean hasOperation() {
        return true;
    }
//
//    public List<Pai[]> getChi() {
//        return chi;
//    }
//
//    public void setChi(List<Pai[]> chi) {
//        this.chi = chi;
//    }
//
//    public boolean isPeng() {
//        return isPeng;
//    }
//
//    public void setPeng(boolean peng) {
//        isPeng = peng;
//    }
//
//    public boolean isGang() {
//        return isGang;
//    }
//
//    public void setGang(boolean gang) {
//        isGang = gang;
//    }
//
//    public boolean isHu() {
//        return isHu;
//    }
//
//    public void setHu(boolean hu) {
//        isHu = hu;
//    }
//
//    public int getLocationIndex() {
//        return locationIndex;
//    }
//
//    public void setLocationIndex(int locationIndex) {
//        this.locationIndex = locationIndex;
//    }
//
//
    public int getPriority() {
        int p = sequence;
        if (actionName.equals(OperationNames.OPT_HU)) {
            p += 10000;
        }
        if (actionName.equals(OperationNames.OPT_DA_MING_GANG)) {
            p += 1000;
        }
        if (actionName.equals(OperationNames.OPT_PENG)) {
            p += 100;
        }
        return p;
    }
//
//    @Override
//    public String toString() {
//        return "CheckResult{" +
//                "chi=" + chi +
//                ", isPeng=" + isPeng +
//                ", isGang=" + isGang +
//                ", isHu=" + isHu +
//                ", locationIndex=" + locationIndex +
//                '}';
//    }
//
//    public void setSequence(int sequence) {
//        this.sequence = sequence;
//    }
}
