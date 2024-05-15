package org.durak.logic;

import org.durak.model.Card;

import java.util.List;

public class GameLogic {
    public List<Card> deal(Player player, List<Card> cards) {
        if (player.getHand().size() < 7) {
            List<Card> usedCards = cards.subList(0, 6 - player.getHand().size());
            player.draw(usedCards);
            usedCards.clear();
            return usedCards;
        }
        return cards;
    }

    public List<Card> take(Player player, List<Card> cards) {
        player.draw(cards);
        cards.clear();
        return cards;
    }

    public Card getTrump(List<Card> cards) {
        return cards.get(35);
    }

//    public List<Card> beaten(Player player, Card card) {
//        if player.getHand()
//    }

}
