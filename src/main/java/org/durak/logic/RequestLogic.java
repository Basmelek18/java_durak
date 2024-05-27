package org.durak.logic;

import org.durak.controller.dto.LoginRequest;
import org.durak.controller.dto.LoginResponse;
import org.durak.controller.dto.RegistrationRequest;
import org.durak.controller.dto.RegistrationResponse;

import java.io.*;
import java.net.Socket;

public class RequestLogic {

    public static Object login(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn = new BufferedReader(new InputStreamReader(System.in));
        String value = valIn.readLine();
        LoginRequest loginRequest = new LoginRequest(value);
        outputStream.writeObject(loginRequest);

        Object response = inputStream.readObject();

        if (response instanceof LoginResponse loginResponse) {
            System.out.println("Login successful: " + loginResponse.getUserId());
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
        return response;
    }

    public static void registration(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Your login");
        BufferedReader valIn1 = new BufferedReader(new InputStreamReader(System.in));
        String value1 = valIn1.readLine();
        System.out.println("Your name");
        BufferedReader valIn2 = new BufferedReader(new InputStreamReader(System.in));
        String value2 = valIn2.readLine();
        RegistrationRequest registrationRequest = new RegistrationRequest(value1, value2);
        outputStream.writeObject(registrationRequest);

        Object response = inputStream.readObject();

        if (response instanceof RegistrationResponse) {
            System.out.println("Login successfully registered");
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
        }
    }


}
