package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.server.message.AbstractMessage;

public class ChapterStartMsg extends AbstractMessage{
    public static final int TYPE = Integer.parseInt(Commands.gameChapterStart);
    public static final int ID = 0;

    private int roomId;



    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ChapterEndMsg{" +
                "roomId=" + roomId +
                '}';
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
