package org.durak.logic;

import org.durak.controller.dto.LoginRequest;
import org.durak.controller.dto.LoginResponse;

import java.io.*;
import java.net.Socket;

public class RequestLogic {

    public static Object login(Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn = new BufferedReader(new InputStreamReader(System.in));
        String value = valIn.readLine();
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        LoginRequest loginRequest = new LoginRequest(value);
        outputStream.writeObject(loginRequest);

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Object response = inputStream.readObject();

        if (response instanceof LoginResponse loginResponse) {
            System.out.println("Login successful: " + loginResponse.getUserId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }


}
