package org.durak.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import org.durak.controller.dto.LoginRequest;
import org.durak.logic.GameLogic;
import org.durak.model.Card;
import org.durak.repository.GetCards;

public class Server {
    private static Dotenv dotenv = Dotenv.load();
    private static List<ClientHandler> clients = new ArrayList<>();
    private static List<Card> cards = new ArrayList<>();
    private LoginController loginController = new LoginController();

    public Server() throws SQLException {
        cards = GetCards.getCards();
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Client connected: " + clientSocket);
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                Object clientRequest = inputStream.readObject();
                Object response = null;
                if (clientRequest instanceof LoginRequest) {
                    response = loginController.login((LoginRequest) clientRequest);
                }
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(response);

            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static synchronized void playerOne(String message, ClientHandler sender) {
        ClientHandler player = clients.get(0);
        player.sendMessage(message);
    }

    public static synchronized void playerTwo(String message, ClientHandler sender) {
        ClientHandler player = clients.get(1);
        player.sendMessage(message);
    }


    public static void main(String[] args) throws SQLException {
        Server server = new Server();
        server.start(Integer.parseInt(dotenv.get("PORT")));
    }

}
