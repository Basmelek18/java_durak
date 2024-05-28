package org.durak.controller.dto;

import org.durak.model.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class CardsOnTableResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long gameId;
    private Map<Long, Map<Card, Card>> table;

    public CardsOnTableResponse(long gameId, Map<Long, Map<Card, Card>> table) {
        this.gameId = gameId;
        this.table = table;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Map<Long, Map<Card, Card>> getTable() {
        return table;
    }

    public void setTable(Map<Long, Map<Card, Card>> table) {
        this.table = table;
    }
}
