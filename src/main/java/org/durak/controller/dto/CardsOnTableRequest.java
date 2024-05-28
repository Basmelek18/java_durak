package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CardsOnTableRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;

    public CardsOnTableRequest(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
