package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;

public class MoveRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private Card card;

    public MoveRequest(long gameId, Card card) {
        this.gameId = gameId;
        this.card = card;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
