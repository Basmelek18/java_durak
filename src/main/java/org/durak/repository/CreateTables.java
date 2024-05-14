package org.durak.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
    public static void main(String[] args) {
        try (Connection connection = PostgreSQLConnection.getConnection();
             Statement stmt = connection.createStatement()) {

            // SQL-request for creating table of Users
            String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "login VARCHAR(100), " +
                    "score INT)";

            stmt.executeUpdate(createUsersTableSQL);
            System.out.println("Table 'Users' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = PostgreSQLConnection.getConnection();
             Statement stmt = connection.createStatement()) {

            // SQL-request for creating table of Cards
            String createCardsTableSQL = "CREATE TABLE IF NOT EXISTS cards (" +
                    "id SERIAL PRIMARY KEY, " +
                    "rank INT, " +
                    "suit INT)";

            stmt.executeUpdate(createCardsTableSQL);
            System.out.println("Table 'Cards' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
