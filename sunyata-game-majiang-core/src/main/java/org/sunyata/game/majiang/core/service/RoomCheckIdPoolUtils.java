package org.sunyata.game.majiang.core.service;

import org.sunyata.game.lock.LockService;
import org.sunyata.game.lock.MutiLock;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.models.room.RoomCheckIdPool;
import org.sunyata.game.majiang.core.models.room.RoomCheckStatus;
import org.sunyata.game.majiang.core.store.RoomCheckIdPoolDao;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17/11/17.
 */
@Component
public class RoomCheckIdPoolUtils {
    Logger logger = LoggerFactory.getLogger(RoomCheckIdPoolUtils.class);
    private LinkedBlockingQueue<String> idBuffer = new LinkedBlockingQueue<>();
    int INIT_ID_NUMS = 200;
    int INIT_ID_BUFFER = 50;
    int LOOP_NUMS = 5;

    int CHECK_ROOM_ID_LEN = 6;

    @Autowired
    RoomCheckIdPoolDao roomCheckIdPoolDao;

    @Autowired
    LockService zkLockService;

    @Autowired
    RoomLockKeys lockKeys;

    /**
     * 生成一个随机id 不重复!
     */
    public String getBufferId() throws Exception {
        MutiLock createRoomCheckIdLock = zkLockService.getlock(lockKeys.getCreateRoomCheckIdLock());
        createRoomCheckIdLock.acquire(5, TimeUnit.SECONDS);
        try {
            String id = idBuffer.poll();
            if (id != null) {
                return id;
                //return "995555";
            }
            //return "995555";
            initCheckId();
            try {
                return idBuffer.take();
            } catch (InterruptedException e) {
                throw new Exception(e);
            }
        } finally {
            createRoomCheckIdLock.release();
        }
    }

    public void freeId(String id) {
        idBuffer.offer(id);
    }

    private void initCheckId() throws Exception {
        if (idBuffer.size() > INIT_ID_BUFFER) {
            return;
        }
        for (int l = 0; l < LOOP_NUMS; l++) {
            if (!idBuffer.isEmpty()) {
                return;
            }
            List<RoomCheckIdPool> noUseIds = roomCheckIdPoolDao.findNoUseIds(100);
            for (int i = 0; i < noUseIds.size(); i++) {
                RoomCheckIdPool idPoolDO = noUseIds.get(i);
                int isUpdate = roomCheckIdPoolDao.updateToBuffer(idPoolDO.getId());//把当前id状态变成buffer,返回更新数量
                if (isUpdate > 0) {
                    idBuffer.offer(idPoolDO.getId());
                }
            }
            if (idBuffer.isEmpty()) {
                RoomCheckIdPool item = new RoomCheckIdPool();
                for (int i = 0; i < INIT_ID_NUMS; ) {
                    item.setId(RandomStringUtils.randomNumeric(CHECK_ROOM_ID_LEN));
                    item.setState(RoomCheckStatus.Buffer.getValue());
                    try {
                        roomCheckIdPoolDao.insert(item);
                        i++;
                        idBuffer.offer(item.getId());
                    } catch (DuplicateKeyException ignored) {
                        logger.info("插入checkId重复,忽略掉");
                    }
                }
            }
        }
        throw new Exception("id 初始化严重问题!!!!超过重复次数上线,需要人工介入");
    }
}
