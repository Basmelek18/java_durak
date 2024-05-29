package org.durak.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
