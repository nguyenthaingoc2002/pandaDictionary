package Database;

import DictionaryMain.Word;

import java.sql.*;
import java.util.ArrayList;

import static DictionaryMain.Dictionary.listHistory;

public class HistoryData {
    public static void readHistory() {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  * FROM words \n" +
                            "WHERE history <> 0\n" +
                            "ORDER BY history DESC"
            );
            listHistory.clear();
            boolean check = false;
            while (resultSet.next()) {
                if (resultSet.getInt("favorite") > 0) check = true;
                else check = false;
                listHistory.add(new Word(resultSet.getString("content"),
                        resultSet.getInt("id"), check));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Word> searchDataWord(String word) {

        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM [words]\n" +
                            "WHERE [content] LIKE ?\n AND history <> 0\n" +
                            "ORDER BY content ASC"
            );
            preparedStatement.setString(1, word + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Word> s = new ArrayList<>();
            boolean check = false;
            while (resultSet.next()) {
                if (resultSet.getInt("favorite") > 0) check = true;
                else check = false;
                s.add(new Word(resultSet.getString("content"),
                        resultSet.getInt("id"), check));

            }
            connection.close();
            return s;
        } catch (SQLException e) {
            return null;
        }
    }
}
