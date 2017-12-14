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

import org.apache.ibatis.annotations.*;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.majiang.core.models.room.RoomEntity;


/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface UserRoomMapper extends MapperInterface {

    @Select("select * from FundBalance where account=#{account} and fundAccountType=#{fundAccountType}")
    RoomEntity getRoom(@Param("account") String account, @Param("fundAccountType") int fundAccountType);

    @Insert("INSERT INTO FundBalance(account,fundAccountType,availableBalance,actualBalance,updateDateTime," +
            "funChangeId,accountName) " +
            "VALUES(#{account},#{fundAccountType},#{availableBalance},#{actualBalance},#{updateDateTime}," +
            "#{funChangeId},#{accountName})")
    @Options(keyProperty = "id", keyColumn = "id",useGeneratedKeys = true)
    Integer insertRoom(RoomEntity room);

    @Update("UPDATE FundBalance SET availableBalance=#{availableBalance},actualBalance=#{actualBalance}," +
            "updateDateTime=#{updateDateTime},funChangeId=#{funChangeId} WHERE id=#{id}")
    void updateRoom(RoomEntity balance);
}
