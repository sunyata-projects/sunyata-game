package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractMessage;

public class VoteDelStart extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 23;
	
	
	public VoteDelStart(){
		
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
		return "VoteDelStart [ ]";
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
