package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.majiang.OperationNames;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

/**
 * 自己的发牌的信息
 * <p>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class OperationFaPai extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.optFaPai);
    public static final int ID = 12;

    /**
     * 位置
     */
    private int index;
    private int pai;
    private int[] anGang;
    private int[] mingGang;
    private boolean hu;

    public OperationFaPai() {

    }

    public OperationFaPai(int index, int pai, int[] anGang, int[] mingGang, boolean hu) {
        this.index = index;
        this.pai = pai;
        this.anGang = anGang;
        this.mingGang = mingGang;
        this.hu = hu;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        index = in.readInt();
//        pai = in.readInt();
//        anGang = in.readIntArray();
//        mingGang = in.readIntArray();
//        hu = in.readBoolean();
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeInt(getIndex());
//        out.writeInt(getPai());
//        out.writeIntArray(getAnGang());
//        out.writeIntArray(getMingGang());
//        out.writeBoolean(getHu());
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

    public int getPai() {
        return pai;
    }

    public void setPai(int pai) {
        this.pai = pai;
    }

    public int[] getAnGang() {
        return anGang;
    }

    public void setAnGang(int[] anGang) {
        this.anGang = anGang;
    }

    public int[] getMingGang() {
        return mingGang;
    }

    public void setMingGang(int[] mingGang) {
        this.mingGang = mingGang;
    }

    public boolean getHu() {
        return hu;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }


    @Override
    public String toString() {
        return "OperationFaPai [index=" + index + ",pai=" + pai + ",anGang=" + anGang + ",mingGang=" + mingGang + "," +
                "hu=" + hu + ", ]";
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
        Room.OperationCPGH.Builder builder = Room.OperationCPGH.newBuilder();
        builder.setIndex(getIndex());
        builder.setPai(getPai());

        Room.OperationInfo.Builder optAnGang = Room.OperationInfo.newBuilder();
        optAnGang.setOpt(OperationNames.OPT_AN_GANG).addAllPai(toList(getAnGang()));
        builder.addOperationInfo(optAnGang);


        Room.OperationInfo.Builder optMingGang = Room.OperationInfo.newBuilder();
        optMingGang.setOpt(OperationNames.OPT_XIAO_MING_GANG).addAllPai(toList(getMingGang()));
        builder.addOperationInfo(optMingGang);


        if (getHu()) {
            Room.OperationInfo.Builder optHu = Room.OperationInfo.newBuilder();
            optHu.setOpt(OperationNames.OPT_HU).addPai(getPai());
            builder.addOperationInfo(optHu);
        }
        //builder.addAllMingGang(toList(getMingGang()));
        //builder.setHu(getHu());
        return builder.build();
    }

    @Override
    public byte[] toBytes() {
        Room.OperationFaPai object = (Room.OperationFaPai) toPbObject();
        return object.toByteArray();
    }
}
