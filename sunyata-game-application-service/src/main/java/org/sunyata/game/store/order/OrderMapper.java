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

package org.sunyata.game.store.order;

import org.apache.ibatis.annotations.*;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.order.models.Order;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface OrderMapper extends MapperInterface {
    @Select("select * from TradingOrder where id=#{id}")
    Order getOrder(@Param("id") String id);

//    @Insert("insert into t_result(m_id,q_id)  \n" +
//            "        values  \n" +
//            "        <foreach collection=\"list\" item=\"item\" index=\"index\"  \n" +
//            "            separator=\",\">  \n" +
//            "            (#{m_id,jdbcType=INTEGER},#{item.id,jdbcType=INTEGER})  \n" +
//            "        </foreach>  ")
//
//    int insertAll(@Param("list") ArrayList<Question> list, @Param("m_id") Integer m_id);

    @Insert("INSERT INTO TradingOrder(id,businessType,businessId,parameterString,orderStatusType,orderStatusTypeDesc," +
            "notes,createDateTime,totalPrice,userId) " +
            "VALUES(#{id},#{businessType},#{businessId},#{parameterString},#{orderStatusType},#{orderStatusTypeDesc}," +
            "#{notes},#{createDateTime},#{totalPrice},#{userId})")
    Integer insertOrder(Order order);

    @Select("select id from TradingOrder where id=#{id}")
    String getOrderId(@Param("id") String id);

    @Update("update TradingOrder set orderStatusType=#{orderStatusType}," +
            "orderStatusTypeDesc=#{orderStatusTypeDesc},notes=#{notes} where id = #{orderId}")
    void updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatusType") Integer orderStatusType,
                           @Param("orderStatusTypeDesc") String orderStatusTypeDesc,
                           @Param("notes") String notes);

//
//    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
//    int insert(@Param("name") String name, @Param("age") Integer age);
//
//    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
//    int insertByMap(Map<String, Object> map);
//
//    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
//    int insertByUser(User user);
//
//    @Update("UPDATE user SET age=#{age} WHERE name=#{name}")
//    void writeLog(User user);
//    @Delete("DELETE FROM user WHERE id =#{id}")
//    void delete(Long id);
//
//    @Results({
//            @Result(property = "name", column = "name"),
//            @Result(property = "age", column = "age")
//    })
//    @Select("SELECT name, age FROM user")
//    List<User> findAll();
//

}
