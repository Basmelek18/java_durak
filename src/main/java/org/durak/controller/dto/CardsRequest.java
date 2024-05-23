package org.durak.controller.dto;

public class CardsRequest {
    private long gameId;

    public CardsRequest(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
