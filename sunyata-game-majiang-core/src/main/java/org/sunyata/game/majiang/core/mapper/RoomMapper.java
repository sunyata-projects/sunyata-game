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
import org.sunyata.game.majiang.core.models.room.UserRoom;

import java.util.List;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface RoomMapper extends MapperInterface {

    @Select("select * from FundBalance where account=#{account} and fundAccountType=#{fundAccountType}")
    RoomEntity getRoom(@Param("account") String account, @Param("fundAccountType") int fundAccountType);

    @Insert("INSERT INTO OctopusRoom(orderId,roomCheckId,createUserId,uuid,sceneId,userMax,chapterNums,startDate," +
            "endDate,createDate,status,config) " +
            "VALUES(#{orderId},#{roomCheckId},#{createUserId},#{uuid},#{sceneId},#{userMax},#{chapterNums}," +
            "#{startDate},#{endDate},#{createDate},#{status},#{config})")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    Integer insertRoom(RoomEntity room);

    @Update("UPDATE FundBalance SET availableBalance=#{availableBalance},actualBalance=#{actualBalance}," +
            "updateDateTime=#{updateDateTime},funChangeId=#{funChangeId} WHERE id=#{id}")
    void updateRoom(RoomEntity balance);

    @Select("select * from OctopusRoom where roomCheckId=#{roomCheckId} and status <> 2")
    RoomEntity getNotEndRoom(String roomCheckId);

    @Select("select * from OctopusUserRoom where roomId=#{roomId}")
    List<UserRoom> getUserRooms(Integer roomId);

    @Insert("INSERT INTO OctopusUserRoom(roomId,userId,userName,score,position,isDisable) " +
            "VALUES(#{roomId},#{userId},#{userName},#{score},#{position},#{isDisable})")
    void insertUserRoom(UserRoom allocateUserRoom);

    @Select("select * from OctopusUserRoom where userId=#{userId} and isDisable = #{isDisable}")
    UserRoom getUserRoom(@Param("userId") int userId, @Param("isDisable") boolean isDisable);

    @Select("select * from OctopusRoom where id=#{Id}")
    RoomEntity getRoomById(Integer Id);

    @Select("select * from OctopusUserRoom where userId=#{userId} and isDisable = 0")
    UserRoom getUnEndUserRoom(int userId);

    @Select("select * from OctopusRoom where createUserId=#{userId} and createRoomType = #{createRoomType}")
    RoomEntity getRoomByCreateUserId(@Param("userId") Integer userId, @Param("createRoomType") int createRoomType);
}
