package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

/**
 * 通知用户出牌
 * <p/>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class OperationOut extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.optOut);
    public static final int ID = 14;

    private int index;

    public OperationOut() {

    }

    public OperationOut(int index) {
        this.index = index;
    }

//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        index = in.readInt();
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeInt(getIndex());
//    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public String toString() {
        return "OperationOut [index=" + index + ", ]";
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
        Room.OperationOut.Builder builder = Room.OperationOut.newBuilder();
        builder.setIndex(getIndex());
        return builder.build();
    }

    @Override
    public byte[] toBytes() {
        Room.OperationOut out = (Room.OperationOut) this.toPbObject();
        return out.toByteArray();
    }
}
