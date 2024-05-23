package org.durak.controller;

import org.durak.logic.GameLogic;
import org.durak.logic.Player;
import org.durak.model.Card;
import org.durak.model.User;
import org.durak.repository.GetCards;
import org.durak.repository.SaveUser;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread implements Runnable {
    private Socket clientSocket;
    private List<Card> cards = new ArrayList<>();
    private PrintWriter out;

    public ClientHandler(Socket clientSocket, List<Card> cards) {
        this.clientSocket = clientSocket;
        this.cards = cards;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String inputLine;
            Object empty = inputStream.readObject();
            outputStream.writeObject("Your login:");
            Object login = inputStream.readObject();
            User user;
            if (!SaveUser.existingUser(login.toString())) {
                outputStream.writeObject("Your name:");
                Object name = inputStream.readObject();
                user = new User(name.toString(), login.toString(), 0);
                outputStream.writeObject(user);
                SaveUser.createUser(user);
            } else {
                user = SaveUser.getUser(login.toString());
            }
            outputStream.writeObject("You are " + login);
            outputStream.writeObject(user);

            synchronized (cards) {
                if (cards.isEmpty()) {
                    outputStream.writeObject(null);  // No cards left to distribute
                } else {
                    int end = Math.min(6, cards.size());
                    List<Card> clientCards = cards.subList(0, end);
                    outputStream.writeObject(clientCards);
                    cards.subList(0, end).clear();  // Remove the distributed cards
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
