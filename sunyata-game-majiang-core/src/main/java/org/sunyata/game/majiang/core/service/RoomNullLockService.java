package org.sunyata.game.majiang.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.game.lock.LockService;
import org.sunyata.game.lock.MutiLock;

/**
 * Created by leo on 16/8/10.
 */
@Component
public class RoomNullLockService implements LockService {
    final Logger LOGGER = LoggerFactory.getLogger(RoomNullLockService.class);

    @Override
    public MutiLock getlock(String lockId) {
        return new MockLock();
    }
}
