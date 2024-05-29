package org.durak.logic;

import org.durak.controller.dto.*;
import org.durak.model.Card;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class RequestLogic {

    public static Object login(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn = new BufferedReader(new InputStreamReader(System.in));
        String value = valIn.readLine();
        LoginRequest loginRequest = new LoginRequest(value);
        outputStream.reset();
        outputStream.writeObject(loginRequest);

        Object response = inputStream.readObject();

        if (response instanceof LoginResponse loginResponse) {
            System.out.println("Login successful: " + loginResponse.getUserId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static void registration(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        String value1 = valIn1.readLine();
        System.out.println("Your name");
        BufferedReader valIn2 = new BufferedReader(new InputStreamReader(System.in));
        String value2 = valIn2.readLine();
        RegistrationRequest registrationRequest = new RegistrationRequest(value1, value2);
        outputStream.reset();
        outputStream.writeObject(registrationRequest);

        Object response = inputStream.readObject();

        if (response instanceof RegistrationResponse) {
            System.out.println("User successfully registered");
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static void createGame(ObjectOutputStream outputStream, ObjectInputStream inputStream, long userId) throws IOException, ClassNotFoundException {
        CreateGameRequest createGameRequest = new CreateGameRequest(userId);
        outputStream.reset();
        outputStream.writeObject(createGameRequest);
        Object response = inputStream.readObject();

        if (response instanceof CreateGameResponse createGameResponse) {
            System.out.println("Game successfully created " + createGameResponse.getGameId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static Object joinGame(ObjectOutputStream outputStream, ObjectInputStream inputStream, long userId) throws IOException, ClassNotFoundException {
        System.out.println("Which game?");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        long gameId = Long.parseLong(valIn1.readLine());
        JoinGameRequest joinGameRequest = new JoinGameRequest(userId, gameId);
        outputStream.reset();
        outputStream.writeObject(joinGameRequest);
        Object response = inputStream.readObject();

        if (response instanceof JoinGameResponse joinGameResponse) {
            System.out.println("Game successfully has been joined " + joinGameResponse.getGameId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static void getCardsFromDeck(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        CardsRequest cardsRequest = new CardsRequest(gameId);
        outputStream.reset();
        outputStream.writeObject(cardsRequest);
        Object response = inputStream.readObject();

        if (response instanceof CardsResponse cardsResponse) {
            if (cardsResponse.getTrump() == null) {
                System.out.println("Cards deck is empty");
            } else {
                System.out.println("Cards in a deck " + cardsResponse.getAmount() +
                        ". Trump is " + cardsResponse.getTrump().getValue() +
                        " " + cardsResponse.getTrump().getSuit());
            }
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static Object takeCardsFromDeck(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand) throws IOException, ClassNotFoundException {
        TakeCardsFromListRequest request = new TakeCardsFromListRequest(gameId, userId, hand);
        System.out.println(hand);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();

        if (response instanceof TakeCardsFromListResponse takeCardsFromListResponse) {
            System.out.println("You got " + takeCardsFromListResponse.getCards());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static void getTable(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        CardsOnTableRequest request = new CardsOnTableRequest(gameId);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();

        if (response instanceof CardsOnTableResponse cardsOnTableResponse) {
            System.out.println("You got " + cardsOnTableResponse.getTable());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }

    }

    public static void move(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand) throws IOException, ClassNotFoundException {
        System.out.println("Which card?");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        Card card = hand.get(Integer.parseInt(valIn1.readLine()) - 1);
        hand.remove(card);

        MoveRequest request = new MoveRequest(gameId, userId, card);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();
        System.out.println(response);

        if (response instanceof MoveResponse) {
            System.out.println("You are moving with " + card);
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }

    }

    public static void beat(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand) throws IOException, ClassNotFoundException {
        System.out.println("Which card?");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        Card card = hand.get(Integer.parseInt(valIn1.readLine()) - 1);
        hand.remove(card);

        BeatCardRequest request = new BeatCardRequest(gameId, userId, card);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();

        if (response instanceof BeatCardResponse) {
            System.out.println("You are moving with " + card);
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }

    }
    public static void beaten(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        BeatenCardsRequest request = new BeatenCardsRequest(gameId);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();

        if (response instanceof BeatenCardsResponse) {
            System.out.println("Table is clear");
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static Object takeFromTable(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        TakeCardsFromTableRequest request = new TakeCardsFromTableRequest(gameId);
        outputStream.reset();
        outputStream.writeObject(request);
        Object response = inputStream.readObject();

        if (response instanceof TakeCardsFromTableResponse) {
            System.out.println("Cards added in hand");
            return response;
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return null;
    }

}
