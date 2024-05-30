package org.durak.controller;

import org.durak.controller.dto.FirstMoveRequest;
import org.durak.controller.dto.FirstMoveResponse;
import org.durak.model.Card;

import java.util.List;
import java.util.Map;

public class FirstMoveController {
    public FirstMoveResponse firstMove(FirstMoveRequest request, Map<Long, List<Card>> cards) {
        cards.put(request.getUserId(), request.getHand());
        return new FirstMoveResponse(request.getGameId());
    }
}
