package org.durak.controller;

import org.durak.controller.dto.CardsOnTableRequest;
import org.durak.controller.dto.CardsOnTableResponse;
import org.durak.controller.dto.CardsRequest;
import org.durak.controller.dto.CardsResponse;
import org.durak.model.Card;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardsOnTableController {
    public CardsOnTableResponse getTable(CardsOnTableRequest request, Map<Long, Map<Card, Card>> tables) {
        return new CardsOnTableResponse(request.getGameId(), tables);
    }
}
