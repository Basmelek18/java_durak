package org.durak.logic;

import org.durak.model.Card;
import org.durak.model.User;

import java.util.List;

public class Player {
    private User user;
    private List<Card> hand;

    public Player (User user, List<Card> hand) {
        this.user = user;
        this.hand = hand;
    }

    public Card move(int cardIndexInHand) {
        Card card = hand.get(cardIndexInHand);
        hand.remove(cardIndexInHand);
        return card;
    }

    public void draw(List<Card> cards) {
        hand.addAll(cards);
    }

    public String endOfRound() {
        return "End of Round";
    }

    public List<Card> getHand() {
        return hand;
    }
}
