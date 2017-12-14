//package org.sunyata.game.order.store;
//
//import org.apache.ibatis.annotations.*;
//import org.sunyata.game.org.sunyata.game.login.models.UserEntity;
//
///**
// * Created by leo on 17/11/15.
// */
//@Mapper
//public interface UserMapper {
//    //    int insertByUser(User user);
//    @Select("SELECT * FROM user WHERE open_id = #{open_id}")
//    UserEntity findByOpenId(@Param("open_id") String openId);
//
//    @Insert("INSERT INTO user(name,open_id,uuid,avator,sex,create_date,update_date,gold,history_gold,level,mobile," +
//            "login_token,ip,longitude,latitude,password) " +
//            "VALUES(#{name},#{open_id},#{uuid},#{avator},#{sex},#{create_date},#{update_date},#{gold}," +
//            "#{history_gold},#{level},#{mobile},#{login_token},#{ip},#{longitude},#{latitude},#{password})")
//    void insertUser(UserEntity userEntity);
//    @Update("UPDATE user SET name=#{name} WHERE open_id=#{open_id}")
//    void update(UserEntity userEntity);
//}
