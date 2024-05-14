package org.durak.controller;

import org.durak.model.User;
import org.durak.repository.SaveUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler extends Thread implements Runnable{
    private Socket clientSocket;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            String empty = in.readLine();
            out.println("Your login:");
            String login = in.readLine();
            if (!SaveUser.existingUser(login)) {
                out.println("Your name:");
                String name = in.readLine();
                User newUser = new User(name, login, 0);
                out.println(newUser);
                SaveUser.createUser(newUser);
            }
            out.println("You are " + login);
            // Чтение сообщения от клиента и отправка ответа
            while ((inputLine = in.readLine()) != null) {
                System.out.println("От клиента: " + inputLine);
                // Передача сообщения всем остальным клиентам
                Server.broadcastMessage(inputLine, this);
                out.flush();

            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close(); // Закрытие соединения с клиентом
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
