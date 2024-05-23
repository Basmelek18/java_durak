package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class RegistrationRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String login;
    private String name;

    public RegistrationRequest(String login, String name) {
        this.login = login;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }
}
