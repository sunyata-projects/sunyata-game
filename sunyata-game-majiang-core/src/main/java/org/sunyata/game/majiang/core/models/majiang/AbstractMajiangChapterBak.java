//package org.sunyata.game.majiang.core.models.majiang;
//
//import Message;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.sunyata.game.contract.majiang.OperationNames;
//import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
//import org.sunyata.game.majiang.core.models.SceneUser;
//import org.sunyata.game.majiang.core.models.majiang.processes.*;
//import org.sunyata.game.majiang.core.models.message.*;
//import org.sunyata.game.majiang.core.service.Room;
//
//import java.util.*;
//
///**
// * Created by leo on 17/11/27.
// */
//public abstract class AbstractMajiangChapterBak implements MajiangChapter {
//    Logger logger = LoggerFactory.getLogger(AbstractMajiangChapterBak.class);
//    protected int USER_NUMS = 4;
//    protected ComputeFanInterface computeFan;
//    protected int zhuangIndex;//庄index 0 东 1南 2西 3北 逆时针顺序
//    protected int quanIndex;//圈index 0 东 1南 2西 3北 逆时针顺序
//    protected Rules rules;
//    protected Room room;
//    protected GameChapterEnd gameChapterEnd;
//    protected int chapterNums;//局数, 0开始
//    protected boolean isStart = false;
//    protected TingPai[] tingPais = new TingPai[USER_NUMS];
//    protected final UserPlace[] userPlaces = new UserPlace[USER_NUMS];
//    protected PaiPoolInterface paiPool;
//    protected Pai[] huiEr = null;
//
//    protected OperationFaPai operationFaPai;
//    protected boolean operationFaPaiIsGang;
//
//    protected Pai currentChuPai = null;
//
//    protected CheckResult cpghCheckResult;
//
//    protected HashMap<String, Class<? extends OptProcess>> processMap = new HashMap<>();
//
//    protected final List<CheckResult> checkResults = new ArrayList<>();
//
//    protected int shouIndex = 0;
//    /**
//     * 操作开始时间
//     */
//    protected long operationTime = 0;
//
//
//    protected OperationOut operationOut;
//
//    @Override
//    public OperationFaPai getOperationFaPai() {
//        return operationFaPai;
//    }
//
//    @Override
//    public boolean getOperationFaPaiIsGang() {
//        return operationFaPaiIsGang;
//    }
//
//    @Override
//    public int getWaitCurrentIndex() {
//        return waitCurrentIndex;
//    }
//
//    protected OperationCPGH operationCPGH;
//    /**
//     * 当前打牌的人
//     */
//    protected int currentIndex = 0;
//
//    protected int waitCurrentIndex = -1;
//
//    public AbstractMajiangChapterBak(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface
//            computeFan) {
//        this.rules = rule;
//        this.paiPool = paiPool;
//        this.room = roomInfo;
//        this.computeFan = computeFan;
//
//        for (int i = 0; i < rules.getUserMax(); i++) {
//            userPlaces[i] = new UserPlace();
//            userPlaces[i].setLocationIndex(i);
//        }
//
//        initProcessMap();
//
//    }
//
//    protected void initProcessMap() {
//        processMap.put(OperationNames.OPT_HU, HuProcess.class);
//        processMap.put(OperationNames.OPT_DA_MING_GANG, DaMingGangProcess.class);
//        processMap.put(OperationNames.OPT_PENG, PengProcess.class);
//        processMap.put(OperationNames.OPT_CHI, ChiProcess.class);
//        processMap.put(OperationNames.OPT_GUO, GuoProcess.class);
//    }
//
//    protected void changeCurrentIndex(int index) {
//        this.currentIndex = index;
//        shouIndex++;
//    }
//
//
//    @Override
//    public void sendMessage(int locationIndex, Message msg) {
//        getRoom().sendMessage(locationIndex, msg);
//    }
//
//    @Override
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
//    public void startNext() {
//        faPai(true, false);
//        logger.debug("发牌完毕！{}", this);
//    }
//
//    /**
//     * 去下一个玩家
//     */
//    protected void goNext() {
//        //int next = (currentIndex + 1) %  userPlaces.length;
//        int next = (currentIndex + 1) % rules.getUserMax();
//        changeCurrentIndex(next);
//        faPai(true, false);
//    }
//
//    protected int getNextIndex(int curIndex) {
//        return (currentIndex + 1) % rules.getUserMax();
//    }
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
//    //    private void changeCurrentIndex(int index) {
////        this.currentIndex = index;
////        shouIndex++;
////    }
//    public void outRet(int locationIndex, int paiIndex) {
//        Pai pai = Pai.fromIndex(paiIndex);
//        if (locationIndex != operationOut.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationOut.getIndex());
//        }
//        operationOut = null;
//        chuPai(userPlaces[currentIndex], locationIndex, pai);
//    }
//
//    public void chuPai(UserPlace userPlace, int locationIndex, Pai pai) {
//        if (locationIndex != currentIndex) {
//            throw new RuntimeException(
//                    "发牌用户错误，不是当前用户(发牌用户：locationIndex:" + locationIndex + ",当前操作用户：currentIndex:" + currentIndex + ")"
//            );
//        }
//        if (!userPlace.checkShouPai(pai)) {
////            throw new RuntimeException("打出的不是手牌：" + pai);
//            logger.error("打出的不是手牌：" + pai);
//            return;
//        }
////        if (userPlace.getFa() == null) {
////            throw new RuntimeException("操作用户未发牌？：" + userPlace);
////        }
//        //开始出来操作
//        currentChuPai = pai;
//
//        userPlace.removeShouPai(pai);
//        userPlace.addAllOut(pai);
//        generateCPGHResultsWhenChuPai(userPlace, locationIndex, pai);
//        sync(OperationNames.OPT_OUT, pai);
//        checkTingPai();
//        waitCurrentIndex = currentIndex;
//        checkCPGH();
//    }
//
//    protected CheckResult generateCPGHResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace
//            curUserPlace,
//                                                        Pai pai) {
////        generateCPGHChiResultForLocation(locationIndex, outUserPlace, curUserPlace, pai, checkResult);
////        generateCPGHPengResultForLocation(locationIndex, outUserPlace, curUserPlace, pai, checkResult);
////        generateCPGHGangResultForLocation(locationIndex, outUserPlace, curUserPlace, pai, checkResult);
////        generateCPGHFangPaoResultForLocation(locationIndex, outUserPlace, curUserPlace, pai, checkResult);
//        return null;
//    }
//
//    protected void generateCPGHChiResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace current, Pai
//            pai, CheckResult checkResult) {
//        if (rules.allowChi()) {
//            List<Pai[]> chi = current.isChi(pai);
//            //checkResult.setChi(current.isChi(pai));
//            checkResult.setActionName(OperationNames.OPT_CHI).setPais(chi);
//        }
//        //else {
////            checkResult.setChi(new ArrayList<>());
////        }
//    }
//
//    protected void generateCPGHPengResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace
//            curUserPlace, Pai pai, CheckResult checkResult) {
//        boolean peng = curUserPlace.isPeng(pai);
//
//        //checkResult.setPeng(curUserPlace.isPeng(pai));
//        if (peng) {
//            checkResult.setActionName(OperationNames.OPT_PENG).setPais(null);
//        }
//    }
//
//    protected void generateCPGHGangResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace
//            curUserPlace, Pai pai, CheckResult checkResult) {
//        //checkResult.setGang(curUserPlace.isDaMingGang(pai));
//        boolean daMingGang = curUserPlace.isDaMingGang(pai);
//        if (daMingGang) {
//            checkResult.setActionName(OperationNames.OPT_DA_MING_GANG).setPais(null);
//        }
//
//    }
//
//    protected void generateCPGHFangPaoResultForLocation(int locationIndex, UserPlace outUserPlace, UserPlace
//            curUserPlace, Pai pai, CheckResult checkResult) {
////        if (rules.allowFangPao()) {
////            checkResult.setHu(curUserPlace.isHuPaiBy(pai));
////        }
//        boolean huPaiBy = curUserPlace.isHuPaiBy(pai);
//        if (huPaiBy) {
//            checkResult.setActionName(OperationNames.OPT_HU).setPais(null);
//        }
//    }
//
//    protected CheckResult createNewCheckResult() {
//        return new CheckResult();
//    }
//
//    protected void generateCPGHResultsWhenChuPai(UserPlace userPlace, int locationIndex, Pai pai) {
//        //检查其他三家 吃 碰 杠 胡
//        //for (int i = locationIndex, j = 0; i < (locationIndex + userPlaces.length); i++, j++) {
//        for (int i = locationIndex, j = 0; i < (locationIndex + rules.getUserMax()); i++, j++) {
//            //int index = i % userPlaces.length;
//            int index = i % rules.getUserMax();
//            UserPlace current = userPlaces[index];
//            if (current != userPlace) {
//                //CheckResult checkResult = createNewCheckResult();
////                if (rules.allowChi()) {
////                    checkResult.setChi(current.isChi(pai));
////                } else {
////                    checkResult.setChi(new ArrayList<>());
////                }
////                checkResult.setPeng(current.isPeng(pai));
////                checkResult.setGang(current.isDaMingGang(pai));
////
////                if (rules.allowFangPao()) {
////                    checkResult.setHu(current.isHuPaiBy(pai));
////                }
//                CheckResult checkResult = generateCPGHResultForLocation(locationIndex, userPlace, current, pai);
//                if (checkResult != null) {
//                    checkResult.setLocationIndex(index);
//                    checkResult.setSequence(rules.getUserMax() - i);
//                    if (checkResult.hasOperation()) {
//                        checkResults.add(checkResult);
//                    }
//                }
//            }
//        }
//        if (checkResults.size() > 0) {
//            checkResults.sort(
//                    (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority())
//            );
//        }
//    }
//
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
//    public void stopCPGH(boolean isSync) {
//        if (isSync) {
//            sync(OperationNames.OPT_OUT_OK, currentChuPai);
//        }
//        checkResults.clear();
//        operationCPGH = null;
//        waitCurrentIndex = -1;
//        currentChuPai = null;
//        cpghCheckResult = null;
//    }
//
//    protected void checkCPGHChi(OperationCPGH operationCPGH, CheckResult checkResult) {
//        int[] ints = MajiangUtils.toIndexByDyadicArray(checkResult.getPais());
//        if (ints != null && ints.length > 0) {//有吃的牌
//            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_CHI).setPai(ints));
//        }
//    }
//
//    protected void checkCPGHPeng(OperationCPGH operationCPGH, CheckResult checkResult) {
//        //if (checkResult.isPeng()) {
//        if (checkResult.getActionName().equals(OperationNames.OPT_PENG)) {
//            //operationCPGH.setIsPeng(checkResult.isPeng());
//            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_PENG).setPai(new int[]{currentChuPai
//                    .getIndex()}));
//        }
//    }
//
//    protected void checkCPGHGang(OperationCPGH operationCPGH, CheckResult checkResult) {
//        //if (checkResult.isGang()) {
//        if (checkResult.getActionName().equals(OperationNames.OPT_DA_MING_GANG)) {
//            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_DA_MING_GANG).setPai(new
//                    int[]{currentChuPai.getIndex()}));
//        }
//    }
//
//    protected void checkCPGHHu(OperationCPGH operationCPGH, CheckResult checkResult) {
//        //if (checkResult.isHu()) {
//        if (checkResult.getActionName().equals(OperationNames.OPT_HU)) {
//            operationCPGH.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_HU).setPai(new int[]{currentChuPai
//                    .getIndex()}));
//        }
//    }
//
//    public void checkCPGH() {
//        if (checkResults.size() > 0) {
//            CheckResult checkResult = checkResults.remove(0);
//            operationCPGH = new OperationCPGH();
//
//            checkCPGHChi(operationCPGH, checkResult);
//            checkCPGHPeng(operationCPGH, checkResult);
//            checkCPGHGang(operationCPGH, checkResult);
//            checkCPGHHu(operationCPGH, checkResult);
//
//
//            operationCPGH.setIndex(checkResult.getLocationIndex());
//
//            this.cpghCheckResult = checkResult;
//
//            changeCurrentIndex(checkResult.getLocationIndex());
//
//            onOperationStart();
//            //发送玩家可以吃碰杠和
//            sendMessage(checkResult.getLocationIndex(), operationCPGH);
//        } else {
//            changeCurrentIndex(waitCurrentIndex);
//            userPlaces[currentIndex].addOut(currentChuPai);
//
//            stopCPGH(true);
//            goNext();
//        }
//    }
//
//
//    public void xiaoMingGang(UserPlace userPlace, Pai pai) {
//        userPlace.xiaoMingGang(pai);
//        sync(OperationNames.OPT_XIAO_MING_GANG, pai);
//        faPai(true, true);
//    }
//
//    public void anGang(UserPlace userPlace, Pai pai) {
//        userPlace.anGang(shouIndex, pai);
//        syncHidePai(OperationNames.OPT_AN_GANG, pai.getIndex());
//        faPai(true, true);
//    }
//
//
//    public void faPaiRet(int locationIndex, String opt, int paiIndex) throws IllegalAccessException,
//            InstantiationException {
//        Pai pai = Pai.fromIndex(paiIndex);
//        if (operationFaPai == null) {
//            logger.error("发牌操作已经结束！index:{},opt:{},pai:{}");
//        }
//        if (locationIndex != operationFaPai.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
//        }
//        OperationFaPai prev = this.operationFaPai;
//        this.operationFaPai = null;
//        ////OUT:打牌,AN_GANG:暗杠牌,XIAO_MING_GANG:暗杠牌,HU:胡牌
//        UserPlace userPlace = userPlaces[locationIndex];
//        Class<? extends OptProcess> orDefault = processMap.getOrDefault(opt, null);
//        OptProcess optProcess = orDefault.newInstance();
//        if (optProcess == null) {
//            throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
//        }
//        optProcess.process(true, this, userPlace, locationIndex, pai, -1, null);
////        switch (opt) {
////            case OperationNames.OPT_OUT:
////                chuPai(userPlace, locationIndex, pai);
////                break;
////            case OperationNames.OPT_AN_GANG:
////                if (!ArrayUtils.isEmpty(prev.getAnGang())) {
////                    anGang(userPlace, pai);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
////                break;
////            case OperationNames.OPT_XIAO_MING_GANG:
////                if (!ArrayUtils.isEmpty(prev.getMingGang())) {
////                    xiaoMingGang(userPlace, pai);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
////                break;
////            case OperationNames.OPT_HU:
////                if (prev.getHu()) {
////                    huPai(userPlace, locationIndex, currentChuPai, -1, operationFaPaiIsGang);
////                } else {
////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                }
////                break;
////            default:
////                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
////        }
//    }
////
////    protected void proecssCPGHOpt(String opt, UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex,
////                                  boolean isGangShangHua) {
////
////    }
//
////    protected void processCPGHHuRet(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex,
////                                    boolean isGangShangHua) {
////        huPai(userPlace, locationIndex, currentChuPai, waitCurrentIndex, false);
////        stopCPGH(false);
////    }
//
//
//    public void cpghRet(int locationIndex, String opt, int[] chi) throws IllegalAccessException,
//            InstantiationException {
//        if (operationCPGH == null) {
//            logger.error("发牌操作已经结束！index:{},opt:{},pai:{}");
//        }
//        if (locationIndex != operationCPGH.getIndex()) {
//            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
//        }
//        UserPlace userPlace = userPlaces[locationIndex];
//        Class<? extends OptProcess> orDefault = processMap.getOrDefault(opt, null);
//        OptProcess optProcess = orDefault.newInstance();
//        if (optProcess == null) {
//            throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
//        }
//        optProcess.process(false, this, userPlace, locationIndex, currentChuPai, waitCurrentIndex, chi);
////        switch (opt) {
////            case OperationNames.OPT_HU:
////                //if (operationCPGH.getIsHu()) {
////                huPai(userPlace, locationIndex, currentChuPai, waitCurrentIndex, false);
////                stopCPGH(false);
////                //} else {
////                //throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
////                //}
////                break;
////            case OperationNames.OPT_DA_MING_GANG:
////                //if (operationCPGH.getIsGang()) {
////                daMingGang(userPlace, currentChuPai);
//////                } else {
//////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//////                }
////                break;
////            case OperationNames.OPT_PENG:
////                //if (operationCPGH.getIsPeng()) {
////                peng(userPlace, currentChuPai);
//////                } else {
//////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//////                }
////                break;
////            case OperationNames.OPT_CHI:
////                //if (!ArrayUtils.isEmpty(operationCPGH.getChi())) {
////                chi(userPlace, currentChuPai, chi);
//////                } else {
//////                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
//////                }
////                break;
////            case OperationNames.OPT_GUO:
////                checkCPGH();
////                break;
////            default:
////                //放弃
////                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
////        }
//    }
//
//    public void chi(UserPlace userPlace, Pai pai, int[] chi) {
//        if (Arrays.binarySearch(chi, pai.getIndex()) == -1) {
//            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
//        }
//        boolean isCheckOk = false;
//        CHI_OUT:
//        for (int i = 0; i < cpghCheckResult.getPais().size(); i++) {
//            Pai[] pais = cpghCheckResult.getPais().get(i);
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
//    public void peng(UserPlace userPlace, Pai pai) {
//        userPlace.peng(pai);
//        sync(OperationNames.OPT_PENG, pai);
//        stopCPGH(false);
//        notifyOut();
//    }
//
//    public void daMingGang(UserPlace userPlace, Pai pai) {
//        userPlace.daMingGang(pai);
//        sync(OperationNames.OPT_DA_MING_GANG, pai);
//        stopCPGH(false);
//        faPai(true, true);
//    }
//
//    public void huPai(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex, boolean isGangShangHua) {
//        if (fangPaoIndex > -1) {
//            userPlace.addShouPai(pai);
//        }
//        this.end(locationIndex, fangPaoIndex, isGangShangHua);
//    }
//
//
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
//    protected void end(int huPaiLocationIndex, int fangPaoIndex, boolean isGangShangHua) {
//        computeFan = computeFan.build(this, huPaiLocationIndex, fangPaoIndex, isGangShangHua);
//        ChapterEndResult endResult = computeFan.compute();
//        //开始处理扎码
//
//
//        if (endResult.isHuPai()) {
//            if (zhuangIndex != huPaiLocationIndex) {
//                zhuangIndex = (zhuangIndex + 1) % 4;
//                if (zhuangIndex == 0) {//一圈
//                    quanIndex = (quanIndex + 1) % 4;
//                }
//            }
//
//            int zaMaScore = computeFan.zaMa();
//
//            int fanNums = endResult.getFanNums() + zaMaScore;
//
//
//            endResult.excuteScore(fanNums);
//            computeFan.computeGuaFengXiaYu();
//
//            room.getRoomInfo().changeScore(endResult.getUserPaiInfos());
//        }
//        GameChapterEnd msg = endResult.toMessage();
//        gameChapterEnd = msg;
//        chapterNums++;
//
//
//        isStart = false;
//        clear();
//        room.sendMessage(msg);
//        room.endChapter(endResult, this);
//    }
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
//    public void sync(String opt, Pai... pais) {
//        sync(opt, Arrays.stream(pais).mapToInt(Pai::getIndex).toArray());
//    }
//
//    protected void huangPai() {
//        end(-1, -1, false);
//    }
//
//    protected void notifyOut() {
//        operationOut = new OperationOut(currentIndex);
//        onOperationStart();
//        sendMessage(currentIndex, operationOut);
//    }
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
//    protected void onOperationStart() {
//        operationTime = System.currentTimeMillis();
//        syncOptTime();
//    }
//
//    public boolean isOperationTimeOut(long now) {
//        return (now - operationTime) > rules.getShouTimeMillisecond();
//    }
//
//    public void syncOptTime() {
//        room.sendMessage(getSyncOptTime());
//    }
//
//    protected SyncOptTime getSyncOptTime() {
//        int leftTime = 0;
//        if (operationTime > 0) {
//            leftTime = (int) (
//                    rules.getShouTimeMillisecond() - (System.currentTimeMillis() - operationTime)
//            );
//        }
//        return new SyncOptTime(currentIndex, leftTime);
//    }
//
//    public void updateUser(SceneUser sceneUser) {
//        UserPlace userPlace = userPlaces[sceneUser.getLocationIndex()];
//        userPlace.setUserId(sceneUser.getUserId());
//        userPlace.setUserName(sceneUser.getUserName());
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
//    public Rules getRules() {
//        return rules;
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
//    protected int getBaoliuLength() {
//        return rules.getBaoliuLength();
//    }
//
//    public Room getRoom() {
//        return this.room;
//    }
//
//    /**
//     * 是否最后一张牌
//     */
//    public boolean isLastPai() {
//        return paiPool.size() <= getBaoliuLength();
//    }
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
//}
