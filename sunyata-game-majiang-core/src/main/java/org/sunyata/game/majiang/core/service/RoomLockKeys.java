package org.sunyata.game.majiang.core.service;

import org.springframework.stereotype.Component;

/**
 * Created by leo on 17/11/20.
 */
@Component
public class RoomLockKeys {

    public String getJoinGameLockKey(String roomCheckId) {
        return "join_game_" + roomCheckId;
    }


    public String getCreateRoomLockKey(String account) {
        return "create_room_" + account;
    }

    public String getFundLockKey(String account) {
        //return new ReentrantZkLock2(baseLockPath + "/" + "fund_" + account, getZkSessionManager());
        return "fund_" + account;
    }

    public String getCreateRoomCheckIdLock() {
        return "fund_" + "create_room_check_id";
    }
}
