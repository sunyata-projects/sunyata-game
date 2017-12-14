package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

public class GameChapterStartRet extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 2;
	
	
	public GameChapterStartRet(){
		
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
		return "GameChapterStartRet [ ]";
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
	public byte[] toBytes() {
		return Room.GameChapterStartRet.newBuilder().build().toByteArray();
	}
}
