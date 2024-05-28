package org.durak.controller;

import org.durak.controller.dto.MoveRequest;
import org.durak.controller.dto.MoveResponse;
import org.durak.model.Card;

import java.util.HashMap;
import java.util.Map;

public class MoveController {
    public MoveResponse move(MoveRequest request, Map<Long, Map<Card, Card>> table) {
        table.computeIfAbsent(request.getGameId(), k -> new HashMap<>())
                .put(request.getCard(), null);
        System.out.println(table);
        return new MoveResponse(request.getGameId());
    }
}
