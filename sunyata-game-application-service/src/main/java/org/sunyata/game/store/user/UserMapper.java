package org.sunyata.game.store.user;

import org.apache.ibatis.annotations.*;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.login.models.UserEntity;

/**
 * Created by leo on 17/11/15.
 */
@Mapper
public interface UserMapper extends MapperInterface {
    //    int insertByUser(User user);
    @Select("SELECT * FROM user WHERE open_id = #{open_id}")
    UserEntity findByOpenId(@Param("open_id") String openId);

    @Insert("INSERT INTO user(name,open_id,uuid,avatar,sex,create_date,update_date,gold,history_gold,level,mobile," +
            "login_token,ip,longitude,latitude,password) " +
            "VALUES(#{name},#{open_id},#{uuid},#{avatar},#{sex},#{create_date},#{update_date},#{gold}," +
            "#{history_gold},#{level},#{mobile},#{login_token},#{ip},#{longitude},#{latitude},#{password})")
    void insertUser(UserEntity userEntity);
    @Update("UPDATE user SET name=#{name} WHERE open_id=#{open_id}")
    void update(UserEntity userEntity);
}
