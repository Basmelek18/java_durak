package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class FirstMoveResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long userId;

    public FirstMoveResponse(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
