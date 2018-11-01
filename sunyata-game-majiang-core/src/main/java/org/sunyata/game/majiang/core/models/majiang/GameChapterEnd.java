package org.sunyata.game.majiang.core.models.majiang;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 牌局结束
 * <p>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class GameChapterEnd extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.gameChapterEnd);
    public static final int ID = 0;

    private int huPaiIndex;
    private int fangPaoIndex;
    private int zaMaType;
    private int[] zaMaPai;
    private int zaMaFan;
    private java.util.ArrayList<GameFanResult> fanResults;

    public GameChapterEnd() {

    }

    public GameChapterEnd(int huPaiIndex, int fangPaoIndex, int zaMaType, int[] zaMaPai, int zaMaFan, java.util
            .ArrayList<GameFanResult> fanResults) {
        this.huPaiIndex = huPaiIndex;
        this.fangPaoIndex = fangPaoIndex;
        this.zaMaType = zaMaType;
        this.zaMaPai = zaMaPai;
        this.zaMaFan = zaMaFan;
        this.fanResults = fanResults;
    }

//	@Override
//	public void decode(Input in)  throws IOException, ProtocolException {
//		huPaiIndex = in.readInt();
//		fangPaoIndex = in.readInt();
//		zaMaType = in.readInt();
//		zaMaPai = in.readIntArray();
//		zaMaFan = in.readInt();
//
//		int fanResultsLen = in.readInt();
//		if(fanResultsLen == -1){
//			fanResults = null;
//		}else{
//			fanResults = new java.util.ArrayList<GameFanResult>(fanResultsLen);
//			for(int i = 0; i < fanResultsLen; i++){
//				GameFanResult fanResultsItem = new GameFanResult();
//				fanResultsItem.decode(in);
//				fanResults.add(fanResultsItem);
//			}
//		}
//	}
//
//	@Override
//	public void encode(Output out)  throws IOException, ProtocolException {
//		out.writeInt(getHuPaiIndex());
//		out.writeInt(getFangPaoIndex());
//		out.writeInt(getZaMaType());
//		out.writeIntArray(getZaMaPai());
//		out.writeInt(getZaMaFan());
//
//		if(fanResults == null){
//			out.writeInt(-1);
//		}else{
//			ArrayList<GameFanResult> fanResultsList = getFanResults();
//			int fanResultsLen = fanResultsList.size();
//			out.writeInt(fanResultsLen);
//			for(GameFanResult fanResultsItem: fanResultsList){
//				fanResultsItem.encode(out);
//			}
//		}
//	}

    public int getHuPaiIndex() {
        return huPaiIndex;
    }

    public void setHuPaiIndex(int huPaiIndex) {
        this.huPaiIndex = huPaiIndex;
    }

    public int getFangPaoIndex() {
        return fangPaoIndex;
    }

    public void setFangPaoIndex(int fangPaoIndex) {
        this.fangPaoIndex = fangPaoIndex;
    }

    public int getZaMaType() {
        return zaMaType;
    }

    public void setZaMaType(int zaMaType) {
        this.zaMaType = zaMaType;
    }

    public int[] getZaMaPai() {
        return zaMaPai;
    }

    public void setZaMaPai(int[] zaMaPai) {
        this.zaMaPai = zaMaPai;
    }

    public int getZaMaFan() {
        return zaMaFan;
    }

    public void setZaMaFan(int zaMaFan) {
        this.zaMaFan = zaMaFan;
    }

    public java.util.ArrayList<GameFanResult> getFanResults() {
        return fanResults;
    }

    public void setFanResults(ArrayList<GameFanResult> fanResults) {
        this.fanResults = fanResults;
    }


    public void addFanResults(GameFanResult fanResults) {
        if (this.fanResults == null) {
            this.fanResults = new java.util.ArrayList<GameFanResult>();
        }
        this.fanResults.add(fanResults);
    }

    @Override
    public String toString() {
        return "GameChapterEnd [huPaiIndex=" + huPaiIndex + ",fangPaoIndex=" + fangPaoIndex + ",zaMaType=" + zaMaType
                + ",zaMaPai=" + zaMaPai + ",zaMaFan=" + zaMaFan + ",fanResults=" + fanResults + ", ]";
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
        Room.GameChapterEnd.Builder builder = Room.GameChapterEnd.newBuilder();
        builder.setHuPaiIndex(getHuPaiIndex());
        builder.setFangPaoIndex(getFangPaoIndex());
        builder.setZaMaType(getZaMaType());
        int[] zaMaPai = getZaMaPai();
        if (zaMaPai != null && zaMaPai.length > 0) {
            builder.addAllZaMaPai(toList(zaMaPai));
        }
        builder.setZaMaFan(getZaMaFan());
        List<Room.GameFanResult> collect = getFanResults().stream().map(p -> (Room.GameFanResult) p.toPbObject())
                .collect(Collectors.toList());
        builder.addAllFanResults(collect);
        return builder.build();
    }

    @Override
    public byte[] toBytes() {
        Room.GameChapterEnd object = (Room.GameChapterEnd) toPbObject();
        return object.toByteArray();
    }
}
