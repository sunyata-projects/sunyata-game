package org.sunyata.game.login;

import org.sunyata.core.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.exceptions.UserAndPasswordIsNotRightException;
import org.sunyata.game.login.models.UserEntity;
import org.sunyata.game.login.models.UserLoginLog;
import org.sunyata.game.store.user.UserDao;
import org.sunyata.game.store.user.UserLoginLogDao;

import java.util.Date;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class UserService {
    //登录类型1用户名密码,2手机验证码,3微信,4token,9匿名,
    public static final int LOGIN_TYPE_USERNAME_PASSWORD = 1;
    public static final int LOGIN_TYPE_MOBILE_SMS = 2;
    public static final int LOGIN_TYPE_WEIXIN = 3;
    public static final int LOGIN_TYPE_TOKEN = 4;
    public static final int LOGIN_TYPE_ANONYMOUS = 9;

    @Autowired
    UserDao userDao;
    @Autowired
    UserLoginLogDao userLoginLogDao;

    public UserLoginRetInfo loginByOpenId(int type, String openId, String code) throws Exception {
        UserLoginRetInfo login = login(type, openId, code);
        return login;
    }

    private UserLoginRetInfo login(int type, String openId, String code) throws Exception {
        UserEntity userEntity = null;
        boolean result = false;
        if (type == LOGIN_TYPE_USERNAME_PASSWORD) {
            userEntity = getUserByUserName(openId);
            if (userEntity == null) {
                userEntity = insertByUserNameAndPasswordIfNecessary(openId, code);
            }
            if (digestPassword(code).equals(userEntity.getPassword())) {
                result = true;
            } else {
                throw new UserAndPasswordIsNotRightException("用户名密码不正确");
            }
        } else if (type == LOGIN_TYPE_MOBILE_SMS) {

        } else if (type == LOGIN_TYPE_WEIXIN) {

        } else if (type == LOGIN_TYPE_TOKEN) {

        } else if (type == LOGIN_TYPE_ANONYMOUS) {
        }
        if (result) {
            return login(type, "", "10.01", "30.04", userEntity);
        }
        return null;
    }

    private UserLoginRetInfo login(int type, String ip, String longitude, String latitude, UserEntity userEntity) {
        userEntity.setLogin_token(java.util.UUID.randomUUID().toString());
        userEntity.setIp(ip);
        userEntity.setLongitude(Double.parseDouble(longitude)).setLatitude(Double.parseDouble(latitude));
        //记录登录日志
        userDao.update(userEntity);
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUser_id(userEntity.getId());
        userLoginLog.setLogin_date(new Date());
        userLoginLog.setIp(userEntity.getIp());
        userLoginLog.setLogin_token(userEntity.getLogin_token());
        userLoginLogDao.insert(userLoginLog);
        UserLoginRetInfo result = new UserLoginRetInfo();
        result.setId(userEntity.getId()).setName(userEntity.getName()).setOpenId(userEntity.getOpen_id()).setUuid
                (userEntity.getUuid()).setAvatar(userEntity.getAvatar()).setSex(userEntity.getSex()).setGold
                (userEntity.getGold()).setLoginToken(userEntity.getLogin_token()).setIp(userEntity.getIp());
        if (result.getAvatar() == null) {
            result.setAvatar("");
        }
        if (result.getRoomCheckId() == null) {
            result.setRoomCheckId("");
        }
        result.setUserEntity(userEntity);
        return result;
    }

    public UserEntity insertByUserNameAndPasswordIfNecessary(String userName, String password) {
        UserEntity userEntity = userDao.getUserEntity(userName);
        //if (userEntity != null && userEntity.getPassword().equals(digestPassword(password))) {
        // return true;
        //}
        //return false;
        if (userEntity == null) {
            Date now = new Date();
            userEntity = new UserEntity().setOpen_id(userName).setPassword(digestPassword(password))
                    .setGold(10000).setName(userName)
                    .setUuid(UUID.generate())
                    .setUpdate_date(now).setCreate_date(now)
                    .setSex(1);
            userDao.insert(userEntity);
        }

        return userEntity;
    }

    public UserEntity getUserByUserName(String userName) {
        UserEntity userEntity = userDao.getUserEntity(userName);
        return userEntity;
    }

    private static String digestPassword(String password) {
        return Base64.encodeBase64URLSafeString(DigestUtils.sha256(salt + password));
    }

    private static final String salt = "n532ij";


}
