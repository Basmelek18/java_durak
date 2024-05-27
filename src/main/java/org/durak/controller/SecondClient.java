package org.durak.controller;

import org.durak.controller.dto.JoinGameResponse;
import org.durak.controller.dto.LoginResponse;
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
                    RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                }
            }

        }
    }
}

