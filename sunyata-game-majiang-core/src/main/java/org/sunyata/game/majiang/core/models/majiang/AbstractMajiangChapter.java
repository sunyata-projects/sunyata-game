package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.server.message.Notice;
import org.sunyata.game.server.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.majiang.core.models.NoticeType;
import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.majiang.actions.Action;
import org.sunyata.game.majiang.core.models.majiang.actions.MajiangAction;
import org.sunyata.game.majiang.core.models.majiang.actions.MajiangActionManager;
import org.sunyata.game.majiang.core.models.majiang.actions.OperationWhen;
import org.sunyata.game.majiang.core.models.majiang.actions.base.*;
import org.sunyata.game.majiang.core.models.message.*;
import org.sunyata.game.majiang.core.service.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by leo on 17/11/27.
 */
public abstract class AbstractMajiangChapter implements MajiangChapter {
    Logger logger = LoggerFactory.getLogger(AbstractMajiangChapter.class);
    protected int USER_NUMS = 4;
    protected Rules rules;
    protected Room room;
    private OperationFaPaiNew prevFaPai;

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    protected PaiPoolInterface paiPool;
    protected ComputeFanInterface computeFan;
    private JudgeHuService judgeHuService;
    protected Pai[] huiEr = null;

    protected int zhuangIndex = 0;//庄index 0 东 1南 2西 3北 逆时针顺序
    protected int quanIndex;//圈index 0 东 1南 2西 3北 逆时针顺序
    protected GameChapterEnd gameChapterEnd;
    protected int chapterNums;//局数, 0开始
    protected boolean isStart = false;
    protected TingPai[] tingPais = new TingPai[USER_NUMS];
    protected final UserPlace[] userPlaces = new UserPlace[USER_NUMS];

    protected boolean operationFaPaiIsGang;
    protected Pai currentChuPai = null;
    protected CheckResult cpghCheckResult;
    protected final List<CheckResult> checkResults = new ArrayList<>();//出牌时可进行操作合集

    protected List<Class<? extends MajiangAction>> actionMap = new ArrayList<>();

    protected int shouIndex = 0;
    /**
     * 操作开始时间
     */
    protected long operationTime = 0;

    protected OperationFaPaiNew operationFaPai;

    protected OperationOut operationOut;

    protected OperationCPGH operationCPGH;
    /**
     * 当前打牌的人
     */
    protected int currentIndex = 0;

    protected int waitCurrentIndex = -1;

    public Pai getCurrentChuPai() {
        return currentChuPai;
    }

    @Override
    public OperationFaPaiNew getOperationFaPai() {
        return operationFaPai;
    }

    @Override
    public OperationFaPaiNew getOperationPrevFaPai() {
        return prevFaPai;
    }

    @Override
    public boolean getOperationFaPaiIsGang() {
        return operationFaPaiIsGang;
    }

    public OperationCPGH getOperationCPGH() {
        return operationCPGH;
    }

    @Override
    public int getWaitCurrentIndex() {
        return waitCurrentIndex;
    }

    public AbstractMajiangChapter(Room roomInfo, Rules rule, PaiPoolInterface paiPool, ComputeFanInterface
            computeFan, JudgeHuService judgeHuService) {
        this.rules = rule;
        this.paiPool = paiPool;
        this.room = roomInfo;
        this.computeFan = computeFan;
        this.judgeHuService = judgeHuService;

        for (int i = 0; i < rules.getUserMax(); i++) {
            userPlaces[i] = new UserPlace(judgeHuService);
            userPlaces[i].setLocationIndex(i);
        }

        initProcessMap();

    }

