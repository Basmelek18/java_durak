package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;

public class CardsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
