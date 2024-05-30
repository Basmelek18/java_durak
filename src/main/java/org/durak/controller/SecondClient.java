package org.durak.controller;

import org.durak.controller.dto.JoinGameResponse;
import org.durak.controller.dto.LoginResponse;
import org.durak.controller.dto.TakeCardsFromListResponse;
import org.durak.controller.dto.TakeCardsFromTableResponse;
import org.durak.logic.RequestLogic;
import org.durak.model.Card;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SecondClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String serverAddress = "localhost"; // Адрес сервера
        final int serverPort = 8000; // Порт сервера
        long userId = 0;
        long gameId = 0;
        List<Card> hand = new ArrayList<>();
        List<Card> table = new ArrayList<>();

        BufferedReader ctrlIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            boolean loginFlag = false;
            boolean joinFlag = false;
            while ((userInput = ctrlIn.readLine()) != null) {
                if (userInput.equals("l")) {
                    Object response = RequestLogic.login(outputStream, inputStream);
                    userId = ((LoginResponse) response).getUserId();
                    loginFlag = true;
                } else if (userInput.equals("r")) {
                    RequestLogic.registration(outputStream, inputStream);
                } else if (userInput.equals("cg") && loginFlag) {
                    RequestLogic.createGame(outputStream, inputStream, userId);
                } else if (userInput.equals("j")  && loginFlag) {
                    Object response = RequestLogic.joinGame(outputStream, inputStream, userId);
                    gameId = ((JoinGameResponse) response).getGameId();
                    joinFlag = true;
                } else if (userInput.equals("gc") && loginFlag && joinFlag) {
                    RequestLogic.getCardsFromDeck(outputStream, inputStream, gameId);
                } else if (userInput.equals("tc") && loginFlag && joinFlag) {
                    Object response = RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                    hand.clear();
                    hand.addAll(((TakeCardsFromListResponse) response).getCards());
                } else if (userInput.equals("t") && loginFlag && joinFlag) {
                    table = RequestLogic.getTable(outputStream, inputStream, gameId);
                } else if (userInput.equals("m") && loginFlag && joinFlag) {
                    RequestLogic.move(outputStream, inputStream, gameId, userId, hand, table);
                    table = RequestLogic.getTable(outputStream, inputStream, gameId);
                } else if (userInput.equals("h") && loginFlag && joinFlag) {
                    System.out.println(RequestLogic.cardsToStr(hand));
                } else if (userInput.equals("bc") && loginFlag && joinFlag) {
                    RequestLogic.beat(outputStream, inputStream, gameId, userId, hand);
                } else if (userInput.equals("b") && loginFlag && joinFlag) {
                    RequestLogic.beaten(outputStream, inputStream, gameId);
                } else if (userInput.equals("tt") && loginFlag && joinFlag) {
                    Object response = RequestLogic.takeFromTable(outputStream, inputStream, gameId);
                    hand.addAll(((TakeCardsFromTableResponse) response).getCards());
                }
            }

        }
    }
}

