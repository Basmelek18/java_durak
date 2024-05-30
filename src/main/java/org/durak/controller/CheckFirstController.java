package org.durak.controller;

import org.durak.controller.dto.CheckFirstRequest;
import org.durak.controller.dto.CheckFirstResponse;
import org.durak.model.Card;

import java.util.List;
import java.util.Map;

public class CheckFirstController {
    public CheckFirstResponse checkFirst(CheckFirstRequest request, Map<Long, List<Card>> hands, Map<Long, List<Card>> deck) {
        Card trump = deck
                .get(request.getGameId())
                .get(deck.get(request.getGameId()).size() - 1);
        long result = 0L;
        Card lowerTrump = new Card(15, trump.getSuit());
        Card lowerCard = new Card(5, trump.getSuit());
        boolean trumpFlag = false;
        for (Map.Entry<Long, List<Card>> entry : hands.entrySet()) {
            for (Card card : entry.getValue()) {
                if (card.getSuit() == trump.getSuit()) {
                    if (card.getValue() < lowerTrump.getValue()) {
                        lowerTrump = card;
                        result = entry.getKey();
                        trumpFlag = true;
                    }
                }
            }
        }
        if (!trumpFlag) {
            for (Map.Entry<Long, List<Card>> entry : hands.entrySet()) {
                for (Card card : entry.getValue()) {
                    if (card.getValue() >= lowerCard.getValue()) {
                        lowerCard = card;
                        result = entry.getKey();
                    }
                }
            }
        }
        return new CheckFirstResponse(result);
    }
}
