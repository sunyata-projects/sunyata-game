package org.sunyata.game.majiang.core;

import org.sunyata.game.server.message.Message;

public interface MessageHandler<T extends Message,U> {
	/**
	 * 返回值决定是否把这个对象脱离对象缓冲！
	 * 
	 * @param msg
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */

    boolean handler(T msg, U user) throws Exception;
}
