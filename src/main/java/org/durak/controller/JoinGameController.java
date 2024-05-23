package org.durak.controller;

import org.durak.controller.dto.JoinGameRequest;
import org.durak.controller.dto.JoinGameResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinGameController {
    public JoinGameResponse joinGame(JoinGameRequest request, Map<Long, List<Long>> allGamesWithPlayers) {
        List<Long> valuesList = allGamesWithPlayers.computeIfAbsent(request.getGameId(), k -> new ArrayList<>());
        valuesList.add(request.getUserId());
        return new JoinGameResponse(request.getGameId());
    }
}
