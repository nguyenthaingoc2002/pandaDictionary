package Database;

import DictionaryMain.Word;

import java.sql.*;
import java.util.ArrayList;

public class SearchData {

    public static String MakeType(int id) {
        if (id == 0) {
            return "";
        }
        if (id == 1) {
            return "Danh từ";
        }
        if (id == 2) {
            return "Tính từ";
        }
        if (id == 4) {
            return "Nội động từ";
        }
        if (id == 8) {
            return "Phó từ";
        }
        if (id == 16) {
            return "Ngoại động từ";
        }
        if (id == 32) {
            return "Mạo từ";
        }
        if (id == 64) {
            return "Giới từ";
        }
        if (id == 128) {
            return "Từ hạn định";
        }
        if (id == 256) {
            return "Đại từ";
        }
        if (id == 512) {
            return "Liên từ";
        }
        if (id == 1024) {
            return "Thán từ";
        }
        if (id == 2048) {
            return "Viết Tắt";
        }
        if (id == 4096) {
            return "Ngĩa bóng";
        }
        if (id == 8192) {
            return "Định ngữ";
        }
        return "Trợ động từ";
    }

    public static Word searchDatabase(int id) {

        try {
            //Class.forName("com.sqlite.jdbc.Driver");

            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM definitions\n" +
                            "WHERE word_ref = ?" +
                            "ORDER BY word_type ASC\n"

            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String s = "";
            String wordType = null;
            int count = 1;
            while (resultSet.next()) {
                String type = MakeType(resultSet.getInt("word_type"));
                if (wordType == null) {
                    if(type.equals("")) {
                        wordType = "-Không có định dạng loại từ:\n";
                    }
                    else {
                        wordType = type;
                        s = s + "-" + wordType + ":\n";
                    }
                }
                if (!wordType.equals(type) )
                {
                    if (!type.equals("")){
                        wordType = type;
                        s = s + "-" + wordType + "\n" + "1. " + resultSet.getString("definition") + "\n";
                        count = 2;
                    } else {
                        s = s + "-Không định dạng loại từ:\n" + "1. " +  resultSet.getString("definition") + "\n";
                        count = 2;
                    }
                } else {
                    s = s + count + ". " + resultSet.getString("definition") + "\n";
                    count++;
                }
            }
            PreparedStatement pronunciationStatement = connection.prepareStatement(
                    "SELECT *FROM words WHERE id = ?"
            );
            pronunciationStatement.setInt(1, id);
            resultSet = pronunciationStatement.executeQuery();
            String pronunciation = resultSet.getString("pronunciation");
            if (pronunciation.equals("")) pronunciation = "không có dữ liệu";
            if (s.equals("")) s = "không có dữ liệu";
            connection.close();
            return new Word(s, pronunciation);
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Word> searchDataWord(String word) {

        try {
            //Class.forName("com.sqlite.jdbc.Driver");

            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM [words]\n" +
                            "WHERE [content] LIKE ?\n" +
                            "ORDER BY [content] ASC"
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

    public static void update(int id, String explain) {
        try {
            //Class.forName("com.sqlite.jdbc.Driver");

            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT id FROM definitions\n" +
                            "WHERE word_ref = ?"
            );
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM definitions\n" +
                            "WHERE word_ref = ?"
            );
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO definitions (id, definition, word_ref, word_type)\n" +
                            "VALUES (?, ?, ?, ?)"
            );
            insertStatement.setInt(1, resultSet.getInt("id"));
            insertStatement.setString(2, explain);
            insertStatement.setInt(3, id);
            insertStatement.setInt(4, 0);
            insertStatement.executeUpdate();
            // addExplain(id, explain, 0);
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public static void delete(int id) {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement deleteWordsStatement = connection.prepareStatement(
                    "DELETE FROM [words]\n" +
                            "WHERE id = ?"
            );
            deleteWordsStatement.setInt(1, id);
            deleteWordsStatement.executeUpdate();
            PreparedStatement deleteDefinitionsStatement = connection.prepareStatement(
                    "DELETE FROM definitions\n" +
                            "WHERE word_ref = ?"
            );
            deleteDefinitionsStatement.setInt(1, id);
            deleteDefinitionsStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addFavorite(int id) {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(favorite) AS favorite FROM words"
            );
            PreparedStatement addStatement = connection.prepareStatement(
                    "UPDATE words SET favorite = ?" +
                            "WHERE id = ?"
            );
            int count = resultSet.getInt("favorite");
            addStatement.setInt(1, count + 1);
            addStatement.setInt(2, id);
            addStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addHistory(int id) {
        try {
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(history) AS history FROM words"
            );
            PreparedStatement addStatement = connection.prepareStatement(
                    "UPDATE words SET history = ?" +
                            "WHERE id = ?"
            );
            int count = resultSet.getInt("history");
            addStatement.setInt(1, count + 1);
            addStatement.setInt(2, id);
            addStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
