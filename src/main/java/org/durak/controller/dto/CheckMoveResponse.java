package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CheckMoveResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private boolean approvedMove;

    public CheckMoveResponse(boolean approvedMove) {
        this.approvedMove = approvedMove;
    }

    public boolean isApprovedMove() {
        return approvedMove;
    }

    public void setApprovedMove(boolean approvedMove) {
        this.approvedMove = approvedMove;
    }
}
