package org.durak.controller;

import org.durak.controller.dto.CardsRequest;
import org.durak.controller.dto.CardsResponse;
import org.durak.model.Card;

import java.util.List;
import java.util.Map;

public class CardsController {
    public CardsResponse getCards(CardsRequest request, Map<Long, List<Card>> cards) {
        Card trump = cards.get(request.getGameId()).get(35);
        int amount = cards.get(request.getGameId()).size();
        return new CardsResponse(trump, amount);
    }
}
