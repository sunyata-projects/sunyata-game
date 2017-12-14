package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.server.message.AbstractJsonBodySerializer;

public class JoinGameToSceneMsg extends AbstractJsonBodySerializer {
    public int getUserId() {
        return userId;
    }

    public JoinGameToSceneMsg setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getUserIdInGateway() {
        return userIdInGateway;
    }

    public JoinGameToSceneMsg setUserIdInGateway(int userIdInGateway) {
        this.userIdInGateway = userIdInGateway;
        return this;
    }

    public int getGatewayServerId() {
        return gatewayServerId;
    }

    public JoinGameToSceneMsg setGatewayServerId(int gatewayServerId) {
        this.gatewayServerId = gatewayServerId;
        return this;
    }

    private int userId;
    private int userIdInGateway;
    private int gatewayServerId;


}