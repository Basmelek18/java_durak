package org.durak.controller;

import org.durak.controller.dto.*;
import org.durak.model.Card;
import org.durak.repository.GetCards;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private LoginController loginController = new LoginController();
    private RegistrationController registrationController = new RegistrationController();
    private CreateGameController createGameController = new CreateGameController();
    private JoinGameController joinGameController = new JoinGameController();
    private CardsController cardsController = new CardsController();
    private TakeCardsFromListController takeCardsFromListController = new TakeCardsFromListController();
    private CardsOnTableController cardsOnTableController = new CardsOnTableController();
    private MoveController moveController = new MoveController();
    private BeatCardController beatCardController = new BeatCardController();
    private BeatenCardsController beatenCardsController = new BeatenCardsController();
    private TakeCardsFromTableController takeCardsFromTableController = new TakeCardsFromTableController();
    private FirstMoveController firstMoveController = new FirstMoveController();
    private CheckFirstController checkFirstController = new CheckFirstController();
    private CheckMoveController checkMoveController = new CheckMoveController();
    private ChangeMoveController changeMoveController = new ChangeMoveController();

    private Map<Long, List<Long>> allGamesWithPlayers;
    private Map<Long, List<Card>> cardsInDeckMap;
    private Map<Long, Map<Card, Card>> tables;
    private Map<Long, List<Card>> firstMoveHands;
    private Map<Long, Boolean> moves;

    public ClientHandler(
            Socket clientSocket,
            Map<Long, List<Long>> allGamesWithPlayers,
            Map<Long, List<Card>> cardsInDeckMap,
            Map<Long, Map<Card, Card>> tables,
            Map<Long, List<Card>> firstMoveHands,
            Map<Long, Boolean> moves
    ) {
        this.clientSocket = clientSocket;
        this.allGamesWithPlayers = allGamesWithPlayers;
        this.cardsInDeckMap = cardsInDeckMap;
        this.tables = tables;
        this.firstMoveHands = firstMoveHands;
        this.moves = moves;
    }

    @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while (true) {
                Object clientRequest = inputStream.readUnshared();
                Object response = null;
                if (clientRequest instanceof LoginRequest) {
                    response = loginController.login((LoginRequest) clientRequest);
                } else if (clientRequest instanceof RegistrationRequest) {
                    response = registrationController.registration((RegistrationRequest) clientRequest);
                } else if (clientRequest instanceof CreateGameRequest) {
                    response = createGameController.startGame((CreateGameRequest) clientRequest);
                    CreateGameResponse response1 = (CreateGameResponse) response;
                    synchronized (allGamesWithPlayers){
                        allGamesWithPlayers.put(response1.getGameId(), new ArrayList<>());
                    }
                    synchronized (cardsInDeckMap) {
                        cardsInDeckMap.put(response1.getGameId(), GetCards.getCards());
                    }
                    synchronized (tables) {
                        tables.put(response1.getGameId(), new LinkedHashMap<>());
                    }
                } else if (clientRequest instanceof JoinGameRequest) {
                    response = joinGameController.joinGame((JoinGameRequest) clientRequest, allGamesWithPlayers);
                    synchronized (firstMoveHands) {
                        firstMoveHands.put(((JoinGameRequest) clientRequest).getUserId(), new ArrayList<>());
                    }
                } else if (clientRequest instanceof CardsRequest) {
                    response = cardsController.getCards((CardsRequest) clientRequest, cardsInDeckMap);
                } else if (clientRequest instanceof TakeCardsFromListRequest) {
                    response = takeCardsFromListController.takeCardsFromList((TakeCardsFromListRequest) clientRequest, cardsInDeckMap);
                } else if (clientRequest instanceof CardsOnTableRequest) {
                    response = cardsOnTableController.getTable((CardsOnTableRequest) clientRequest, tables);
                    System.out.println(((CardsOnTableResponse) response).getTable());
                } else if (clientRequest instanceof MoveRequest) {
                    response = moveController.move((MoveRequest) clientRequest, tables);
                } else if (clientRequest instanceof BeatCardRequest) {
                    response = beatCardController.beat((BeatCardRequest) clientRequest, tables);
                } else if (clientRequest instanceof BeatenCardsRequest) {
                    response = beatenCardsController.beatenCards((BeatenCardsRequest) clientRequest, tables);
                } else if (clientRequest instanceof TakeCardsFromTableRequest) {
                    response = takeCardsFromTableController.takeCardsFromTable((TakeCardsFromTableRequest) clientRequest, tables);
                } else if (clientRequest instanceof FirstMoveRequest) {
                    response = firstMoveController.firstMove((FirstMoveRequest) clientRequest, firstMoveHands);
                } else if (clientRequest instanceof CheckFirstRequest) {
                    response = checkFirstController.checkFirst((CheckFirstRequest) clientRequest, firstMoveHands, cardsInDeckMap);
                    synchronized (moves) {
                        moves.put(
                                ((CheckFirstRequest) clientRequest).getUserId(),
                                ((CheckFirstRequest) clientRequest).getUserId() == ((CheckFirstResponse) response).getUserId());
                    }
                } else if (clientRequest instanceof CheckMoveRequest) {
                    response = checkMoveController.approved((CheckMoveRequest) clientRequest, moves);
                } else if (clientRequest instanceof ChangeMoveRequest) {
                    response = changeMoveController.changeMove((ChangeMoveRequest) clientRequest, moves);
                }

                outputStream.reset();
                outputStream.writeObject(response);
                outputStream.flush();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
