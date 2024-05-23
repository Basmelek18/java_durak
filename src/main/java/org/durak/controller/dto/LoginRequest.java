package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String login;

    public LoginRequest(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
