package org.durak.model;

public class User {
    private int id;
    private String login;
    private String name;
    private int score;

    public User(int id, String login, String name, int score){
        this.id= id;
        this.login = login;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
