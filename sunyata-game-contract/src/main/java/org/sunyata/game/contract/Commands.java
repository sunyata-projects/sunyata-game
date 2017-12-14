package org.sunyata.game.contract;

/**
 * Created by leo on 17/11/9.
 */
public class Commands {
    //game org.sunyata.game.login service
    public final static String login = "51001";//登录请求
    public final static String loginRet = "5100101";//登录返回
    public final static String loginSuccessEvent = "51003";
    public final static String loginEvent = "51601";
    public final static String logout = "51002";//登录请求
    public final static String userOffline = "51004";//登录请求

    public final static String userJoinGame = "51005";//登录请求

    public final static String createOrder = "62001";

    /***************************/
    public final static String gameUserInfo = "6400101";
    /***************************/

    /*************
     * 房间相关的消息编号
     **************/
    public final static String createRoom = "63001";//创建房间
    public final static String createRoomRet = "6300101";//创建房间返回

    public final static String joinRoom = "63002";//加入房间
    public final static String joinRoomToScene = "63003";
    public final static String joinRoomRet = "6300201";//加入房间返回

    public final static String joinGame = "63004";//加入游戏
    public final static String joinGameToScene = "63005";
    public final static String gameRoomInfo = "6300401";//加入游戏返回
    /*************房间相关的消息编号**************/

    /*************
     * 麻将相关的消息编号
     **************/
    //客户端发送信息
    public final static String optFaPaiRet = "71001";//发牌后,玩家打牌发送此消息
    public final static String optOutRet = "71002";//用户吃碰杠后,打牌发送此消息
    public final static String optCPGHRet = "71003";//用户吃碰杠和,发送此消息

    //下发到客户端信息
    public final static String optFaPai = "7100101";//
    public final static String optCPGH = "7100201";//
    public final static String optOut = "7100301";//通知当前用户出牌(在吃碰后)
    public final static String tingPai = "7100401";//通知当前用户出牌(在吃碰后)
    public final static String syncOpt = "7100501";//通知房间内其它用户,当前用户已经出牌
    public final static String syncOptTime = "7100601";//通知用户开始计时(房间所有用户)
    public final static String majiangChapterInfo = "7100701";//一局麻将信息,在局开始时发送给客户端


    /*************
     * 麻将相关的消息编号
     **************/

    //game inner gateway service
    //game gateway service
    //game test service

    public final static String testCommand = "40001";
    public final static String testCommandRet = "4000101";
    public final static String testCommand2Ret = "4000102";


    public final static String playCommand = "40002";

    public final static String errorCommand = "99001";
    public final static String notice = "99002";
}
