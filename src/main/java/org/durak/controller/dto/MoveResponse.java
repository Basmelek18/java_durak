package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class MoveResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;

    public MoveResponse(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

}
