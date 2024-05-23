package org.durak.model;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String login;
    private String name;
    private int score;

    public User(String name, String login, int score){
        this.name = name;
        this.login = login;
        this.score = score;
    }


    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
