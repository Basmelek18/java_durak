package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class TakeCardsFromTableResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private List<Card> cards;

    public TakeCardsFromTableResponse(long gameId, List<Card> cards) {
        this.gameId = gameId;
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
