package org.durak.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public static void main(String[] args) {
        try (Connection connection = PostgreSQLConnection.getConnection()) {
            // SQL-запрос для вставки данных
            String insertSQL = "INSERT INTO cards (rank, suit) VALUES (?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                // Вставка нескольких записей
                for (int i=0; i<4; i++) {
                    for (int j=6; j<15; j++) {
                        pstmt.setInt(1, j);
                        pstmt.setInt(2, i);
                        pstmt.executeUpdate();
                    }
                }

                System.out.println("Data inserted successfully into 'cards'.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
