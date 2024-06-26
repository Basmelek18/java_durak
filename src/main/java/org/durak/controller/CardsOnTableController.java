package org.durak.controller;

import org.durak.controller.dto.CardsOnTableRequest;
import org.durak.controller.dto.CardsOnTableResponse;
import org.durak.model.Card;

import java.util.Map;

public class CardsOnTableController {
    public CardsOnTableResponse getTable(CardsOnTableRequest request, Map<Long, Map<Card, Card>> tables) {
        return new CardsOnTableResponse(request.getGameId(), tables);
    }
}
