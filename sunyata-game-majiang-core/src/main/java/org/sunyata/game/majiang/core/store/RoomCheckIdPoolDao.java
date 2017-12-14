package org.sunyata.game.majiang.core.store;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.mapper.RoomCheckIdPoolMapper;
import org.sunyata.game.majiang.core.models.room.RoomCheckIdPool;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class RoomCheckIdPoolDao {
    protected final Logger LOGGER = LoggerFactory.getLogger(RoomCheckIdPoolDao.class);
    @Autowired
    RoomCheckIdPoolMapper roomCheckIdPoolMapper;

    public void createRoom(String orderId) throws Exception {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {
//            FundMapper mapper = session.getMapper(FundMapper.class);

            //session.commit();
        } catch (Exception ex) {
            LOGGER.error(ExceptionUtils.getStackTrace(ex));
            throw ex;
        }
    }

    public List<RoomCheckIdPool> findNoUseIds(int topN) {
        return roomCheckIdPoolMapper.findNoUseIds(topN);
    }

    public int updateToBuffer(String id) {
        return roomCheckIdPoolMapper.updateToBuffer(id);
    }

    public void insert(RoomCheckIdPool item) {
        roomCheckIdPoolMapper.insertRoomCheckId(item);
    }
}