    protected void initProcessMap() {
        actionMap.add(AnGangAfterFaPaiAction.class);
        actionMap.add(ChiAfterOtherChuPaiAction.class);
        actionMap.add(DaMingGangAfterOtherChuPaiAction.class);
        actionMap.add(HuAfterFaPaiAction.class);
        actionMap.add(HuAfterOtherChuPaiAction.class);
        actionMap.add(PengAfterOtherChuPaiAction.class);
        actionMap.add(XiaoMingGangAfterFaPaiAction.class);
        actionMap.add(GuoAfterOtherChuPaiAction.class);
        actionMap.add(OutChuPaiAfterFaPaiAction.class);
        actionMap.add(OutAfterOtherChuPaiAction.class);
    }

    protected void changeCurrentIndex(int index) {
        this.currentIndex = index;
        shouIndex++;
    }

    @Override
    public void start() {
        clear();
        //this.rules.rest();
        gameChapterEnd = null;
        isStart = true;
        //发牌
        paiPool.start();
        //开始发牌给玩家，

        //for (int i = 0; i < userPlaces.length; i++) {
        for (int i = 0; i < rules.getUserMax(); i++) {
            paiPool.faPai(i, userPlaces[i]);
        }

        paiPool.onFaPaiEnd(this);
        huiEr = paiPool.getHuiEr();
        //发牌完毕！
        changeCurrentIndex(zhuangIndex);
    }

    public void startNext() throws IllegalAccessException, InstantiationException {
        faPai(true, false);
        logger.debug("发牌完毕！{}", this);
    }

    /**
     * 去下一个玩家
     */
    protected void goNext() throws IllegalAccessException, InstantiationException {
        //int next = (currentIndex + 1) %  userPlaces.length;
        int next = (currentIndex + 1) % rules.getUserMax();
        changeCurrentIndex(next);
        faPai(true, false);
    }

    public void faPai(boolean isSendMessage, boolean isGang) throws InstantiationException, IllegalAccessException {
        if (!(paiPool.size() > getBaoliuLength())) {
            huangPai();
            return;
        }
        Pai pai = isGang ? paiPool.getFreeGangPai(currentIndex) : paiPool.getFreePai(currentIndex);
        if (pai == null) {
            huangPai();
            logger.info("黄牌了,娘亲");
            return;
        }
        operationFaPai = new OperationFaPaiNew();

        operationFaPaiIsGang = isGang;
        operationFaPai.setIndex(currentIndex);
        operationFaPai.setPai(pai.getIndex());


        UserPlace userPlace = userPlaces[currentIndex];
        userPlace.changeFa(pai);
        int sequence = rules.getUserMax() - currentIndex;
        List<CheckResult> checkResults = MajiangActionManager.generateOperation(OperationWhen.chuPaiAfterFaPai, this,
                this.actionMap, userPlace, pai,
                currentIndex, sequence);
        for (CheckResult result : checkResults) {
            MajiangActionManager.convertToOperationCPGH(OperationWhen.chuPaiAfterFaPai, this, actionMap,
                    operationFaPai, result, pai,
                    currentIndex);
        }

        //根据情况发送消息，初始化不用，因为后面同步场景会同步操作到客户端
        syncHidePai(OperationNames.OPT_FA, pai.getIndex());
        onOperationStart();
        if (isSendMessage) {
            sendMessage(currentIndex, operationFaPai);
        }
    }

