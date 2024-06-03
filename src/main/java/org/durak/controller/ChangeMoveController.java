package org.durak.controller;

import org.durak.controller.dto.ChangeMoveRequest;
import org.durak.controller.dto.ChangeMoveResponse;

import java.util.Map;

public class ChangeMoveController {
    public ChangeMoveResponse changeMove(ChangeMoveRequest request, Map<Long, Boolean> moves) {
        for (Map.Entry<Long, Boolean> entry: moves.entrySet()) {
            moves.put(entry.getKey(), !entry.getValue());
        }
        return new ChangeMoveResponse(request.getUserId());
    }
}
