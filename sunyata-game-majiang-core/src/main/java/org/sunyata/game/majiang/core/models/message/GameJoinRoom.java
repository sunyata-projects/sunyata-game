package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractMessage;

/**
 * 准备就绪,通知服务器可以开始发送房间信息了
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class GameJoinRoom extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 6;
	
	
	public GameJoinRoom(){
		
	}
//
//	@Override
//	public void decode(Input in)  throws IOException, ProtocolException {
//	}
//
//	@Override
//	public void encode(Output out)  throws IOException, ProtocolException {
//	}

	
	@Override
	public String toString() {
		return "GameJoinRoom [ ]";
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
