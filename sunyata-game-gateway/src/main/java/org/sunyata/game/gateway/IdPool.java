package org.sunyata.game.gateway;

import org.springframework.stereotype.Component;

import java.util.TreeSet;

/**
 * Created by leo on 17/10/30.
 */
@Component
public class IdPool {


    private final TreeSet<Short> idPool = new TreeSet<>();
    private int max;

    private final int maxConnect;
    private final int freeWaitNums;

    public IdPool() {
        maxConnect = (int) (1024 * 1.3 + 1);
        freeWaitNums = 1024 * 1 / 3;
    }

    public int generateId() throws Exception {
        int id;
        if (idPool.size() > freeWaitNums) {
            id = idPool.pollFirst();
        } else {
            if (max >= maxConnect) {
                throw new Exception("pool id too big:" + max + ", max:" + maxConnect);
            }
            id = max;
            max++;
        }
        return id;
    }

    public void add(int id) {

    }
}
