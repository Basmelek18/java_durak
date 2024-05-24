package org.durak.controller;

import org.durak.controller.dto.CardsRequest;
import org.durak.controller.dto.CardsResponse;
import org.durak.model.Card;

import java.util.List;
import java.util.Map;

public class CardsController {
    public CardsResponse getCards(CardsRequest request, Map<Long, List<Card>> cards) {
        List<Card> allCards = cards.get(request.getGameId());
        int amount = allCards.size();
        Card trump = null;
        if (amount>0) {
            trump = allCards.get(amount - 1);
        }
        return new CardsResponse(trump, amount);
    }
}
