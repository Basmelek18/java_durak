package org.durak.controller;

import org.durak.controller.dto.MoveRequest;
import org.durak.controller.dto.MoveResponse;
import org.durak.model.Card;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MoveController {
    public MoveResponse move(MoveRequest request, Map<Long, Map<Card, Card>> table) {
        table.computeIfAbsent(request.getGameId(), k -> new LinkedHashMap<>())
                .put(request.getCard(), null);
        return new MoveResponse(request.getGameId());
    }
}
