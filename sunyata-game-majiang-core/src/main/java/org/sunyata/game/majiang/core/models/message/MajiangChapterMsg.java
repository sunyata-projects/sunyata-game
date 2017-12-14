package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;
import org.sunyata.game.majiang.core.models.majiang.GameChapterEnd;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一局麻将的信息
 * <p>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class MajiangChapterMsg extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.majiangChapterInfo);
    public static final int ID = 9;

    /**
     * 剩余张数
     */
    private int freeLength;
    /**
     * 保留张数
     */
    private int baoliuLength;
    /**
     * 会儿牌
     */
    private int[] huiEr;
    /**
     * 变化类型！！
     */
    private String bianType;
    /**
     * 变化类型！！
     */
    private int bianSource;
    private java.util.ArrayList<UserPlaceMsg> userPlace;
    /**
     * 当前操作用户
     */
    private int currentIndex;
    /**
     * 局数, 0开始
     */
    private int chapterNums;
    /**
     * 局数, 0开始
     */
    private int chapterNumsMax;
    /**
     * 圈index 0 东 1南 2西 3北 逆时针顺序
     */
    private int quanIndex;
    /**
     * 庄index 0 东 1南 2西 3北 逆时针顺序
     */
    private int zhuangIndex;
    private OperationCPGH optCpgh;
    private OperationFaPaiNew optFaPai;
    private OperationOut optOut;
    private SyncOptTime syncOptTime;
    private GameChapterEnd gameChapterEnd;
    private TingPai tingPai;
    private int wailIndex;

    public MajiangChapterMsg() {

    }

    public MajiangChapterMsg(int freeLength, int baoliuLength, int[] huiEr, String bianType, int bianSource, java
            .util.ArrayList<UserPlaceMsg> userPlace, int currentIndex, int chapterNums, int chapterNumsMax, int
                                     quanIndex, int zhuangIndex, OperationCPGH optCpgh, OperationFaPaiNew optFaPai,
                             OperationOut optOut,
                             SyncOptTime syncOptTime, GameChapterEnd gameChapterEnd, TingPai tingPai) {
        this.freeLength = freeLength;
        this.baoliuLength = baoliuLength;
        this.huiEr = huiEr;
        this.bianType = bianType;
        this.bianSource = bianSource;
        this.userPlace = userPlace;
        this.currentIndex = currentIndex;
        this.chapterNums = chapterNums;
        this.chapterNumsMax = chapterNumsMax;
        this.quanIndex = quanIndex;
        this.zhuangIndex = zhuangIndex;
        this.optCpgh = optCpgh;
        this.optFaPai = optFaPai;
        this.optOut = optOut;
        this.syncOptTime = syncOptTime;
        this.gameChapterEnd = gameChapterEnd;
        this.tingPai = tingPai;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        freeLength = in.readInt();
//        baoliuLength = in.readInt();
//        huiEr = in.readIntArray();
//        bianType = in.readString();
//        bianSource = in.readInt();
//
//        int userPlaceLen = in.readInt();
//        if (userPlaceLen == -1) {
//            userPlace = null;
//        } else {
//            userPlace = new java.util.ArrayList<UserPlaceMsg>(userPlaceLen);
//            for (int i = 0; i < userPlaceLen; i++) {
//                UserPlaceMsg userPlaceItem = new UserPlaceMsg();
//                userPlaceItem.decode(in);
//                userPlace.add(userPlaceItem);
//            }
//        }
//        currentIndex = in.readInt();
//        chapterNums = in.readInt();
//        chapterNumsMax = in.readInt();
//        quanIndex = in.readInt();
//        zhuangIndex = in.readInt();
//
//        boolean optCpghIsNotNull = in.readBoolean();
//        if (optCpghIsNotNull) {
//            optCpgh = new OperationCPGH();
//            optCpgh.decode(in);
//        } else {
//            optCpgh = null;
//        }
//
//        boolean optFaPaiIsNotNull = in.readBoolean();
//        if (optFaPaiIsNotNull) {
//            optFaPai = new OperationFaPaiNew();
//            optFaPai.decode(in);
//        } else {
//            optFaPai = null;
//        }
//
//        boolean optOutIsNotNull = in.readBoolean();
//        if (optOutIsNotNull) {
//            optOut = new OperationOut();
//            optOut.decode(in);
//        } else {
//            optOut = null;
//        }
//
//        boolean syncOptTimeIsNotNull = in.readBoolean();
//        if (syncOptTimeIsNotNull) {
//            syncOptTime = new SyncOptTime();
//            syncOptTime.decode(in);
//        } else {
//            syncOptTime = null;
//        }
//
//        boolean gameChapterEndIsNotNull = in.readBoolean();
//        if (gameChapterEndIsNotNull) {
//            gameChapterEnd = new GameChapterEnd();
//            gameChapterEnd.decode(in);
//        } else {
//            gameChapterEnd = null;
//        }
//
//        boolean tingPaiIsNotNull = in.readBoolean();
//        if (tingPaiIsNotNull) {
//            tingPai = new TingPai();
//            tingPai.decode(in);
//        } else {
//            tingPai = null;
//        }
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeInt(getFreeLength());
//        out.writeInt(getBaoliuLength());
//        out.writeIntArray(getHuiEr());
//        out.writeString(getBianType());
//        out.writeInt(getBianSource());
//
//        if (userPlace == null) {
//            out.writeInt(-1);
//        } else {
//            java.util.ArrayList<UserPlaceMsg> userPlaceList = getUserPlace();
//            int userPlaceLen = userPlaceList.size();
//            out.writeInt(userPlaceLen);
//            for (UserPlaceMsg userPlaceItem : userPlaceList) {
//                userPlaceItem.encode(out);
//            }
//        }
//        out.writeInt(getCurrentIndex());
//        out.writeInt(getChapterNums());
//        out.writeInt(getChapterNumsMax());
//        out.writeInt(getQuanIndex());
//        out.writeInt(getZhuangIndex());
//        OperationCPGH optCpghItem = getOptCpgh();
//        if (optCpghItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            optCpghItem.encode(out);
//        }
//        OperationFaPaiNew optFaPaiItem = getOptFaPai();
//        if (optFaPaiItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            optFaPaiItem.encode(out);
//        }
//        OperationOut optOutItem = getOptOut();
//        if (optOutItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            optOutItem.encode(out);
//        }
//        SyncOptTime syncOptTimeItem = getSyncOptTime();
//        if (syncOptTimeItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            syncOptTimeItem.encode(out);
//        }
//        GameChapterEnd gameChapterEndItem = getGameChapterEnd();
//        if (gameChapterEndItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            gameChapterEndItem.encode(out);
//        }
//        TingPai tingPaiItem = getTingPai();
//        if (tingPaiItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            tingPaiItem.encode(out);
//        }
//    }

    /**
     * 剩余张数
     */
    public int getFreeLength() {
        return freeLength;
    }

    /**
     * 剩余张数
     */
    public void setFreeLength(int freeLength) {
        this.freeLength = freeLength;
    }

    /**
     * 保留张数
     */
    public int getBaoliuLength() {
        return baoliuLength;
    }

    /**
     * 保留张数
     */
    public void setBaoliuLength(int baoliuLength) {
        this.baoliuLength = baoliuLength;
    }

    /**
     * 会儿牌
     */
    public int[] getHuiEr() {
        return huiEr;
    }

    /**
     * 会儿牌
     */
    public void setHuiEr(int[] huiEr) {
        this.huiEr = huiEr;
    }

    /**
     * 变化类型！！
     */
    public String getBianType() {
        return bianType;
    }

    /**
     * 变化类型！！
     */
    public void setBianType(String bianType) {
        this.bianType = bianType;
    }

    /**
     * 变化类型！！
     */
    public int getBianSource() {
        return bianSource;
    }

    /**
     * 变化类型！！
     */
    public void setBianSource(int bianSource) {
        this.bianSource = bianSource;
    }

    public java.util.ArrayList<UserPlaceMsg> getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(java.util.ArrayList<UserPlaceMsg> userPlace) {
        this.userPlace = userPlace;
    }

    /**
     * 当前操作用户
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * 当前操作用户
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * 局数, 0开始
     */
    public int getChapterNums() {
        return chapterNums;
    }

    /**
     * 局数, 0开始
     */
    public void setChapterNums(int chapterNums) {
        this.chapterNums = chapterNums;
    }

    /**
     * 局数, 0开始
     */
    public int getChapterNumsMax() {
        return chapterNumsMax;
    }

    /**
     * 局数, 0开始
     */
    public void setChapterNumsMax(int chapterNumsMax) {
        this.chapterNumsMax = chapterNumsMax;
    }

    /**
     * 圈index 0 东 1南 2西 3北 逆时针顺序
     */
    public int getQuanIndex() {
        return quanIndex;
    }

    /**
     * 圈index 0 东 1南 2西 3北 逆时针顺序
     */
    public void setQuanIndex(int quanIndex) {
        this.quanIndex = quanIndex;
    }

    /**
     * 庄index 0 东 1南 2西 3北 逆时针顺序
     */
    public int getZhuangIndex() {
        return zhuangIndex;
    }

    /**
     * 庄index 0 东 1南 2西 3北 逆时针顺序
     */
    public void setZhuangIndex(int zhuangIndex) {
        this.zhuangIndex = zhuangIndex;
    }

    public OperationCPGH getOptCpgh() {
        return optCpgh;
    }

    public void setOptCpgh(OperationCPGH optCpgh) {
        this.optCpgh = optCpgh;
    }

    public OperationFaPaiNew getOptFaPai() {
        return optFaPai;
    }

    public void setOptFaPai(OperationFaPaiNew optFaPai) {
        this.optFaPai = optFaPai;
    }

    public OperationOut getOptOut() {
        return optOut;
    }

    public void setOptOut(OperationOut optOut) {
        this.optOut = optOut;
    }

    public SyncOptTime getSyncOptTime() {
        return syncOptTime;
    }

    public void setSyncOptTime(SyncOptTime syncOptTime) {
        this.syncOptTime = syncOptTime;
    }

    public GameChapterEnd getGameChapterEnd() {
        return gameChapterEnd;
    }

    public void setGameChapterEnd(GameChapterEnd gameChapterEnd) {
        this.gameChapterEnd = gameChapterEnd;
    }

    public TingPai getTingPai() {
        return tingPai;
    }

    public void setTingPai(TingPai tingPai) {
        this.tingPai = tingPai;
    }


    public void addUserPlace(UserPlaceMsg userPlace) {
        if (this.userPlace == null) {
            this.userPlace = new java.util.ArrayList<UserPlaceMsg>();
        }
        this.userPlace.add(userPlace);
    }

    @Override
    public String toString() {
        return "MajiangChapterMsg [freeLength=" + freeLength + ",baoliuLength=" + baoliuLength + ",huiEr=" + huiEr +
                ",bianType=" + bianType + ",bianSource=" + bianSource + ",userPlace=" + userPlace + ",currentIndex="
                + currentIndex + ",chapterNums=" + chapterNums + ",chapterNumsMax=" + chapterNumsMax + ",quanIndex="
                + quanIndex + ",zhuangIndex=" + zhuangIndex + ",optCpgh=" + optCpgh + ",optFaPai=" + optFaPai + "," +
                "optOut=" + optOut + ",syncOptTime=" + syncOptTime + ",gameChapterEnd=" + gameChapterEnd + "," +
                "tingPai=" + tingPai + ", ]";
    }

    @Override
    public final int getMessageType() {
        return TYPE;
    }

    @Override
    public final int getMessageId() {
        return ID;
    }

    @Override
    public Object toPbObject() {
        Room.MajiangChapter.Builder builder = Room.MajiangChapter.newBuilder();
        builder.setFreeLength(getFreeLength()).setBaoliuLength(getBaoliuLength());
        int[] huiEr = getHuiEr();
        if (huiEr != null) {
            builder.addAllHuiEr(toList(huiEr));
        }
        String bianType = getBianType();
        builder.setBianType(bianType == null ? "" : bianType).setBianSource(getBianSource());
        List<Room.UserPlace> collect = getUserPlace().stream().map(p -> (Room.UserPlace) p.toPbObject()).collect
                (Collectors.toList());
        builder.addAllUserPlace(collect);
        builder.setCurrentIndex(getCurrentIndex()).setChapterNums(getChapterNums()).setChapterNumsMax
                (getChapterNumsMax())
                .setQuanIndex(getQuanIndex()).setZhuangIndex(getZhuangIndex());
        OperationCPGH optCpgh = getOptCpgh();
        if (optCpgh != null) {
            builder.setOptCPGH((Room.OperationCPGH) optCpgh.toPbObject());
        }
        OperationFaPaiNew optFaPai = getOptFaPai();
        if (optFaPai != null) {
//            Room.OperationCPGH.Builder optCpghBuilder = Room.OperationCPGH.newBuilder();
            builder.setOptFaPai((Room.OperationCPGH) optFaPai.toPbObject());
        }
        OperationOut optOut = getOptOut();
        if (optOut != null) {
            builder.setOptOut((Room.OperationOut) optOut.toPbObject());
        }
        SyncOptTime syncOptTime = getSyncOptTime();
        if (syncOptTime != null) {
            builder.setSyncOptTime((Room.SyncOptTime) syncOptTime.toPbObject());
        }
        GameChapterEnd gameChapterEnd = getGameChapterEnd();
        if (gameChapterEnd != null) {
            builder.setGameChapterEnd((Room.GameChapterEnd) gameChapterEnd.toPbObject());
        }
        TingPai tingPai = getTingPai();
        if (tingPai != null) {
            Object o = tingPai.toPbObject();
            if (o != null) {
                Room.TingPai tingPaiPb = (Room.TingPai) o;
                builder.setTingPai(tingPaiPb);
            }
        }
        return builder.build();
    }

    @Override
    public byte[] toBytes() {
        Room.MajiangChapter object = (Room.MajiangChapter) toPbObject();
        return object.toByteArray();

    }

    public void setWailIndex(int wailIndex) {
        this.wailIndex = wailIndex;
    }

    public int getWailIndex() {
        return wailIndex;
    }
}
