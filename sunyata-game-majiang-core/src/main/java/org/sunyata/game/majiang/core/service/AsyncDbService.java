package org.sunyata.game.majiang.core.service;

import org.sunyata.game.service.AbstractAsyncService;
import org.springframework.stereotype.Component;

/**
 * 数据库异步执行
 *
 * @author leo on 14-1-8.
 */
@Component
public final class AsyncDbService extends AbstractAsyncService {
    //    private static final Logger log = LoggerFactory.getLogger(AsyncDbService.class);
    public AsyncDbService() {
        super(2, 2);
    }


    //    public void execute(User user, Runnable task) {
//        submit(user.getUserId(), task);
//    }
    public void executeRoom(int roomId, Runnable task) {
        submit(roomId, task);
    }

    public void execute(Runnable task) {
        submit(task);
    }
}
