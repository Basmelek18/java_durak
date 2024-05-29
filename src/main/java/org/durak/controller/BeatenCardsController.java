package org.durak.controller;

import org.durak.controller.dto.BeatenCardsRequest;
import org.durak.controller.dto.BeatenCardsResponse;
import org.durak.model.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeatenCardsController {
    public BeatenCardsResponse beatenCards(BeatenCardsRequest request, Map<Long, Map<Card, Card>> table) {
        table.put(request.getGameId(), new LinkedHashMap<>());
        return new BeatenCardsResponse(request.getGameId());
    }
}
