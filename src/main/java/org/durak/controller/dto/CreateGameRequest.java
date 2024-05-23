package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CreateGameRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int idPlayer;

    public CreateGameRequest(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
