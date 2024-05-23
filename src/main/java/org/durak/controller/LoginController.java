package org.durak.controller;

import org.durak.controller.dto.LoginRequest;
import org.durak.controller.dto.LoginResponse;
import org.durak.model.User;
import org.durak.repository.SaveUser;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginController {
    private Random random = new Random();
    private Map<Long, String> loggedUsers = new HashMap<>();

    public LoginResponse login(LoginRequest request) throws SQLException {
        String login = request.getLogin();
        if (!SaveUser.existingUser(login)) {
            return new LoginResponse(-1, false);
        }
        Long id = random.nextLong();
        loggedUsers.put(id, login);
        return new LoginResponse(id, true);
    }
}
