package org.durak.controller;

import org.durak.logic.RequestLogic;

import java.io.*;
import java.net.*;

public class SecondClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String serverAddress = "localhost"; // Адрес сервера
        final int serverPort = 8000; // Порт сервера

        BufferedReader ctrlIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while ((userInput = ctrlIn.readLine()) != null) {
                if (userInput.equals("l")) {
                    RequestLogic.login(clientSocket, outputStream, inputStream);
                } else if (userInput.equals("r")) {
                    RequestLogic.registration(clientSocket, outputStream, inputStream);
                }
            }

        }
    }
}

