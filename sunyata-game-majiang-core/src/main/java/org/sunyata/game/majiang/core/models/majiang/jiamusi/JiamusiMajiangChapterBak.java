//package org.sunyata.game.majiang.core.models.majiang.jiamusi;
//
//import org.sunyata.game.contract.majiang.OperationNames;
//import org.sunyata.game.majiang.core.models.majiang.*;
//import org.sunyata.game.majiang.core.models.message.OperationCPGH;
//import org.sunyata.game.majiang.core.service.Room;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 一局麻将的信息
// *
// * @author leo on 16/10/17.
// */
//public class JiamusiMajiangChapterBak extends AbstractMajiangChapter {
//
//    public JiamusiMajiangChapterBak(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface computeFan) {
//        super(roomInfo, rule, paiPool, computeFan);
//    }
//
//    private boolean tingAfterChi() {
//        //// TODO: 17/11/28 实现上听吃三家
//        return true;
//    }
//
//    @Override
//    protected void initProcessMap() {
//        super.initProcessMap();
//        //this.processMap.put(OperationNames.OPT_CHI_TING, ChiTingProcess.class);
//    }
//
//    @Override
//    protected void generateCPGHChiResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace current, Pai
//            pai, CheckResult checkResult) {
//        List<Pai[]> chi = current.isChi(pai);
//        JiamusiCheckResult result = (JiamusiCheckResult) checkResult;
//        int nextIndex = getNextIndex(locationIndex);
//        if (chi != null && chi.size() > 0) {//如果可以吃
//            if ((nextIndex - 1) == locationIndex) {//是下家
//                //result.setChi(chi);
//                result.setTingAfterChi(false);
//            } else {//如果不是下家,则吃完后必须听
//                if (tingAfterChi()) {//如果吃完后可以听
//                    //result.setChi(chi);
//                    result.setTingAfterChi(true);
//                }
//            }
//        } else {//没有可吃的牌
//            //checkResult.setChi(new ArrayList<>());
//        }
//    }
//
//    protected void checkCPGHChi(OperationCPGH operationCPGH, CheckResult checkResult) {
////        int[] ints = MajiangUtils.toIndexByDyadicArray(checkResult.getChi());
////        JiamusiCheckResult jiamusiCheckResult = (JiamusiCheckResult) checkResult;
////        if (ints != null && ints.length > 0) {//有吃的牌
////            if (jiamusiCheckResult.isTingAfterChi()) {
////                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI_TING).setPai(ints));
////            } else {
////                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI).setPai(ints));
////            }
////            //operationCPGH.setChi();
////        }
//    }
//
////    public boolean checkTingPaiForChiTing() {
////
////    }
//
//    public void chiTing(UserPlace userPlace, Pai pai) {
//        userPlace.peng(pai);
//        sync(OperationNames.OPT_PENG, pai);
//        stopCPGH(false);
//        notifyOut();
//    }
//
//    @Override
//    public String toString() {
//        return "MajiangChapter{" +
//                "userPlaces=" + Arrays.toString(userPlaces) +
//                ", paiPool=" + paiPool +
//                ", shouIndex=" + shouIndex +
//                ", currentIndex=" + currentIndex +
//                ", zhuangIndex=" + zhuangIndex +
//                ", huiEr=" + huiEr +
//                ", rules=" + rules +
//                '}';
//    }
//
//
//}
