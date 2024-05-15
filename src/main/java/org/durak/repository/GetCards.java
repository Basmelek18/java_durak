package org.durak.repository;

import org.durak.model.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetCards {
    public static List<Card> getCards() throws SQLException {
        String query = "SELECT * FROM cards";
        List<Card> result = new ArrayList<>();
        try (Statement statement = PostgreSQLConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()){
                int rank = resultSet.getInt("rank");
                int suit = resultSet.getInt("suit");
                result.add(new Card(rank, suit));
            }
            Collections.shuffle(result);
        }
        return result;
    }
}
