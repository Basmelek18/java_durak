package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long userId;
    private boolean userExist;

    public LoginResponse(long userId, boolean userExist) {
        this.userId = userId;
        this.userExist = userExist;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isUserExist() {
        return userExist;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserExist(boolean userExist) {
        this.userExist = userExist;
    }
}
