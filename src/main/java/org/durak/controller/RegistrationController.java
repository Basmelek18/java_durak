package org.durak.controller;

import org.durak.controller.dto.RegistrationRequest;
import org.durak.controller.dto.RegistrationResponse;
import org.durak.model.User;
import org.durak.repository.SaveUser;

import java.sql.SQLException;

public class RegistrationController {
    public RegistrationResponse registration (RegistrationRequest request) throws SQLException {
        User user = new User(request.getName(), request.getLogin(), 0);
        SaveUser.createUser(user);
        return new RegistrationResponse(true);
    }
}
