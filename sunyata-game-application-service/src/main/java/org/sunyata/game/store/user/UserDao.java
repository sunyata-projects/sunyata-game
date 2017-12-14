package org.sunyata.game.store.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.login.models.UserEntity;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class UserDao {
    @Autowired
    UserMapper userMapper;

    public UserEntity getUserEntity(String openId) {
        return userMapper.findByOpenId(openId);
    }

    public void insert(UserEntity userEntity) {
        userMapper.insertUser(userEntity);
    }

    public void update(UserEntity userEntity) {
        userMapper.update(userEntity);
    }
}
