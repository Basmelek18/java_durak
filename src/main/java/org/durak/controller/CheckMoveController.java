package org.durak.controller;

import org.durak.controller.dto.CheckMoveRequest;
import org.durak.controller.dto.CheckMoveResponse;

import java.util.Map;

public class CheckMoveController {
    public CheckMoveResponse approved(CheckMoveRequest request, Map<Long, Boolean> moves) {
        return new CheckMoveResponse(moves.get(request.getUserId()));
    }
}
