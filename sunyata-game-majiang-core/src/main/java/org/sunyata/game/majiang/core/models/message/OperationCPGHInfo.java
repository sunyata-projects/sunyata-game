package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractMessage;

/**
 * 通知客户端"吃碰杠胡"
 * <p/>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class OperationCPGHInfo extends AbstractMessage {
    public static final int TYPE = 1;
    public static final int ID = 10;

    /**
     * 位置
     */
    private int index;
    /**
     * 3个一组一组
     */
    private String opt;
    private int[] pai;
    private int[] suggest;


    public String getOpt() {
        return opt;
    }

    public OperationCPGHInfo setOpt(String opt) {
        this.opt = opt;
        return this;
    }

    public int[] getPai() {
        return pai;
    }

    public OperationCPGHInfo setPai(int[] pai) {
        this.pai = pai;
        return this;
    }

    public int[] getSuggest() {
        return suggest;
    }

    public OperationCPGHInfo setSuggest(int[] suggest) {
        this.suggest = suggest;
        return this;
    }

    public OperationCPGHInfo() {

    }

    public OperationCPGHInfo(int index, int[] pai) {
        this.index = index;
        this.pai = pai;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        index = in.readInt();
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeInt(getIndex());
//    }

    /**
     * 位置
     */
    public int getIndex() {
        return index;
    }

    /**
     * 位置
     */
    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public String toString() {
        return "OperationCPGH [index=" + index + "]";
    }

    @Override
    public final int getMessageType() {
        return TYPE;
    }

    @Override
    public final int getMessageId() {
        return ID;
    }

//	@Override
//	public Object toPbObject() {
//		Room.OperationCPGH.Builder builder = Room.OperationCPGH.newBuilder();
//
//		builder.setIndex(getIndex()).addAllChi(toList(getChi()));
//		builder.setIsPeng(getIsPeng()).setIsGang(getIsGang()).setIsHu(getIsHu());
//		builder.setPai(getPai());
//		return builder.build();
//	}
}
