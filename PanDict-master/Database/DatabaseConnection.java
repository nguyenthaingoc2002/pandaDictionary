package Database;

import DictionaryMain.Word;

import java.sql.*;

import static DictionaryMain.Dictionary.listWord;

public class DatabaseConnection {
    public static void read() {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  * FROM words ORDER BY content ASC\n"
            );
            listWord.clear();
            boolean check;
            while (resultSet.next()) {
                if (resultSet.getInt("favorite") > 0) check = true;
                else check = false;
                listWord.add(new Word(resultSet.getString("content"),
                        resultSet.getInt("id"), check));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
