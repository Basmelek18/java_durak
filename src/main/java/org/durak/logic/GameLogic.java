package org.durak.logic;

import org.durak.model.Card;

import java.util.List;

public class GameLogic {
    /**
     * Набор карт из колоды
     * @param player игрок
     * @param cards колода
     * @return оставшиеся в колоде карты
     */
    public List<Card> deck(Player player, List<Card> cards) {
        if (player.getHand().size() < 7) {
            List<Card> usedCards = cards.subList(0, 6 - player.getHand().size());
            player.draw(usedCards);
            cards = cards.subList(6, 36);
            return usedCards;
        }
        return cards;
    }

    /**
     * Взять карты со стола
     * @param player игрок
     * @param cards карты на столе
     * @return пустой стол
     */
    public List<Card> take(Player player, List<Card> cards) {
        player.draw(cards);
        cards.clear();
        return cards;
    }

    /**
     * Возвращает козырь из колоды
     * @param cards карты в колоде(все)
     * @return последнюю карту
     */
    public Card getTrump(List<Card> cards) {
        return cards.get(35);
    }

    /**
     * Побить карту соперника(добавляет карту на стол)
     * @param player бьющий игрок
     * @param card карта, которую он выбирает
     * @param cards список всех карт на столе
     * @param trump козырь
     * @return итоговый список карт на столе
     */
    public List<Card> beaten(Player player, Card card, List<Card> cards, Card trump) {
        Card lastCardOnField = cards.get(cards.size() - 1);
        if (player.getHand().contains(card) &&
                card.getValue() > lastCardOnField.getValue() &&
                (card.getSuit() == lastCardOnField.getSuit() ||
                        card.getSuit() == trump.getSuit())) {
            cards.add(card);
            return cards;
        }
        return cards;
    }

    /**
     * Подкинуть карту
     * @param player подкидывающий
     * @param card подкидываемая карта
     * @param cards карты на столе
     * @return итоговый список карт на столе
     */
    public List<Card> toss(Player player, Card card, List<Card> cards) {
        if (player.getHand().contains(card)) {
            if (cards.size() > 0) {
                for (Card oneCard : cards) {
                    if (card.getValue() == oneCard.getValue()) {
                        cards.add(card);
                        break;
                    }
                }
            }
            else {
                cards.add(card);
            }
            return cards;
        }
        return cards;
    }

}
