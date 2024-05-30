package org.durak.logic;

import org.durak.controller.dto.*;
import org.durak.model.Card;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestLogic {
    private static Object requestResponse(ObjectOutputStream outputStream, ObjectInputStream inputStream, Object obj) throws IOException, ClassNotFoundException {
        outputStream.reset();
        outputStream.writeObject(obj);
        return inputStream.readObject();
    }

    public static List<String> cardsToStr(List<Card> cards) {
        List<String> result = new ArrayList<>();
        for (Card card: cards) {
            result.add(card.toString());
        }
        return result;
    }

    public static Map<String, String> tableToStr(Map<Card, Card> table) {
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<Card, Card> entry : table.entrySet())
            if (entry.getValue() == null) {
                result.put(entry.getKey().toString(), null);
            } else {
                result.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return result;
    }


    public static Object login(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn = new BufferedReader(new InputStreamReader(System.in));
        String value = valIn.readLine();
        LoginRequest request = new LoginRequest(value);

        Object response = requestResponse(outputStream, inputStream, request);

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
        RegistrationRequest request = new RegistrationRequest(value1, value2);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof RegistrationResponse) {
            System.out.println("User successfully registered");
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static void createGame(ObjectOutputStream outputStream, ObjectInputStream inputStream, long userId) throws IOException, ClassNotFoundException {
        CreateGameRequest request = new CreateGameRequest(userId);

        Object response = requestResponse(outputStream, inputStream, request);

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
        JoinGameRequest request = new JoinGameRequest(userId, gameId);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof JoinGameResponse joinGameResponse) {
            System.out.println("Game successfully has been joined " + joinGameResponse.getGameId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static void getCardsFromDeck(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        CardsRequest request = new CardsRequest(gameId);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof CardsResponse cardsResponse) {
            if (cardsResponse.getTrump() == null) {
                System.out.println("Cards deck is empty");
            } else {
                System.out.println("Cards in a deck " + cardsResponse.getAmount() +
                        ". Trump is " + ((CardsResponse) response).getTrump().toString());
            }
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static Object takeCardsFromDeck(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand) throws IOException, ClassNotFoundException {
        TakeCardsFromListRequest request = new TakeCardsFromListRequest(gameId, userId, hand);
        System.out.println(hand);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof TakeCardsFromListResponse takeCardsFromListResponse) {
            System.out.println("You got " + cardsToStr(takeCardsFromListResponse.getCards()));
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static List<Card> getTable(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        CardsOnTableRequest request = new CardsOnTableRequest(gameId);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof CardsOnTableResponse cardsOnTableResponse) {
            System.out.println("You got " + tableToStr(cardsOnTableResponse.getTable().get(gameId)));
            List<Card> cards = new ArrayList<>();
            for (Map.Entry<Card, Card> entry : ((CardsOnTableResponse) response).getTable().get(request.getGameId()).entrySet()) {
                if (entry.getKey() != null) {
                    cards.add(entry.getKey());
                }
                if (entry.getValue() != null) {
                    cards.add(entry.getKey());
                }
                cards.add(entry.getValue());
            }
            return cards;
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return null;
    }

    public static void move(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand, List<Card> table) throws IOException, ClassNotFoundException {
        boolean cardFlag = false;
        System.out.println("Which card?");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        int cardIndex = Integer.parseInt(valIn1.readLine());
        if (cardIndex <= 0 || cardIndex > hand.size()) {
            System.out.println("End of move");
        } else {
            Card card = hand.get(cardIndex - 1);
            if (table.isEmpty()) {
                cardFlag = true;
            } else {
                for (Card tableCard : table) {
                    if (tableCard != null && card.getValue() == tableCard.getValue()) {
                        cardFlag = true;
                        break;
                    }
                }
            }
            if (cardFlag) {
                hand.remove(card);
                MoveRequest request = new MoveRequest(gameId, userId, card);
                Object response = requestResponse(outputStream, inputStream, request);
                System.out.println(response);

                if (response instanceof MoveResponse) {
                    System.out.println("You are moving with " + card);
                } else {
                    System.out.println("Unexpected response type: " + response.getClass().getName());
                }
            } else {
                System.out.println("You cannot use this card, chose another");
                move(outputStream, inputStream, gameId, userId, hand, table);
            }
        }
    }

    public static void beat(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId, long userId, List<Card> hand) throws IOException, ClassNotFoundException {
        System.out.println("Which card?");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        Card card = hand.get(Integer.parseInt(valIn1.readLine()) - 1);
        hand.remove(card);

        BeatCardRequest request = new BeatCardRequest(gameId, userId, card);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof BeatCardResponse) {
            System.out.println("You are moving with " + card);
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }

    }
    public static void beaten(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        BeatenCardsRequest request = new BeatenCardsRequest(gameId);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof BeatenCardsResponse) {
            System.out.println("Table is clear");
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }

    public static Object takeFromTable(ObjectOutputStream outputStream, ObjectInputStream inputStream, long gameId) throws IOException, ClassNotFoundException {
        TakeCardsFromTableRequest request = new TakeCardsFromTableRequest(gameId);

        Object response = requestResponse(outputStream, inputStream, request);

        if (response instanceof TakeCardsFromTableResponse) {
            System.out.println("Cards added in hand");
            return response;
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return null;
    }

}
