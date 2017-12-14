package org.sunyata.game.store.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.login.models.UserEntity;
import org.sunyata.game.login.models.UserLoginLog;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class UserLoginLogDao {
    @Autowired
    UserLoginLogMapper userMapper;

    public UserEntity getUserEntity(String openId) {
        return userMapper.findByOpenId(openId);
    }

    public void insert(UserLoginLog userEntity) {
        userMapper.insertUserLoginLog(userEntity);
    }

    public void update(UserEntity userEntity) {
        userMapper.update(userEntity);
    }
}
