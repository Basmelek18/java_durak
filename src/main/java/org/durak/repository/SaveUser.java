package org.durak.repository;

import org.durak.model.User;

import java.sql.*;

public class SaveUser {

    public static User getUser(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement preparedStatement = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String userLogin = resultSet.getString("login");
                    int score = resultSet.getInt("score");
                    // Получение других полей при необходимости

                    return new User(name, userLogin, score);
                }
            }
        }
        return null;
    }

    public static boolean existingUser(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement preparedStatement = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement .setString(1, login);
            try (ResultSet resultSet = preparedStatement .executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public static void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, login, score) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement  = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setInt(3, user.getScore());
            preparedStatement.executeUpdate();
        }
    }
}
