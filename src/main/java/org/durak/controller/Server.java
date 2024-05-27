package org.durak.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.durak.controller.dto.*;
import org.durak.model.Card;
import org.durak.repository.GetCards;

public class Server {
    private static Dotenv dotenv = Dotenv.load();
    private static List<ClientHandler> clients = new ArrayList<>();
    private static List<Card> cards = new ArrayList<>();
    private Map<Long, List<Long>> allGamesWithPlayers = Collections.synchronizedMap(new HashMap<>());
    private Map<Long, List<Card>> cardsInDeckMap = Collections.synchronizedMap(new HashMap<>());

    public Server() throws SQLException {
        cards = GetCards.getCards();
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Client connected: " + clientSocket);
                new Thread(new ClientHandler(clientSocket, allGamesWithPlayers, cardsInDeckMap)).start();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        }
    }



//    public static synchronized void broadcastMessage(String message, ClientHandler sender) {
//        for (ClientHandler client : clients) {
//            if (client != sender) {
//                client.sendMessage(message);
//            }
//        }
//    }
//
//    public static synchronized void playerOne(String message, ClientHandler sender) {
//        ClientHandler player = clients.get(0);
//        player.sendMessage(message);
//    }
//
//    public static synchronized void playerTwo(String message, ClientHandler sender) {
//        ClientHandler player = clients.get(1);
//        player.sendMessage(message);
//    }


    public static void main(String[] args) throws SQLException {
        Server server = new Server();
        server.start(Integer.parseInt(dotenv.get("PORT")));
    }

}
