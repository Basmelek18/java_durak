package org.durak.model;

public class Card {
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