    public void outRet(int locationIndex, int paiIndex) throws Exception {
        Pai pai = Pai.fromIndex(paiIndex);
        if (locationIndex != operationOut.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationOut.getIndex());
        }
        operationOut = null;
        chuPai(userPlaces[currentIndex], locationIndex, pai);
    }

    public void faPaiRet(int locationIndex, String opt, int paiIndex) throws Exception {
        Pai pai = Pai.fromIndex(paiIndex);
        if (operationFaPai == null) {
            logger.error("发牌操作已经结束！index:{},opt:{},pai:{}");
        }
        if (locationIndex != operationFaPai.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
        }
        this.prevFaPai = this.operationFaPai;
        this.operationFaPai = null;
        UserPlace userPlace = userPlaces[locationIndex];
        getAction(OperationWhen.chuPaiAfterFaPai, opt).processAction(this, userPlace, pai, locationIndex, null);
        this.prevFaPai = null;
    }

    public void cpghRet(int locationIndex, String opt, int[] chi) throws Exception {
        if (operationCPGH == null) {
            logger.error("发牌操作已经结束！index:{},opt:{},pai:{}");
        }
        if (locationIndex != operationCPGH.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
        }
        UserPlace userPlace = userPlaces[locationIndex];

        getAction(OperationWhen.whenOtherChuPai, opt).processAction(this, userPlace, currentChuPai, locationIndex, chi);
    }

    public void chuPai(UserPlace userPlace, int locationIndex, Pai pai) throws Exception {
        if (locationIndex != currentIndex) {
            throw new RuntimeException(
                    "发牌用户错误，不是当前用户(发牌用户：locationIndex:" + locationIndex + ",当前操作用户：currentIndex:" + currentIndex + ")"
            );
        }
        if (!userPlace.checkShouPai(pai)) {
            logger.error("打出的不是手牌：" + pai);
            return;
        }

        //开始出来操作
        currentChuPai = pai;

        userPlace.removeShouPai(pai);
        userPlace.addAllOut(pai);
        generateCPGHResultsWhenChuPai(userPlace, locationIndex, pai);
        sync(OperationNames.OPT_OUT, pai);
        checkTingPai();
        waitCurrentIndex = currentIndex;
        nextCPGHOrStartNext();
    }

    protected void generateCPGHResultsWhenChuPai(UserPlace userPlace, int locationIndex, Pai pai) throws
            InstantiationException, IllegalAccessException {
        //检查其他三家 吃 碰 杠 胡
        //for (int i = locationIndex, j = 0; i < (locationIndex + userPlaces.length); i++, j++) {
        for (int i = locationIndex, j = 0; i < (locationIndex + rules.getUserMax()); i++, j++) {
            //int index = i % userPlaces.length;
            int index = i % rules.getUserMax();
            UserPlace current = userPlaces[index];
            if (current != userPlace) {
                int sequence = rules.getUserMax() - i;
                List<CheckResult> results = MajiangActionManager.generateOperation(OperationWhen
                                .whenOtherChuPai, this, this.actionMap, current,
                        pai, index, sequence);
                this.checkResults.addAll(results);
            }
        }
        if (checkResults.size() > 0) {
            checkResults.sort(
                    (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority())
            );
        }
    }

    private void checkTingPai() {
        UserPlace userPlace = userPlaces[currentIndex];

        ArrayList<Pai> pais = userPlace.checkTingPai(rules.isHuiErGang(), rules.getAllPai(), huiEr);

        TingPai tingPai = new TingPai(pais.stream().mapToInt(Pai::getIndex).toArray());
        tingPais[currentIndex] = tingPai;
        sendMessage(currentIndex, tingPai);
    }

    public void stopCPGH(boolean isSync) {
        if (isSync) {
            sync(OperationNames.OPT_OUT_OK, currentChuPai);
        }
        checkResults.clear();
        operationCPGH = null;
        userPlaces[waitCurrentIndex].clearOutingPai();
        waitCurrentIndex = -1;
        currentChuPai = null;
        cpghCheckResult = null;
    }

    public void nextCPGHOrStartNext() throws InstantiationException, IllegalAccessException {
        if (checkResults.size() > 0) {
            List<CheckResult> results = new ArrayList<>();
            CheckResult checkResult = checkResults.remove(0);
            results.add(checkResult);
            List<CheckResult> collect = checkResults.stream().filter(p -> p.getLocationIndex() == checkResult
                    .getLocationIndex()).collect(Collectors.toList());
            if (collect != null) {
                results.addAll(collect);
                checkResults.removeAll(collect);
            }

            operationCPGH = new OperationCPGH();
            for (CheckResult checkResultItem : results) {
                MajiangActionManager.convertToOperationCPGH(OperationWhen.whenOtherChuPai, this, actionMap,
                        operationCPGH, checkResultItem, getCurrentChuPai(), currentIndex);
            }
            operationCPGH.setIndex(checkResult.getLocationIndex());

            CheckResult chiCheckResult = results.stream().filter(p -> p.getActionName().equalsIgnoreCase(OperationNames
                    .OPT_CHI)).findFirst().orElse(null);
            this.cpghCheckResult = chiCheckResult == null ? checkResult : chiCheckResult;

            changeCurrentIndex(checkResult.getLocationIndex());

            onOperationStart();
            //发送玩家可以吃碰杠和
            sendMessage(checkResult.getLocationIndex(), operationCPGH);
        } else {
            changeCurrentIndex(waitCurrentIndex);
            userPlaces[currentIndex].addOut(currentChuPai);
            stopCPGH(true);
            goNext();
        }
    }

    public void xiaoMingGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException {
        userPlace.xiaoMingGang(pai);
        sync(OperationNames.OPT_XIAO_MING_GANG, pai);
        faPai(true, true);
    }

    public void anGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException {
        userPlace.anGang(shouIndex, pai);
        syncHidePai(OperationNames.OPT_AN_GANG, pai.getIndex());
        faPai(true, true);
    }

    public void chi(UserPlace userPlace, Pai pai, int[] chi) {
        if (Arrays.binarySearch(chi, pai.getIndex()) == -1) {
            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
        }
        List<Pai[]> getPais = new ArrayList<>();
        List<Pai[]> pais1 = cpghCheckResult.getPais();
        getPais.addAll(pais1);
        boolean isCheckOk = false;
        CHI_OUT:
        for (int i = 0; i < getPais.size(); i++) {
            Pai[] pais = getPais.get(i);
            for (int j = 0; j < pais.length; j++) {
                if (pais[j].getIndex() != chi[j]) {
                    continue CHI_OUT;
                }
            }
            isCheckOk = true;
            break;
        }
        if (!isCheckOk) {
            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
        }
        userPlace.chi(pai, chi);
        syncChi(OperationNames.OPT_CHI, pai, chi);
        //通知 吃碰杠胡
        stopCPGH(false);
        //通知出牌
        notifyOut();
    }

    public void peng(UserPlace userPlace, Pai pai) {
        userPlace.peng(pai);
        sync(OperationNames.OPT_PENG, pai);
        stopCPGH(false);
        notifyOut();
    }

    public void daMingGang(UserPlace userPlace, Pai pai) throws IllegalAccessException, InstantiationException {
        userPlace.daMingGang(pai);
        sync(OperationNames.OPT_DA_MING_GANG, pai);
        stopCPGH(false);
        faPai(true, true);
    }

    public void huPai(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex, boolean isGangShangHua)
            throws IllegalAccessException, InstantiationException {
        if (fangPaoIndex > -1) {
            userPlace.addShouPai(pai);
        }
        this.end(locationIndex, fangPaoIndex, isGangShangHua);
    }


    public void syncHidePai(String opt, int... pais) {
        if (pais.length > 1) {
            int[] hidePai = new int[pais.length];
            Arrays.fill(hidePai, Pai.HAS_PAI_INDEX);
            room.sendMessage(currentIndex,
                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais),
                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, hidePai)
            );
        } else {
            room.sendMessage(currentIndex,
                    new SyncOpt(opt, currentIndex, pais[0], null),
                    new SyncOpt(opt, currentIndex, Pai.HAS_PAI_INDEX, null)
            );
        }
    }

    protected void end(int huPaiLocationIndex, int fangPaoIndex, boolean isGangShangHua) throws
            IllegalAccessException, InstantiationException {
        computeFan = computeFan.build(this, huPaiLocationIndex, fangPaoIndex, isGangShangHua,judgeHuService);
        ChapterEndResult endResult = computeFan.compute();
        //开始处理扎码
        if (endResult.isHuPai()) {
            if (zhuangIndex != huPaiLocationIndex) {
                zhuangIndex = (zhuangIndex + 1) % rules.getUserMax();
                if (zhuangIndex == 0) {//一圈
                    quanIndex = (quanIndex + 1) % rules.getUserMax();
                }
            }

            //int zaMaScore = computeFan.zaMa();

            //int fanNums = endResult.getFanNums();// + zaMaScore;
            //endResult.excuteScore();
            computeFan.executeScore(endResult);
            //computeFan.computeGuaFengXiaYu();
            room.getRoomInfo().changeScore(endResult.getUserPaiInfos());
        }
        GameChapterEnd msg = endResult.toMessage();
        gameChapterEnd = msg;
        chapterNums++;


        isStart = false;
        //clear();
        operationCPGH = null;
        operationFaPai = null;
        operationOut = null;

        room.sendMessage(msg);
        try {
            room.endChapter(endResult, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        operationCPGH = null;
//        operationFaPai = null;
//        operationOut = null;
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        startNextChapter();
    }

    private void startNextChapter() throws InstantiationException, IllegalAccessException {
        start();
        for (SceneUser u : room.getRoomInfo().getUsers()) {
            if (u != null && u.isJoinGame()) {
                u.sendMessage(toMessage(u.getLocationIndex()));
            }
        }
        startNext();
    }

    private void clear() {
        Arrays.fill(tingPais, null);
        paiPool.clear();
        huiEr = null;
        for (UserPlace u : userPlaces) {
            if (u != null) {
                u.clear();
            }
        }
    }

    public void sync(String opt, Pai... pais) {
        sync(opt, Arrays.stream(pais).mapToInt(Pai::getIndex).toArray());
    }

    protected void huangPai() throws IllegalAccessException, InstantiationException {
        end(-1, -1, false);
    }

    public int getNextIndex(int curIndex) {
        return (currentIndex + 1) % rules.getUserMax();
    }

    public boolean isStart() {
        return isStart;
    }

    public ArrayList<Pai> getLeftPai() {
        return paiPool.getLeftPai();
    }

    public int getZhuangIndex() {
        return zhuangIndex;
    }

    public UserPlace[] getUserPlaces() {
        return userPlaces;
    }


    public Pai[] getHuiEr() {
        return huiEr;
    }

    public Rules getRules() {
        return rules;
    }

    public int getChapterNums() {
        return chapterNums;
    }

    public int getLeftChapterNums() {
        return room.getConfig().getInt(RoomConfigInfo.CHAPTER_MAX) - chapterNums;
    }

    public PaiPoolInterface getPaiPool() {
        return paiPool;
    }

    protected int getBaoliuLength() {
        return rules.getBaoliuLength();
    }

    public Room getRoom() {
        return this.room;
    }

    /**
     * 是否最后一张牌
     */
    public boolean isLastPai() {
        return paiPool.size() <= getBaoliuLength();
    }

    protected MajiangAction getAction(OperationWhen operationWhen, String opt) throws IllegalAccessException,
            InstantiationException {
        Class<? extends MajiangAction> aClass = actionMap.stream().filter(p -> p.getAnnotation(Action.class)
                .operationWhen() == operationWhen && p.getAnnotation(Action.class).value().equals(opt))
                .findFirst().orElse(null);
        if (aClass != null) {
            return aClass.newInstance();
        }
        throw new RuntimeException("错误的操作用户:" + opt);
    }

    protected void notifyOut() {
        operationOut = new OperationOut(currentIndex);
        onOperationStart();
        sendMessage(currentIndex, operationOut);
    }

    public void syncChi(String opt, Pai pai, int... pais) {
        room.sendMessage(new SyncOpt(opt, currentIndex, pai.getIndex(), pais));
    }

    public void sync(String opt, int... pais) {
        if (pais.length > 1) {
            room.sendMessage(new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais));
        } else {
            room.sendMessage(new SyncOpt(opt, currentIndex, pais[0], null));
        }
    }

    protected void onOperationStart() {
        operationTime = System.currentTimeMillis();
        syncOptTime();
    }

    public boolean isOperationTimeOut(long now) {
        return (now - operationTime) > rules.getShouTimeMillisecond();
    }

    public void syncOptTime() {
        room.sendMessage(getSyncOptTime());
    }

    protected SyncOptTime getSyncOptTime() {
        int leftTime = 0;
        if (operationTime > 0) {
            leftTime = (int) (
                    rules.getShouTimeMillisecond() - (System.currentTimeMillis() - operationTime)
            );
        }
        return new SyncOptTime(currentIndex, leftTime);
    }


    public void updateUser(SceneUser sceneUser) {
        UserPlace userPlace = userPlaces[sceneUser.getLocationIndex()];
        userPlace.setUserId(sceneUser.getUserId());
        userPlace.setUserName(sceneUser.getUserName());
    }


    public MajiangChapterMsg toMessage(int myLocationIndex) {

        MajiangChapterMsg m = new MajiangChapterMsg();
        m.setBaoliuLength(getBaoliuLength());
        m.setFreeLength(paiPool.size());
        if (huiEr != null) {
            m.setHuiEr(Arrays.stream(huiEr).mapToInt(Pai::getIndex).toArray());
        } else {
            m.setHuiEr(null);
        }
        m.setChapterNumsMax(this.room.getConfig().getInt(RoomConfigInfo.CHAPTER_MAX));
        m.setChapterNums(chapterNums);
        m.setQuanIndex(quanIndex);
        m.setZhuangIndex(zhuangIndex);
        String bingType = room.getConfig().getString(RoomConfigInfo.BIAN_TYPE);
        m.setBianType(bingType);
        if (Objects.equals(bingType, RoomConfigInfo.BIAN_TYPE_DAN_GUI) || Objects.equals(bingType, RoomConfigInfo
                .BIAN_TYPE_SHUANG_GUI)) {
            m.setBianSource(huiEr[0].prev().getIndex());
        } else {
            m.setBianSource(Pai.NOT_PAI_INDEX);
        }


//        for (int i = 0; i < userPlaces.length; i++) {
        for (int i = 0; i < rules.getUserMax(); i++) {
            UserPlace userPlace = userPlaces[i];
            m.addUserPlace(userPlace.toMessage(i == myLocationIndex, shouIndex));
        }
        m.setCurrentIndex(currentIndex);
        m.setWailIndex(waitCurrentIndex);

        if (operationFaPai != null && operationFaPai.getIndex() == myLocationIndex) {
            m.setOptFaPai(operationFaPai);
        }
        if (operationCPGH != null && operationCPGH.getIndex() == myLocationIndex) {
            m.setOptCpgh(operationCPGH);
        }
        if (operationOut != null && operationOut.getIndex() == myLocationIndex) {
            m.setOptOut(operationOut);
        }
        if (tingPais[myLocationIndex] != null) {
            m.setTingPai(tingPais[myLocationIndex]);
        }
        if (operationTime > 0) {
            m.setSyncOptTime(getSyncOptTime());
        }
        if (gameChapterEnd != null) {
            m.setGameChapterEnd(gameChapterEnd);
        }
        return m;
    }

    @Override
    public void sendMessage(int locationIndex, Message msg) {
        getRoom().sendMessage(locationIndex, msg);
    }

    public void notice(int locationIndex, String key, String[] args, NoticeType noticeType) {
        getRoom().sendMessage(locationIndex, new Notice(key, args, noticeType.ordinal(), false));

    }

    public void noticeError(int locationIndex, String key, String[] args) {
        notice(locationIndex, key, args, NoticeType.ERROR);
    }
}
