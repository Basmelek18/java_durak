package org.durak.repository;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class PostgreSQLConnection {
    private static Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("POSTGRES_USER");
        String dbPassword = dotenv.get("POSTGRES_PASSWORD");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
