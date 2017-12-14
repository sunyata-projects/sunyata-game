package org.sunyata.game.store.user;

import org.apache.ibatis.annotations.*;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.login.models.UserEntity;
import org.sunyata.game.login.models.UserLoginLog;

/**
 * Created by leo on 17/11/15.
 */
@Mapper
public interface UserLoginLogMapper extends MapperInterface {
    //    int insertByUser(User user);
    @Select("SELECT * FROM user WHERE open_id = #{openId}")
    UserEntity findByOpenId(@Param("openId") String openId);

    @Insert("INSERT INTO user_login_log(user_id,login_date,longitude,latitude,ip,login_token) " +
            "VALUES(#{user_id},#{login_date},#{longitude},#{latitude},#{ip},#{login_token})")
    void insertUserLoginLog(UserLoginLog userEntity);
    @Update("UPDATE user SET name=#{name} WHERE open_id=#{openId}")
    void update(UserEntity userEntity);
}
