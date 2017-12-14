package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

/**
 * 同步操作
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class SyncOptTime extends AbstractMessage{
	public static final int TYPE			 = Integer.parseInt(Commands.syncOptTime);
	public static final int ID				 = 17;
	
	/**
	 * 位置
	 */
	private int index;
	/**
	 * 毫秒
	 */
	private int leftTime;
	
	public SyncOptTime(){
		
	}
	
	public SyncOptTime(int index, int leftTime){
		this.index = index;
		this.leftTime = leftTime;
	}
//
//	@Override
//	public void decode(Input in)  throws IOException, ProtocolException {
//		index = in.readInt();
//		leftTime = in.readInt();
//	}
//
//	@Override
//	public void encode(Output out)  throws IOException, ProtocolException {
//		out.writeInt(getIndex());
//		out.writeInt(getLeftTime());
//	}

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

	/**
	 * 毫秒
	 */
	public int getLeftTime() {
		return leftTime;
	}
	
	/**
	 * 毫秒
	 */
	public void setLeftTime(int leftTime) {
		this.leftTime = leftTime;
	}

	
	@Override
	public String toString() {
		return "SyncOptTime [index=" + index + ",leftTime=" + leftTime + ", ]";
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
		Room.SyncOptTime.Builder builder = Room.SyncOptTime.newBuilder();
		builder.setIndex(getIndex());
		builder.setLeftTime(getLeftTime());
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		Room.SyncOptTime object = (Room.SyncOptTime) toPbObject();
		return object.toByteArray();
	}
}
