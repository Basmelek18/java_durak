package org.durak.controller;

import org.durak.controller.dto.CreateGameRequest;
import org.durak.controller.dto.CreateGameResponse;

import java.util.Random;

public class CreateGameController {
    private Random random = new Random();

    public CreateGameResponse startGame(CreateGameRequest request) {
        long gameId = random.nextLong();
        return new CreateGameResponse(gameId);
    }
}
