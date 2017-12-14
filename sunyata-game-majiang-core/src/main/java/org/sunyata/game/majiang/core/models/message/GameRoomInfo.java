package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;

/**
 * 同步游戏信息
 * 0东 1南 2西 3北 逆时针顺序
 * <p>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class GameRoomInfo extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.gameRoomInfo);
    public static final int ID = 7;

    private java.util.ArrayList<GameUserInfo> sceneUser;
    private boolean start;
    private String roomCheckId;
    private int leftChapterNums;
    private int createUserId;
    private MajiangChapterMsg chapter;
    private int sceneServerId;

    public GameRoomInfo() {

    }

    public GameRoomInfo(java.util.ArrayList<GameUserInfo> sceneUser, boolean start, String roomCheckId, int
            leftChapterNums, int createUserId, MajiangChapterMsg chapter) {
        this.sceneUser = sceneUser;
        this.start = start;
        this.roomCheckId = roomCheckId;
        this.leftChapterNums = leftChapterNums;
        this.createUserId = createUserId;
        this.chapter = chapter;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//
//        int sceneUserLen = in.readInt();
//        if (sceneUserLen == -1) {
//            sceneUser = null;
//        } else {
//            sceneUser = new java.util.ArrayList<GameUserInfo>(sceneUserLen);
//            for (int i = 0; i < sceneUserLen; i++) {
//                GameUserInfo sceneUserItem = new GameUserInfo();
//                sceneUserItem.decode(in);
//                sceneUser.add(sceneUserItem);
//            }
//        }
//        start = in.readBoolean();
//        roomCheckId = in.readString();
//        leftChapterNums = in.readInt();
//        createUserId = in.readInt();
//
//        boolean chapterIsNotNull = in.readBoolean();
//        if (chapterIsNotNull) {
//            chapter = new MajiangChapterMsg();
//            chapter.decode(in);
//        } else {
//            chapter = null;
//        }
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//
//        if (sceneUser == null) {
//            out.writeInt(-1);
//        } else {
//            ArrayList<GameUserInfo> sceneUserList = getSceneUser();
//            int sceneUserLen = sceneUserList.size();
//            out.writeInt(sceneUserLen);
//            for (GameUserInfo sceneUserItem : sceneUserList) {
//                sceneUserItem.encode(out);
//            }
//        }
//        out.writeBoolean(getStart());
//        out.writeString(getRoomCheckId());
//        out.writeInt(getLeftChapterNums());
//        out.writeInt(getCreateUserId());
//        MajiangChapterMsg chapterItem = getChapter();
//        if (chapterItem == null) {
//            out.writeBoolean(false);
//        } else {
//            out.writeBoolean(true);
//            chapterItem.encode(out);
//        }
//    }

    public java.util.ArrayList<GameUserInfo> getSceneUser() {
        return sceneUser;
    }

    public void setSceneUser(java.util.ArrayList<GameUserInfo> sceneUser) {
        this.sceneUser = sceneUser;
    }

    public boolean getStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public void setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
    }

    public int getLeftChapterNums() {
        return leftChapterNums;
    }

    public void setLeftChapterNums(int leftChapterNums) {
        this.leftChapterNums = leftChapterNums;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public MajiangChapterMsg getChapter() {
        return chapter;
    }

    public void setChapter(MajiangChapterMsg chapter) {
        this.chapter = chapter;
    }

    public void addSceneUser(GameUserInfo sceneUser) {
        if (this.sceneUser == null) {
            this.sceneUser = new java.util.ArrayList<GameUserInfo>();
        }
        this.sceneUser.add(sceneUser);
    }

    public int getSceneServerId() {
        return sceneServerId;
    }

    public GameRoomInfo setSceneServerId(int sceneServerId) {
        this.sceneServerId = sceneServerId;
        return this;
    }


    @Override
    public String toString() {
        return "GameRoomInfo [sceneUser=" + sceneUser + ",start=" + start + ",roomCheckId=" + roomCheckId + "," +
                "leftChapterNums=" + leftChapterNums + ",createUserId=" + createUserId + ",chapter=" + chapter + ", ]";
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
        Room.GameRoomInfo object = (Room.GameRoomInfo) toPbObject();
        return object.toByteArray();
    }

    @Override
    public Object toPbObject() {
        Room.GameRoomInfo.Builder builder = Room.GameRoomInfo.newBuilder();
        for (GameUserInfo sceneUser : this.getSceneUser()) {
            Room.GameUserInfo pbUserInfo = (Room.GameUserInfo) sceneUser.toPbObject();
            builder.addSceneUser(pbUserInfo);
        }
        builder.setStart(getStart());
        String roomCheckId = getRoomCheckId();
        builder.setRoomCheckId(roomCheckId == null ? "" : roomCheckId);
        builder.setLeftChapterNums(getLeftChapterNums());
        builder.setCreateUserId(getCreateUserId());
        MajiangChapterMsg chapter = getChapter();
        if (chapter != null) {
            builder.setChapter((Room.MajiangChapter) chapter.toPbObject());
        }
        builder.setSceneServerId(getSceneServerId());
        return builder.build();
    }
}
