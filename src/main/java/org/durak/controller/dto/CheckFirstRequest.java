package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CheckFirstRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long userId;
    private long gameId;

    public CheckFirstRequest(long userId, long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
