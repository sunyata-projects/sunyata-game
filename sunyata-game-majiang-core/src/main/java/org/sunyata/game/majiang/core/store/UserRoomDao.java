package org.sunyata.game.majiang.core.store;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.mapper.RoomMapper;

import java.sql.Timestamp;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class UserRoomDao {
    protected final Logger LOGGER = LoggerFactory.getLogger(UserRoomDao.class);

    @Autowired
    RoomMapper roomMapper;

    public void createRoom(String orderId) throws Exception {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {
//            FundMapper mapper = session.getMapper(FundMapper.class);
            RoomMapper mapper = roomMapper;

            //session.commit();
        } catch (Exception ex) {
            LOGGER.error(ExceptionUtils.getStackTrace(ex));
            throw ex;
        }
    }
}
