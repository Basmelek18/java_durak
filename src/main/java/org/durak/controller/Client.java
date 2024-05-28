package org.durak.controller;

import org.durak.controller.dto.JoinGameResponse;
import org.durak.controller.dto.LoginResponse;
import org.durak.controller.dto.TakeCardsFromListResponse;
import org.durak.logic.RequestLogic;
import org.durak.model.Card;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String serverAddress = "localhost"; // Адрес сервера
        final int serverPort = 8000; // Порт сервера
        long userId = 0;
        long gameId = 0;
        List<Card> hand = new ArrayList<>();

        BufferedReader ctrlIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while ((userInput = ctrlIn.readLine()) != null) {
                if (userInput.equals("l")) {
                    Object response = RequestLogic.login(outputStream, inputStream);
                    userId = ((LoginResponse) response).getUserId();
                } else if (userInput.equals("r")) {
                    RequestLogic.registration(outputStream, inputStream);
                } else if (userInput.equals("cg")) {
                    RequestLogic.createGame(outputStream, inputStream, userId);
                } else if (userInput.equals("j")) {
                    Object response = RequestLogic.joinGame(outputStream, inputStream, userId);
                    gameId = ((JoinGameResponse) response).getGameId();
                } else if (userInput.equals("gc")) {
                    RequestLogic.getCardsFromDeck(outputStream, inputStream, gameId);
                } else if (userInput.equals("tc")) {
                    Object response = RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                    hand.addAll(((TakeCardsFromListResponse) response).getCards());
                } else if (userInput.equals("t")) {
                    RequestLogic.getTable(outputStream, inputStream, gameId);
                } else if (userInput.equals("m")) {
                    RequestLogic.move(outputStream, inputStream, gameId, hand);
                }
            }

        }
    }
}





//            String userInput;
//            String myLogin = "";
//            String serverAnswer;
//
//            while ((userInput = stdIn.readLine()) != null) {
//                if (!myLogin.equals("")) {
//                    out.writeObject(myLogin + ":" + userInput); // Отправка данных на сервер
//                } else {
//                    out.writeObject(userInput); // Отправка данных на сервер
//                }
//                serverAnswer = in.readObject().toString();
//                System.out.println("От сервера: " + serverAnswer); // Вывод ответа от сервера
//                if (serverAnswer.startsWith("You are ")) {
//                    myLogin = serverAnswer;
//                    int lastIndex = myLogin.lastIndexOf(" "); // Находим индекс последнего пробела
//                    myLogin = myLogin.substring(lastIndex + 1);
//                    System.out.println("My login " + myLogin);
//                }
//            }
//            List<Card> cards = (List<Card>) in.readObject();
//            if (cards != null) {
//                System.out.println("Received cards:");
//                for (Card card : cards) {
//                    System.out.println(card);
//                }
//            } else {
//                System.out.println("No more cards available.");
//            }
//        } catch (UnknownHostException e) {
//            System.err.println("Не удалось определить хост: " + serverAddress);
//        } catch (IOException e) {
//            System.err.println("Ошибка ввода/вывода для соединения с " + serverAddress);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }