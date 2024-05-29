package org.durak.controller;

import org.durak.controller.dto.BeatCardRequest;
import org.durak.controller.dto.BeatCardResponse;
import org.durak.model.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeatCardController {

    private static <K, V> Map.Entry<K, V> getLastEntry(Map<K, V> map) {
        Map.Entry<K, V> lastEntry = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            lastEntry = entry;
        }
        return lastEntry;
    }
    public BeatCardResponse beat(BeatCardRequest request, Map<Long, Map<Card, Card>> tables) {
        Map.Entry<Card, Card> cardEntry = getLastEntry(tables.get(request.getGameId()));
        tables.computeIfAbsent(request.getGameId(), k -> new LinkedHashMap<>())
                .put(cardEntry.getKey(), request.getCard());
        return new BeatCardResponse(request.getGameId());
    }
}
