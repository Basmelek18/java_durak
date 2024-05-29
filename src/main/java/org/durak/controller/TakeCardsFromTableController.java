package org.durak.controller;

import org.durak.controller.dto.TakeCardsFromTableRequest;
import org.durak.controller.dto.TakeCardsFromTableResponse;
import org.durak.model.Card;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TakeCardsFromTableController {
    public TakeCardsFromTableResponse takeCardsFromTable(TakeCardsFromTableRequest request, Map<Long, Map<Card, Card>> table) {
        List<Card> cards = new ArrayList<>();
        for (Map.Entry<Card, Card> entry : table.get(request.getGameId()).entrySet()) {
            if (entry.getKey() != null) {
                cards.add(entry.getKey());
            }
            if (entry.getValue() != null) {
                cards.add(entry.getKey());
            }
            cards.add(entry.getValue());
        }
        table.put(request.getGameId(), new LinkedHashMap<>());
        return new TakeCardsFromTableResponse(request.getGameId(), cards);
    }
}
