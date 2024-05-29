package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class BeatenCardsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;

    public BeatenCardsResponse(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }


}
