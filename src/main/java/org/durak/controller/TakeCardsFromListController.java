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
            for (int i = 0; i < 6 - handSize; i++) {
                request.getHand().add(valuesCards.get(i));
            }
            for (int i = 0; i < 6 - handSize; i++) {
                valuesCards.remove(0);
            }
            return new TakeCardsFromListResponse(request.getGameId(), request.getUserId(), request.getHand());
        }
        return null;
    }
}
