package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class TakeCardsFromListResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private long userId;
    private List<Card> cards;

    public TakeCardsFromListResponse(long gameId, long userId, List<Card> cards) {
        this.gameId = gameId;
        this.userId = userId;
        this.cards = cards;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
