package org.durak.repository;

import org.durak.model.User;

import java.sql.*;

public class SaveUser {

    public static boolean existingUser(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement statement = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
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
        try (PreparedStatement statement = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setInt(3, user.getScore());
            statement.executeUpdate();
        }
    }
}
