package org.sunyata.game.majiang.core.service;

import org.sunyata.game.service.AbstractScheduledAsyncService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * room 线程
 *
 * @author leo on 14-1-8.
 */
@Component
public final class RoomAsyncService extends AbstractScheduledAsyncService {
    public RoomAsyncService() {
        super(1, 4);
    }

    public void run(int id, Runnable task) {
        submit(id, task);
    }

    protected ScheduledFuture<?> runFrame(int id, Runnable task, int delayMilliseconds) {
        return submit(id, task, delayMilliseconds);
    }
}
