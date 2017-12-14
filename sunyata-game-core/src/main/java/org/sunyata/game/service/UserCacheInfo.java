package org.sunyata.game.service;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leo on 17/11/15.
 */
public class UserCacheInfo implements Serializable {
    private int id;
    /**昵称*/
    private String name;
    private String open_id;
    /**用户唯一uuid*/
    private String uuid;
    /**用户头像地址*/
    private String avatar;
    /**0:女生,1:男生,2:未知*/
    private int sex;
    private Date create_date;
    private Date update_date;
    private int version;
    private int gold;
    private String mobile;
    private String login_token;
    private int history_gold;
    /**用户代理级别*/
    private int level;
    /**ip*/
    private String ip;
    /**ip*/
    private double longitude;
    /**ip*/
    private double latitude;
    /**密码*/
    private String password;

    public int getId() {
        return id;
    }

    public UserCacheInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserCacheInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getOpen_id() {
        return open_id;
    }

    public UserCacheInfo setOpen_id(String open_id) {
        this.open_id = open_id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public UserCacheInfo setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserCacheInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public UserCacheInfo setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public UserCacheInfo setCreate_date(Date create_date) {
        this.create_date = create_date;
        return this;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public UserCacheInfo setUpdate_date(Date update_date) {
        this.update_date = update_date;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public UserCacheInfo setVersion(int version) {
        this.version = version;
        return this;
    }

    public int getGold() {
        return gold;
    }

    public UserCacheInfo setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public UserCacheInfo setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getLogin_token() {
        return login_token;
    }

    public UserCacheInfo setLogin_token(String login_token) {
        this.login_token = login_token;
        return this;
    }

    public int getHistory_gold() {
        return history_gold;
    }

    public UserCacheInfo setHistory_gold(int history_gold) {
        this.history_gold = history_gold;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public UserCacheInfo setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public UserCacheInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public UserCacheInfo setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public UserCacheInfo setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCacheInfo setPassword(String password) {
        this.password = password;
        return this;
    }
}
