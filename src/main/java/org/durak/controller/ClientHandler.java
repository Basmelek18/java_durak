package org.durak.controller;

import org.durak.controller.dto.*;
import org.durak.model.Card;
import org.durak.repository.GetCards;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private LoginController loginController = new LoginController();
    private RegistrationController registrationController = new RegistrationController();
    private CreateGameController createGameController = new CreateGameController();
    private JoinGameController joinGameController = new JoinGameController();
    private CardsController cardsController = new CardsController();
    private TakeCardsFromListController takeCardsFromListController = new TakeCardsFromListController();
    private Map<Long, List<Long>> allGamesWithPlayers;
    private Map<Long, List<Card>> cardsInDeckMap;

    public ClientHandler(Socket clientSocket, Map<Long, List<Long>> allGamesWithPlayers, Map<Long, List<Card>> cardsInDeckMap) {
        this.clientSocket = clientSocket;
        this.allGamesWithPlayers = allGamesWithPlayers;
        this.cardsInDeckMap = cardsInDeckMap;
    }

    @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while (true) {
                Object clientRequest = inputStream.readObject();
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
                } else if (clientRequest instanceof JoinGameRequest) {
                    response = joinGameController.joinGame((JoinGameRequest) clientRequest, allGamesWithPlayers);
                } else if (clientRequest instanceof CardsRequest) {
                    response = cardsController.getCards((CardsRequest) clientRequest, cardsInDeckMap);
                } else if (clientRequest instanceof TakeCardsFromListRequest) {
                    response = takeCardsFromListController.takeCardsFromList((TakeCardsFromListRequest) clientRequest, cardsInDeckMap);
                }
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
