package org.sunyata.game.majiang.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunyata.game.majiang.core.MessageHandler;
import org.sunyata.game.majiang.core.models.room.RoomConfigInfo;
import org.sunyata.game.majiang.core.models.room.RoomInfo;
import org.sunyata.game.majiang.core.models.SceneUser;
import org.sunyata.game.majiang.core.models.majiang.ChapterEndResult;
import org.sunyata.game.majiang.core.models.majiang.MajiangChapter;
import org.sunyata.game.majiang.core.models.message.VoteDelSelectRet;
import org.sunyata.game.majiang.core.models.message.VoteDelStart;
import org.sunyata.game.server.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 服务器帧率25帧率
 *
 * @author leo on 16/10/7.
 */
public abstract class Room {
    protected static final Logger log = LoggerFactory.getLogger(Room.class);
    public static final int FRAME_DELAY_MILLISECONDS = 50;

    protected RoomInfo roomInfo;
    private ScheduledFuture<?> scheduledFuture;

    private RoomAsyncService roomAsyncService;

    protected RoomConfigInfo config;

    private boolean end = false;

    //投票记录
    protected Map<Integer, Integer> voteDelInfo = new HashMap<>();

    public Room(RoomAsyncService roomAsyncService) {
        this.roomAsyncService = roomAsyncService;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    @SuppressWarnings("unchecked")
    public void handler(MessageHandler handler, Message message, SceneUser sceneUser) {
        run(() -> {
            SceneUser prevSceneUser = roomInfo.getUserInfo(sceneUser.getLocationIndex());
            if (prevSceneUser != sceneUser && prevSceneUser.getUserId() != sceneUser.getUserId()) {
                throw new RuntimeException(String.format("检查房间内玩家信息,发现冲突 %s", sceneUser));
            }
            try {
                handler.handler(message, sceneUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void start() {
        scheduledFuture = roomAsyncService.runFrame(roomInfo.getRoomId(), this::frame, FRAME_DELAY_MILLISECONDS);
    }

    public void close() {
        if (!scheduledFuture.cancel(false)) {
            throw new RuntimeException("frame 回调停止失败");
        }
    }

    protected abstract void frame();

    protected void run(Runnable runnable) {
        this.roomAsyncService.run(roomInfo.getRoomId(), runnable);
    }

    protected void checkThread() {
        this.roomAsyncService.checkThread(roomInfo.getRoomId());
    }


    public void sendMessage(Message msg) {
        checkThread();
        sendMessage(msg, null);
    }

    /**
     * 发送给自己和别人不一样的消息
     */
    public void sendMessage(int locationIndex, Message myMsg, Message otherMsg) {
        checkThread();
        for (SceneUser u : roomInfo.getUsers()) {
            if (u != null && u.isJoinGame()) {
                if (u.getLocationIndex() == locationIndex) {
                    u.sendMessage(myMsg);
                } else {
                    u.sendMessage(otherMsg);
                }
            }
        }
    }

    /**
     * 不发送消息给自己
     */
    public void sendMessage(Message msg, SceneUser my) {
        checkThread();
        for (SceneUser u : roomInfo.getUsers()) {
            if (u != null && u.isJoinGame() && u != my) {
                u.sendMessage(msg);
            }
        }
    }

    public void sendMessage(int locationIndex, Message msg) {
        checkThread();
        SceneUser sceneUser = roomInfo.getUserInfo(locationIndex);
        if (sceneUser != null && sceneUser.isJoinGame()) {
            sceneUser.sendMessage(msg);
        } else {
            log.error("用户不存在:locationIndex:{},commandId:{}", locationIndex, msg.getMessageType());
        }
    }

    public abstract void endChapter(ChapterEndResult endResult, MajiangChapter majiangChapter);

    public boolean isStart() {
        return roomInfo.isStart();
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }


    public RoomConfigInfo getConfig() {
        return config;
    }

    public abstract void voteDelStart(VoteDelStart msg, SceneUser user);

    public abstract void voteDelSelect(VoteDelSelectRet msg, SceneUser user);
}
