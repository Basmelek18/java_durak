package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class TakeCardsFromListRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private long userId;
    private List<Card> hand;

    public TakeCardsFromListRequest(long gameId, long userId, List<Card> hand) {
        this.gameId = gameId;
        this.userId = userId;
        this.hand = hand;
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

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
}
