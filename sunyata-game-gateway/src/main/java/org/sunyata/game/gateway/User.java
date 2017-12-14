package org.sunyata.game.gateway;

import org.sunyata.game.server.message.OctopusToUserRawMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class User {
    private short id;
    private Channel channel;
    private boolean isLogin;
    private boolean checkConnect = false;

    public User() {

    }

    public User(short id, Channel channel) {
        super();
        this.id = id;
        this.channel = channel;
    }

    public final short getId() {
        return id;
    }

    public final void setId(short id) {
        this.id = id;
    }

    public void writeAndFlush(OctopusToUserRawMessage obj) {
        ByteBuf buffer = channel.alloc().buffer();
        buffer.writeInt(obj.getCmd());//4
        buffer.writeInt(obj.getSerial());//8
        buffer.writeInt(obj.getCode());//4
        int length = obj.getBody() == null ? 0 : obj.getBody().length;
        buffer.writeInt(length);//4
        if (length > 0) {
            buffer.writeBytes(obj.getBody());
        }

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        channel.writeAndFlush(frame);
    }

    public final Channel getChannel() {
        return channel;
    }

    public final void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isCheckConnect() {
        return checkConnect;
    }

    public void setCheckConnect(boolean checkConnect) {
        this.checkConnect = checkConnect;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", channel=" + channel +
                ", isLogin=" + isLogin +
                ", checkConnect=" + checkConnect +
                '}';
    }
}
