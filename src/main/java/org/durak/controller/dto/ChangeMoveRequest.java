package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class ChangeMoveRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private long userId;

    public ChangeMoveRequest(long gameId, long userId) {
        this.gameId = gameId;
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
