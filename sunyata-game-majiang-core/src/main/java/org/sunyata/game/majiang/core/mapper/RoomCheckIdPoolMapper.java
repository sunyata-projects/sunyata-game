/*
 *
 *
 *  * Copyright (c) 2017 Leo Lee(lichl.1980@163.com).
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  * use this file except in compliance with the License. You may obtain a copy
 *  * of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *  *
 *
 */

package org.sunyata.game.majiang.core.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.majiang.core.models.room.RoomCheckIdPool;

import java.util.List;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface RoomCheckIdPoolMapper extends MapperInterface {

    @Select("select * from room_check_id_pool where state=0 limit 100")
    List<RoomCheckIdPool> findNoUseIds(int topN);

    @Update("UPDATE room_check_id_pool SET state=2 WHERE id=#{id} and state=0")
    Integer updateToBuffer(String id);

    @Insert("INSERT INTO room_check_id_pool(id,state) " +
            "VALUES(#{id},#{state})")
    void insertRoomCheckId(RoomCheckIdPool item);
}
