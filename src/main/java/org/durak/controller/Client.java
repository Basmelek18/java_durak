package org.durak.controller;

import org.durak.controller.dto.*;
import org.durak.logic.RequestLogic;
import org.durak.model.Card;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String serverAddress = "localhost"; // Адрес сервера
        final int serverPort = 10000; // Порт сервера
        long userId = 0;
        long gameId = 0;
        List<Card> hand = new ArrayList<>();
        List<Card> table = new ArrayList<>();
        boolean loginFlag = false;
        boolean joinFlag = false;
        boolean firstMoveFlag = false;
        boolean canMoveFlag = false;
        boolean endCheckFlag = true;
        Card trump = null;

        BufferedReader ctrlIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while ((userInput = ctrlIn.readLine()) != null) {
                if (userInput.equals("l")) {
                    Object lResponse = RequestLogic.login(outputStream, inputStream);
                    userId = ((LoginResponse) lResponse).getUserId();
                    loginFlag = true;
                } else if (userInput.equals("r")) {
                    RequestLogic.registration(outputStream, inputStream);
                } else if (userInput.equals("cg") && loginFlag) {
                    RequestLogic.createGame(outputStream, inputStream, userId);
                } else if (userInput.equals("j")  && loginFlag) {
                    Object jResponse = RequestLogic.joinGame(outputStream, inputStream, userId);
                    gameId = ((JoinGameResponse) jResponse).getGameId();
                    joinFlag = true;

                    Object handResponse = RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                    hand.clear();
                    hand.addAll(((TakeCardsFromListResponse) handResponse).getCards());

                    RequestLogic.firstMove(outputStream, inputStream, userId, gameId, hand);
                }  else if (userInput.equals("ch") & endCheckFlag) {
                    long checkResponse = RequestLogic.checkFirst(outputStream, inputStream, userId, gameId);
                    if (checkResponse == userId) {
                        firstMoveFlag = true;
                    }
                    canMoveFlag = true;
                    endCheckFlag = false;
                } else if (userInput.equals("gc") & loginFlag & joinFlag) {
                    trump = RequestLogic.getCardsFromDeck(outputStream, inputStream, gameId);
                } else if (userInput.equals("h") & loginFlag & joinFlag) {
                    System.out.println(RequestLogic.cardsToStr(hand));
                } else if (userInput.equals("t") & loginFlag & joinFlag) {
                    RequestLogic.getTable(outputStream, inputStream, gameId);
                }


                if (canMoveFlag && RequestLogic.checkMove(outputStream, inputStream, userId, gameId)) {
                    table = RequestLogic.getTable(outputStream, inputStream, gameId);
                    if (userInput.equals("tc") & table.isEmpty()) {
                        Object tCResponse = RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                        hand.clear();
                        hand.addAll(((TakeCardsFromListResponse) tCResponse).getCards());
                        firstMoveFlag = true;
                    } else if (userInput.equals("m") & firstMoveFlag) {
                        RequestLogic.move(outputStream, inputStream, gameId, userId, hand, table);
                        RequestLogic.getTable(outputStream, inputStream, gameId);
                        RequestLogic.changeMove(outputStream, inputStream, userId, gameId);

                    } else if (userInput.equals("bc") & !firstMoveFlag) {
                        RequestLogic.beat(outputStream, inputStream, gameId, userId, hand, table, trump);
                        RequestLogic.changeMove(outputStream, inputStream, userId, gameId);
                    } else if (userInput.equals("b") & firstMoveFlag) {
                        RequestLogic.beaten(outputStream, inputStream, gameId);
                        firstMoveFlag = false;
                        Object tCResponse = RequestLogic.takeCardsFromDeck(outputStream, inputStream, gameId, userId, hand);
                        hand.clear();
                        hand.addAll(((TakeCardsFromListResponse) tCResponse).getCards());
                        RequestLogic.changeMove(outputStream, inputStream, userId, gameId);
                    } else if (userInput.equals("tt") & !firstMoveFlag) {
                        Object tTResponse = RequestLogic.takeFromTable(outputStream, inputStream, gameId);
                        assert tTResponse != null;
                        hand.addAll(((TakeCardsFromTableResponse) tTResponse).getCards());
                        System.out.println(hand);

                        RequestLogic.changeMove(outputStream, inputStream, userId, gameId);
                    } else if (userInput.equals("pass")) {
                        RequestLogic.changeMove(outputStream, inputStream, userId, gameId);
                    }
                }
            }

        }
    }
}
