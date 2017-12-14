package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractMessage;

/**
 * 用户离线
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class UserOffline extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 19;
	
	private int index;
	
	public UserOffline(){
		
	}
	
	public UserOffline(int index){
		this.index = index;
	}
//
//	@Override
//	public void decode(Input in)  throws IOException, ProtocolException {
//		index = in.readInt();
//	}
//
//	@Override
//	public void encode(Output out)  throws IOException, ProtocolException {
//		out.writeInt(getIndex());
//	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	
	@Override
	public String toString() {
		return "UserOffline [index=" + index + ", ]";
	}
	
	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
