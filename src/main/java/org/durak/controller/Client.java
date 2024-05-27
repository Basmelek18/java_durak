package org.durak.controller;

import org.durak.logic.RequestLogic;

import java.io.*;
import java.net.*;

public class Client {
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