package org.durak.model;

import java.io.Serial;
import java.io.Serializable;

public class Card implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int rank;
    private final int suit;


    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getValue() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }
}
