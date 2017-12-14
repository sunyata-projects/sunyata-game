//package org.sunyata.game.majiang.core.models.majiang.processes;
//
//import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
//import org.sunyata.game.majiang.core.models.majiang.Pai;
//import org.sunyata.game.majiang.core.models.majiang.UserPlace;
//import org.sunyata.game.majiang.core.models.message.OperationFaPai;
//
///**
// * Created by leo on 17/11/28.
// */
//public class HuProcess implements OptProcess {
//    @Override
//    public void process(boolean isFaPaiRet, MajiangChapter chapter, UserPlace userPlace, int locationIndex, Pai pai,
//                        int fangPaoIndex, int[] chi) {
//        if (!isFaPaiRet) {
//            chapter.huPai(userPlace, locationIndex, pai, chapter.getWaitCurrentIndex(), false);
//            chapter.stopCPGH(false);
//        } else {
//            OperationFaPai prev = chapter.getOperationFaPai();
//            if (prev.getHu()) {
//                chapter.huPai(userPlace, locationIndex, pai, -1, chapter.getOperationFaPaiIsGang());
//            } else {
//                throw new RuntimeException("未允许操作！" + "HU" + ",locationIndex:" + locationIndex);
//            }
//        }
//    }
//}
