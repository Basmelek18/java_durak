package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;

public class BeatCardRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private long userId;
    private Card card;


    public BeatCardRequest(long gameId, long userId, Card card) {
        this.gameId = gameId;
        this.userId = userId;
        this.card = card;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
