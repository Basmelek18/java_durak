package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class RegistrationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private boolean userExist;

    public RegistrationResponse(boolean userExist) {
        this.userExist = userExist;
    }

    public boolean isUserExist() {
        return userExist;
    }

    public void setUserExist(boolean userExist) {
        this.userExist = userExist;
    }
}
