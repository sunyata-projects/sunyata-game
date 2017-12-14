package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

/**
 * 通知用户出牌
 * <p>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class TingPai extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.tingPai);
    public static final int ID = 18;

    private int[] pais;

    public TingPai() {

    }

    public TingPai(int[] pais) {
        this.pais = pais;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        pais = in.readIntArray();
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeIntArray(getPais());
//    }

    public int[] getPais() {
        return pais;
    }

    public void setPais(int[] pais) {
        this.pais = pais;
    }


    @Override
    public String toString() {
        return "TingPai [pais=" + pais + ", ]";
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
        if (getPais().length > 0) {
            Room.TingPai.Builder builder = Room.TingPai.newBuilder();
            builder.addAllPais(toList(getPais()));
            return builder.build();
        } else {
            return null;
        }
    }

    @Override
    public byte[] toBytes() {
        Object o = toPbObject();
        if (o != null) {
            Room.TingPai tingPai = (Room.TingPai) toPbObject();
            return tingPai.toByteArray();
        } else {
            return null;
        }

    }
}
