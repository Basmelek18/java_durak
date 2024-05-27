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
    private Map<Long, List<Long>> allGamesWithPlayers = new HashMap<>();
    private Map<Long, List<Card>> cardsInDeckMap = new HashMap<>();

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
                } else if (clientRequest instanceof CreateGameController) {
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
                } else if (clientRequest instanceof CardsController) {
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


//        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
//
//            String inputLine;
//            String empty = in.readLine();
//            out.println("Your login:");
//            String login = in.readLine();
//            User newUser = null;
//            List<Card> hand = new ArrayList<>();
//            if (!SaveUser.existingUser(login)) {
//                out.println("Your name:");
//                String name = in.readLine();
//                newUser = new User(name, login, 0);
//                out.println(newUser);
//                SaveUser.createUser(newUser);
//            }
//            out.println("You are " + login);
//
//            synchronized (cards) {
//                if (cards.isEmpty()) {
//                    out.writeObject(null);  // No cards left to distribute
//                } else {
//                    int end = Math.min(6, cards.size());
//                    List<Card> clientCards = cards.subList(0, end);
//                    out.writeObject(clientCards);
//                    cards.subList(0, end).clear();  // Remove the distributed cards
//                }
//            //Козырь
//            GameLogic logic = new GameLogic();
//            Card trump = logic.getTrump(cardsInDeck);
//            //Создаем игроков
//            Player player = new Player(newUser, hand);
//            //Раздаем по 6 карт
//            cardsInDeck = logic.deck(player, cardsInDeck);
//            out.println(cardsInDeck);
//
//
//            // Чтение сообщения от клиента и отправка ответа
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("От клиента: " + inputLine);
//                // Передача сообщения всем остальным клиентам
//                Server.broadcastMessage(inputLine, this);
//                out.flush();
//
//            }
//        } catch (IOException e) {
//            System.err.println("Error handling client: " + e.getMessage());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                clientSocket.close(); // Закрытие соединения с клиентом
//            } catch (IOException e) {
//                System.err.println("Error closing client socket: " + e.getMessage());
//            }
//        }
    }
}
