package org.durak.controller.dto;

import java.io.Serial;
import java.io.Serializable;

public class CreateGameRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long idPlayer;

    public CreateGameRequest(long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
