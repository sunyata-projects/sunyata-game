//package org.sunyata.game.majiang.core.models.majiang.processes;
//
//import org.apache.commons.lang.ArrayUtils;
//import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
//import org.sunyata.game.majiang.core.models.majiang.Pai;
//import org.sunyata.game.majiang.core.models.majiang.UserPlace;
//import org.sunyata.game.majiang.core.models.message.OperationFaPai;
//
///**
// * Created by leo on 17/11/28.
// */
//public class AnGangGangProcess implements OptProcess {
//    @Override
//    public void process(boolean isFaPaiRet, MajiangChapter chapter, UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex,
//                        int[] chi) {
//        OperationFaPai prev = chapter.getOperationFaPai();
//
//        if (!ArrayUtils.isEmpty(prev.getAnGang())) {
//            chapter.anGang(userPlace, pai);
//        } else {
//            throw new RuntimeException("未允许操作！" + "AnGang" + ",locationIndex:" + locationIndex);
//        }
//    }
//}
