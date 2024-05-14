package org.durak.controller;

import org.durak.repository.SaveUser;

import java.io.*;
import java.net.*;

public class SecondClient {
    public static void main(String[] args) {
        final String serverAddress = "localhost"; // Адрес сервера
        final int serverPort = 8000; // Порт сервера

        try (
                Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // Отправка данных на сервер
                String serverAnswer = in.readLine();
                System.out.println("От сервера: " + serverAnswer); // Вывод ответа от сервера
                if (serverAnswer.startsWith("You are ")) {
                    String myLogin = serverAnswer;
                    int lastIndex = myLogin.lastIndexOf(" "); // Находим индекс последнего пробела
                    myLogin = myLogin.substring(lastIndex + 1);
                    System.out.println("My login " + myLogin);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Не удалось определить хост: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода для соединения с " + serverAddress);
        }
    }
}

