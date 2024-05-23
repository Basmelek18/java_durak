package org.durak.controller.dto;

import org.durak.model.Card;

public class CardsResponse {
    private Card trump;
    private int amount;

    public CardsResponse(Card trump, int amount) {
        this.trump = trump;
        this.amount = amount;
    }

    public Card getTrump() {
        return trump;
    }

    public void setTrump(Card trump) {
        this.trump = trump;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
