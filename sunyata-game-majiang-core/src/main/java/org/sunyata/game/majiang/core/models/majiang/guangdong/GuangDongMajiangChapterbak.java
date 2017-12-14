//package org.sunyata.game.majiang.core.models.majiang.guangdong;
//
//import org.apache.commons.lang.ArrayUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.sunyata.game.contract.majiang.OperationNames;
//import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
//import org.sunyata.game.majiang.core.models.SceneUser;
//import org.sunyata.game.majiang.core.models.majiang.*;
//import org.sunyata.game.majiang.core.models.message.*;
//import org.sunyata.game.majiang.core.service.Room;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 一局麻将的信息
// *
// * @author leo on 16/10/17.
// */
//public class GuangDongMajiangChapterbak extends AbstractMajiangChapter {
//    Logger logger = LoggerFactory.getLogger(GuangDongMajiangChapterbak.class);
//    public static final int USER_NUMS = 4;
//    protected static final Logger log = LoggerFactory.getLogger(GuangDongMajiangChapterbak.class);
//
//    //FA:发牌，OUT:打牌,OUT_OK:打牌成功，没人用这个哎,CHI:吃,PENG:碰,AN_GANG:暗杠牌,XIAO_MING_GANG:暗杠牌,DA_MING_GANG:暗杠牌,HU:胡牌,OPT_GUO:放弃
////    private static final String OPT_OUT = "OUT";
////    private static final String OPT_OUT_OK = "OUT_OK";
////    private static final String OPT_CHI = "CHI";
////    private static final String OPT_PENG = "PENG";
////    private static final String OPT_AN_GANG = "AN_GANG";
////    private static final String OPT_XIAO_MING_GANG = "XIAO_MING_GANG";
////    private static final String OPT_DA_MING_GANG = "DA_MING_GANG";
////    private static final String OPT_HU = "HU";
////    private static final String OPT_FA = "FA";
////    private static final String OPT_GUO = "GUO";
//    private final UserPlace[] userPlaces = new UserPlace[USER_NUMS];
//
//    private final List<CheckResult> checkResults = new ArrayList<>();
//    private final Rules rules;
//    private final Room room;
//    private ComputeFanInterface computeFan;
//    /**
//     * 第X手 默认0开始
//     */
//    private int shouIndex = 0;
//    /**
//     * 操作开始时间
//     */
//    private long operationTime = 0;
//    /**
//     * 当前打牌的人
//     */
//    private int currentIndex = 0;
//    /**
//     * 当前操作牌
//     */
//    private Pai currentPai = null;
//    private int waitCurrentIndex = -1;
//    private OperationFaPai operationFaPai;
//    /**
//     * 是否杠后的发牌操作，可以杠上花
//     */
//    private boolean operationFaPaiIsGang;
//
//    private OperationCPGH operationCPGH;
//    private OperationOut operationOut;
//
//    /**
//     * 万用牌信息
//     * 混儿牌,会儿牌
//     */
//    private Pai[] huiEr = null;
//    private int chapterNums;//局数, 0开始
//    private int quanIndex;//圈index 0 东 1南 2西 3北 逆时针顺序
//    private int zhuangIndex;//庄index 0 东 1南 2西 3北 逆时针顺序
//    private CheckResult cpghCheckResult;
//    private final PaiPoolInterface paiPool;
//    private boolean isStart = false;
//    private GameChapterEnd gameChapterEnd;
//    private TingPai[] tingPais = new TingPai[USER_NUMS];
//
//
//    private void clear() {
//        Arrays.fill(tingPais, null);
//        paiPool.clear();
//        huiEr = null;
//        for (UserPlace u : userPlaces) {
//            u.clear();
//        }
//    }
//
////    public GuangDongMajiangChapter(Room room, String rulesName) {
////        this.rules = Rules.createRules(rulesName, room.getConfig());
////        this.paiPool = new GuangDongPaiPool(this.rules);
////        this.room = room;
////
////        //for (int i = 0; i < userPlaces.length; i++) {
////        for (int i = 0; i < rules.getUserMax(); i++) {
////            userPlaces[i] = new UserPlace();
////            userPlaces[i].setLocationIndex(i);
////        }
////    }
//
//    public GuangDongMajiangChapterbak(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface computeFan) {
//        this.rules = rule;
//        this.paiPool = paiPool;
//        this.room = roomInfo;
//        this.computeFan = computeFan;
//        //for (int i = 0; i < userPlaces.length; i++) {
//        for (int i = 0; i < rules.getUserMax(); i++) {
//            userPlaces[i] = new UserPlace();
//            userPlaces[i].setLocationIndex(i);
//        }
//    }
//
//    public void start() {
//        //this.rules.rest();
//        gameChapterEnd = null;
//        isStart = true;
//        //发牌
//        paiPool.start();
//        //开始发牌给玩家，
//
//        //for (int i = 0; i < userPlaces.length; i++) {
//        for (int i = 0; i < rules.getUserMax(); i++) {
//            paiPool.faPai(i, userPlaces[i]);
//        }
//
//        paiPool.onFaPaiEnd();
//        huiEr = paiPool.getHuiEr();
//        //发牌完毕！
//        changeCurrentIndex(0);
//    }
//
//
//    public void startNext() {
//        faPai(true, false);
//        log.debug("发牌完毕！{}", this);
//    }
//    /**
//     * 去下一个玩家
//     */
////    private void goNext() {
////        //int next = (currentIndex + 1) %  userPlaces.length;
////        int next = (currentIndex + 1) % rules.getUserMax();
////        changeCurrentIndex(next);
////        faPai(true, false);
////    }
//
//
//
//    public void faPai(boolean isSendMessage, boolean isGang) {
//        if (!(paiPool.size() > getBaoliuLength())) {
//            huangPai();
//            return;
//        }
//        Pai pai = isGang ? paiPool.getFreeGangPai() : paiPool.getFreePai();
//        if (pai == null) {
//            huangPai();
//            logger.info("黄牌了,娘亲");
//            return;
//        }
//        operationFaPai = new OperationFaPai();
//
//        operationFaPaiIsGang = isGang;
//        operationFaPai.setIndex(currentIndex);
//        operationFaPai.setPai(pai.getIndex());
//
//
//        UserPlace userPlace = userPlaces[currentIndex];
//        userPlace.changeFa(pai);
//
//        operationFaPai.setAnGang(userPlace.checkAnGang(pai));
//        operationFaPai.setMingGang(userPlace.checkXiaoMingGang(pai));
//        operationFaPai.setHu(userPlace.isHuPai(rules.isHuiErGang(), rules.getAllPai(), huiEr));
//
//        OperationFaPaiNew operationFaPaiNew = OperationFaPaiNew.toOperationCpgh(operationFaPai);
//
//        //根据情况发送消息，初始化不用，因为后面同步场景会同步操作到客户端
//        syncHidePai(OperationNames.OPT_FA, pai.getIndex());
//        onOperationStart();
//        if (isSendMessage) {
//            //sendMessage(currentIndex, operationFaPai);
//            sendMessage(currentIndex, operationFaPaiNew);
//        }
//    }
//
//    public void outRet(int locationIndex, int paiIndex) {
//        Pai pai = Pai.fromIndex(paiIndex);
//        if (locationIndex != operationOut.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationOut.getIndex());
//        }
//        operationOut = null;
//        chuPai(userPlaces[currentIndex], locationIndex, pai);
//    }
//
//    public void faPaiRet(int locationIndex, String opt, int paiIndex) {
//        Pai pai = Pai.fromIndex(paiIndex);
//        if (operationFaPai == null) {
//            log.error("发牌操作已经结束！index:{},opt:{},pai:{}");
//        }
//        if (locationIndex != operationFaPai.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
//        }
//        OperationFaPai prev = this.operationFaPai;
//        this.operationFaPai = null;
//        ////OUT:打牌,AN_GANG:暗杠牌,XIAO_MING_GANG:暗杠牌,HU:胡牌
//        UserPlace userPlace = userPlaces[locationIndex];
//        switch (opt) {
//            case OperationNames.OPT_OUT:
//                chuPai(userPlace, locationIndex, pai);
//                break;
//            case OperationNames.OPT_AN_GANG:
//                if (!ArrayUtils.isEmpty(prev.getAnGang())) {
//                    anGang(userPlace, pai);
//                } else {
//                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//                }
//                break;
//            case OperationNames.OPT_XIAO_MING_GANG:
//                if (!ArrayUtils.isEmpty(prev.getMingGang())) {
//                    xiaoMingGang(userPlace, pai);
//                } else {
//                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//                }
//                break;
//            case OperationNames.OPT_HU:
//                if (prev.getHu()) {
//                    huPai(userPlace, locationIndex, currentPai, -1, operationFaPaiIsGang);
//                } else {
//                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//                }
//                break;
//            default:
//                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
//        }
//    }
//
//    public void cpghRet(int locationIndex, String opt, int[] chi) {
//        if (operationCPGH == null) {
//            log.error("发牌操作已经结束！index:{},opt:{},pai:{}");
//        }
//        if (locationIndex != operationCPGH.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
//        }
//        UserPlace userPlace = userPlaces[locationIndex];
//        switch (opt) {
//            case OperationNames.OPT_HU:
//                //if (operationCPGH.getIsHu()) {
//                huPai(userPlace, locationIndex, currentPai, waitCurrentIndex, false);
//                stopCPGH(false);
//                //} else {
//                //throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//                //}
//                break;
//            case OperationNames.OPT_DA_MING_GANG:
//                //if (operationCPGH.getIsGang()) {
//                daMingGang(userPlace, currentPai);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
//                break;
//            case OperationNames.OPT_PENG:
//                //if (operationCPGH.getIsPeng()) {
//                peng(userPlace, currentPai);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
//                break;
//            case OperationNames.OPT_CHI:
//                //if (!ArrayUtils.isEmpty(operationCPGH.getChi())) {
//                chi(userPlace, currentPai, chi);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
//                break;
//            case OperationNames.OPT_GUO:
//                checkCPGH();
//                break;
//            default:
//                //放弃
//                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
//        }
//    }
//
//    private void chi(UserPlace userPlace, Pai pai, int[] chi) {
//        if (Arrays.binarySearch(chi, pai.getIndex()) == -1) {
//            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
//        }
//        boolean isCheckOk = false;
//        CHI_OUT:
//        for (int i = 0; i < cpghCheckResult.getChi().size(); i++) {
//            Pai[] pais = cpghCheckResult.getChi().get(i);
//            for (int j = 0; j < pais.length; j++) {
//                if (pais[j].getIndex() != chi[j]) {
//                    continue CHI_OUT;
//                }
//            }
//            isCheckOk = true;
//            break;
//        }
//        if (!isCheckOk) {
//            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
//        }
//        userPlace.chi(pai, chi);
//        syncChi(OperationNames.OPT_CHI, pai, chi);
//        //通知 吃碰杠胡
//        stopCPGH(false);
//        //通知出牌
//        notifyOut();
//    }
//
//    private void peng(UserPlace userPlace, Pai pai) {
//        userPlace.peng(pai);
//        sync(OperationNames.OPT_PENG, pai);
//        stopCPGH(false);
//        notifyOut();
//    }
//
//    private void daMingGang(UserPlace userPlace, Pai pai) {
//        userPlace.daMingGang(pai);
//        sync(OperationNames.OPT_DA_MING_GANG, pai);
//        stopCPGH(false);
//        faPai(true, true);
//    }
//
//    private void huPai(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex, boolean isGangShangHua) {
//        if (fangPaoIndex > -1) {
//            userPlace.addShouPai(pai);
//        }
//        end(locationIndex, fangPaoIndex, isGangShangHua);
//    }
////
////    private void huangPai() {
////        end(-1, -1, false);
////    }
////
////    public Rules getRules() {
////        return rules;
////    }
//
////    private void end(int huPaiLocationIndex, int fangPaoIndex, boolean isGangShangHua) {
////
//////        GuangDongComputeFan computeFan = new GuangDongComputeFan(
//////                this, huPaiLocationIndex, fangPaoIndex, isGangShangHua
//////        );
////        computeFan = computeFan.build(this,huPaiLocationIndex,fangPaoIndex,isGangShangHua);
////        ChapterEndResult endResult = computeFan.compute();
////        //开始处理扎码
////
////
////        if (endResult.isHuPai()) {
////            if (zhuangIndex != huPaiLocationIndex) {
////                zhuangIndex = (zhuangIndex + 1) % 4;
////                if (zhuangIndex == 0) {//一圈
////                    quanIndex = (quanIndex + 1) % 4;
////                }
////            }
////
////            int zaMaScore = computeFan.zaMa();
////
////            int fanNums = endResult.getFanNums() + zaMaScore;
////
////
////            endResult.excuteScore(fanNums);
////            computeFan.computeGuaFengXiaYu();
////
////            room.getRoomInfo().changeScore(endResult.getUserPaiInfos());
////        }
////        GameChapterEnd msg = endResult.toMessage();
////        gameChapterEnd = msg;
////        chapterNums++;
////
////
////        isStart = false;
////        clear();
////        room.sendMessage(msg);
////        room.endChapter(endResult, this);
////    }
//
////
////    private void notifyOut() {
////        operationOut = new OperationOut(currentIndex);
////        onOperationStart();
////        sendMessage(currentIndex, operationOut);
////    }
//
//    private void xiaoMingGang(UserPlace userPlace, Pai pai) {
//        userPlace.xiaoMingGang(pai);
//        sync(OperationNames.OPT_XIAO_MING_GANG, pai);
//        faPai(true, true);
//    }
//
////    public void chuPai(int locationIndex, int paiIndex) {
////        chuPai(userPlaces[locationIndex], locationIndex, Pai.fromIndex(paiIndex));
////    }
//
////    public void forceChuPai() {
////        UserPlace userPlace = userPlaces[currentIndex];
//////        Pai fa = userPlace.getFa();
////        chuPai(userPlace, currentIndex, fa);
////    }
//
//    private void anGang(UserPlace userPlace, Pai pai) {
//        userPlace.anGang(shouIndex, pai);
//        syncHidePai(OperationNames.OPT_AN_GANG, pai.getIndex());
//        faPai(true, true);
//    }
//
//    private void chuPai(UserPlace userPlace, int locationIndex, Pai pai) {
//        if (locationIndex != currentIndex) {
//            throw new RuntimeException(
//                    "发牌用户错误，不是当前用户(发牌用户：locationIndex:" + locationIndex + ",当前操作用户：currentIndex:" + currentIndex + ")"
//            );
//        }
//        if (!userPlace.checkShouPai(pai)) {
////            throw new RuntimeException("打出的不是手牌：" + pai);
//            log.error("打出的不是手牌：" + pai);
//            return;
//        }
////        if (userPlace.getFa() == null) {
////            throw new RuntimeException("操作用户未发牌？：" + userPlace);
////        }
//        //开始出来操作
//        currentPai = pai;
//
//        userPlace.removeShouPai(pai);
//        userPlace.addAllOut(pai);
//        //检查其他三家 吃 碰 杠 胡
//        //for (int i = locationIndex, j = 0; i < (locationIndex + userPlaces.length); i++, j++) {
//        for (int i = locationIndex, j = 0; i < (locationIndex + rules.getUserMax()); i++, j++) {
//            //int index = i % userPlaces.length;
//            int index = i % rules.getUserMax();
//            UserPlace current = userPlaces[index];
//            if (current != userPlace) {
//                CheckResult checkResult = new CheckResult();
//                if (rules.allowChi()) {
//                    checkResult.setChi(current.isChi(pai));
//                } else {
//                    checkResult.setChi(new ArrayList<>());
//                }
//                checkResult.setPeng(current.isPeng(pai));
//                checkResult.setGang(current.isDaMingGang(pai));
//
//                if (rules.allowFangPao()) {
//                    checkResult.setHu(current.isHuPaiBy(pai));
//                }
//
//                checkResult.setLocationIndex(index);
//                checkResult.setSequence(rules.getUserMax() - i);
//                if (checkResult.hasOperation()) {
//                    checkResults.add(checkResult);
//                }
//            }
//        }
//        if (checkResults.size() > 0) {
//            checkResults.sort(
//                    (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority())
//            );
//        }
//        sync(OperationNames.OPT_OUT, pai);
//        checkTingPai();
//        waitCurrentIndex = currentIndex;
//        checkCPGH();
//    }
//
//    /**
//     * 计算听牌
//     */
//    private void checkTingPai() {
//        UserPlace userPlace = userPlaces[currentIndex];
//
//        ArrayList<Pai> pais = userPlace.checkTingPai(rules.isHuiErGang(), rules.getAllPai(), huiEr);
//
//        TingPai tingPai = new TingPai(pais.stream().mapToInt(Pai::getIndex).toArray());
//        tingPais[currentIndex] = tingPai;
//        sendMessage(currentIndex, tingPai);
//    }
//
//    private void stopCPGH(boolean isSync) {
//        if (isSync) {
//            sync(OperationNames.OPT_OUT_OK, currentPai);
//        }
//        checkResults.clear();
//        operationCPGH = null;
//        waitCurrentIndex = -1;
//        currentPai = null;
//        cpghCheckResult = null;
//    }
//
//    private void checkCPGH() {
//        if (checkResults.size() > 0) {
//            CheckResult checkResult = checkResults.remove(0);
//
//
//            operationCPGH = new OperationCPGH();
//            int[] ints = MajiangUtils.toIndexByDyadicArray(checkResult.getChi());
//            if (ints != null && ints.length > 0) {
//                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI).setPai(ints));
//                //operationCPGH.setChi();
//            }
//            if (checkResult.isPeng()) {
//                //operationCPGH.setIsPeng(checkResult.isPeng());
//                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_PENG).setPai(new int[]{currentPai
//                        .getIndex()}));
//            }
//            if (checkResult.isGang()) {
//                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_DA_MING_GANG).setPai(new
//                        int[]{currentPai.getIndex()}));
//            }
//            //operationCPGH.setIsGang(checkResult.isGang());
//            if (checkResult.isHu()) {
//                operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_HU).setPai(new int[]{currentPai
//                        .getIndex()}));
//            }
//            //operationCPGH.setIsHu(checkResult.isHu());
//            operationCPGH.setIndex(checkResult.getLocationIndex());
//            //operationCPGH.setPai(currentPai.getIndex());
//            this.cpghCheckResult = checkResult;
//
//            changeCurrentIndex(checkResult.getLocationIndex());
//
//            onOperationStart();
//            sendMessage(checkResult.getLocationIndex(), operationCPGH);
//        } else {
//            changeCurrentIndex(waitCurrentIndex);
//            userPlaces[currentIndex].addOut(currentPai);
//
//            stopCPGH(true);
//            goNext();
//        }
//    }
//
//
////    protected void changeCurrentIndex(int index) {
////        this.currentIndex = index;
////        shouIndex++;
////    }
//
////    private int getBaoliuLength() {
////        return rules.getBaoliuLength();
////    }
//
//
//    public MajiangChapterMsg toMessage(int myLocationIndex) {
//
//        MajiangChapterMsg m = new MajiangChapterMsg();
//        m.setBaoliuLength(getBaoliuLength());
//        m.setFreeLength(paiPool.size());
//        if (huiEr != null) {
//            m.setHuiEr(Arrays.stream(huiEr).mapToInt(Pai::getIndex).toArray());
//        } else {
//            m.setHuiEr(null);
//        }
//        m.setChapterNumsMax(this.room.getConfig().getInt(RoomConfigInfo.CHAPTER_MAX));
//        m.setChapterNums(chapterNums);
//        m.setQuanIndex(quanIndex);
//        m.setZhuangIndex(zhuangIndex);
//        String bingType = room.getConfig().getString(RoomConfigInfo.BIAN_TYPE);
//        m.setBianType(bingType);
//        if (Objects.equals(bingType, RoomConfigInfo.BIAN_TYPE_DAN_GUI) || Objects.equals(bingType, RoomConfigInfo
//                .BIAN_TYPE_SHUANG_GUI)) {
//            m.setBianSource(huiEr[0].prev().getIndex());
//        } else {
//            m.setBianSource(Pai.NOT_PAI_INDEX);
//        }
//
//
////        for (int i = 0; i < userPlaces.length; i++) {
//        for (int i = 0; i < rules.getUserMax(); i++) {
//            UserPlace userPlace = userPlaces[i];
//            m.addUserPlace(userPlace.toMessage(i == myLocationIndex, shouIndex));
//        }
//        m.setCurrentIndex(currentIndex);
//
//        if (operationFaPai != null && operationFaPai.getIndex() == myLocationIndex) {
//            m.setOptFaPai(operationFaPai);
//        }
//        if (operationCPGH != null && operationCPGH.getIndex() == myLocationIndex) {
//            m.setOptCpgh(operationCPGH);
//        }
//        if (operationOut != null && operationOut.getIndex() == myLocationIndex) {
//            m.setOptOut(operationOut);
//        }
//        if (tingPais[myLocationIndex] != null) {
//            m.setTingPai(tingPais[myLocationIndex]);
//        }
//        if (operationTime > 0) {
//            m.setSyncOptTime(getSyncOptTime());
//        }
//        if (gameChapterEnd != null) {
//            m.setGameChapterEnd(gameChapterEnd);
//        }
//        return m;
//    }
//
////    public void sendMessage(int locationIndex, Message msg) {
////        room.sendMessage(locationIndex, msg);
////    }
//
//    public void sync(String opt, Pai... pais) {
//        sync(opt, Arrays.stream(pais).mapToInt(Pai::getIndex).toArray());
//    }
//
//
//    public void syncChi(String opt, Pai pai, int... pais) {
//        room.sendMessage(new SyncOpt(opt, currentIndex, pai.getIndex(), pais));
//    }
//
//    public void sync(String opt, int... pais) {
//        if (pais.length > 1) {
//            room.sendMessage(new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais));
//        } else {
//            room.sendMessage(new SyncOpt(opt, currentIndex, pais[0], null));
//        }
//    }
//
////    private void onOperationStart() {
////        operationTime = System.currentTimeMillis();
////        syncOptTime();
////    }
//
//    public boolean isOperationTimeOut(long now) {
//        return (now - operationTime) > rules.getShouTimeMillisecond();
//    }
//
//    public void syncOptTime() {
//        room.sendMessage(getSyncOptTime());
//    }
//
////    private SyncOptTime getSyncOptTime() {
////        int leftTime = 0;
////        if (operationTime > 0) {
////            leftTime = (int) (
////                    rules.getShouTimeMillisecond() - (System.currentTimeMillis() - operationTime)
////            );
////        }
////        return new SyncOptTime(currentIndex, leftTime);
////    }
//
//    /**
//     * 对其他用户隐藏牌的同步方式
//     */
//    public void syncHidePai(String opt, int... pais) {
//        if (pais.length > 1) {
//            int[] hidePai = new int[pais.length];
//            Arrays.fill(hidePai, Pai.HAS_PAI_INDEX);
//            room.sendMessage(currentIndex,
//                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais),
//                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, hidePai)
//            );
//        } else {
//            room.sendMessage(currentIndex,
//                    new SyncOpt(opt, currentIndex, pais[0], null),
//                    new SyncOpt(opt, currentIndex, Pai.HAS_PAI_INDEX, null)
//            );
//        }
//    }
//
//    public boolean isStart() {
//        return isStart;
//    }
//
//    public ArrayList<Pai> getLeftPai() {
//        return paiPool.getLeftPai();
//    }
//
//    public int getZhuangIndex() {
//        return zhuangIndex;
//    }
//
//    public UserPlace[] getUserPlaces() {
//        return userPlaces;
//    }
//
//
//    public Pai[] getHuiEr() {
//        return huiEr;
//    }
//
//    /**
//     * 是否最后一张牌
//     */
//    public boolean isLastPai() {
//        return paiPool.size() <= getBaoliuLength();
//    }
//
//
//    public void updateUser(SceneUser sceneUser) {
//        UserPlace userPlace = userPlaces[sceneUser.getLocationIndex()];
//        userPlace.setUserId(sceneUser.getUserId());
//        userPlace.setUserName(sceneUser.getUserName());
//    }
//
//    public int getChapterNums() {
//        return chapterNums;
//    }
//
//    public int getLeftChapterNums() {
//        return room.getConfig().getInt(RoomConfigInfo.CHAPTER_MAX) - chapterNums;
//    }
//
//    public PaiPoolInterface getPaiPool() {
//        return paiPool;
//    }
//
//    @Override
//    public Room getRoom() {
//        return this.room;
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
