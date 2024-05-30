package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CheckFirstResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public CheckFirstResponse(long userId) {
        this.userId = userId;
    }
}
