package org.durak.controller;

import org.durak.controller.dto.TakeCardsFromListRequest;
import org.durak.controller.dto.TakeCardsFromListResponse;
import org.durak.model.Card;

import java.util.List;
import java.util.Map;

public class TakeCardsFromListController {
    public TakeCardsFromListResponse takeCardsFromList(TakeCardsFromListRequest request, Map<Long, List<Card>> cards) {
        int handSize = request.getHand().size();
        System.out.println(handSize);
        if (handSize<6) {
            List<Card> valuesCards = cards.get(request.getGameId());
            if (valuesCards.size() >= 6 - handSize) {
                for (int i = 0; i < 6 - handSize; i++) {
                    request.getHand().add(valuesCards.get(i));
                }
                valuesCards.subList(0, 6 - handSize).clear();
            } else {
                for (Card valuesCard : valuesCards) {
                    request.getHand().add(valuesCard);
                }
                if (valuesCards.size() > 0) {
                    valuesCards.subList(0, valuesCards.size()).clear();
                }
            }
            return new TakeCardsFromListResponse(request.getGameId(), request.getUserId(), request.getHand());
        }
        return null;
    }
}
