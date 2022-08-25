package Database;

import DictionaryMain.Word;

import java.sql.*;
import java.util.ArrayList;

import static DictionaryMain.Dictionary.listFavorite;

public class FavoriteData {

    public static void readFavorite() {
        try {
            //Class.forName("com.sqlite.jdbc.Driver");
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  * FROM words \n" +
                            "WHERE favorite <> 0\n" +
                            "ORDER BY content ASC"
            );
            listFavorite.clear();
            while (resultSet.next()) {
                listFavorite.add(new Word(resultSet.getString("content"),
                        resultSet.getInt("id"), true));
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
                            "WHERE [content] LIKE ?\n AND favorite <> 0\n" +
                            "ORDER BY content ASC"
            );
            preparedStatement.setString(1, word + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Word> s = new ArrayList<>();
            while (resultSet.next()) {
                s.add(new Word(resultSet.getString("content"),
                        resultSet.getInt("id"), true));

            }
            connection.close();
            return s;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void deleteFavorite(int id) {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "UPDATE words SET favorite = 0\n" +
                            "WHERE id = ?"
            );
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
