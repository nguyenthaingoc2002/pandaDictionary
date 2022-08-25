package Database;

import java.sql.*;
import java.util.Locale;

public class AddWordData {
    public static int makeType(String type) {
        type = type.toUpperCase(Locale.ROOT);
        if(type.equals("NOUN")) return 1;
        if(type.equals("ADJECTIVE")) return 2;
        if(type.equals("VERB")) return 4;
        if(type.equals("ADVERB")) return 8;
        if(type.equals("TRANS_VERB")) return 16;
        if(type.equals("ARTICLE")) return 32;
        if(type.equals("PREPOSITION")) return 64;
        if(type.equals("DETERMINER")) return 128;
        if(type.equals("PRONOUN")) return 256;
        if(type.equals("CONJUNCTION")) return 512;
        if(type.equals("INTERJECTION")) return 1024;
        if(type.equals("ACRONYM")) return 2048;
        if(type.equals("FIGURATIVE")) return 4096;
        if(type.equals("DEFINITION")) return 8192;
        if(type.equals("AUXILIARY")) return 16384;
        return 0;
    }
    public static boolean addWord(String target, String explain, String pronunciation, String type) {
        try {
            //Class.forName("com.sqlite.jdbc.Driver");
            int id = makeType(type);
            String url = "jdbc:sqlite:src/Database/mimir.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement findStatement = connection.prepareStatement(
                    "SELECT * FROM words WHERE content = ?"
            );
            findStatement.setString(1, target);
            ResultSet resultSet = findStatement.executeQuery();
            int checkResultSet;
            if (!resultSet.next())  checkResultSet = 0;
            else  checkResultSet = resultSet.getInt("id");
            Statement statement = connection.createStatement();
            ResultSet idResultSet = statement.executeQuery(
                    "SELECT MAX(id) AS id FROM definitions"
            );
            PreparedStatement addDefinitionStatement = connection.prepareStatement(
                    "INSERT INTO definitions (id, definition, word_ref, word_type)" +
                            "VALUES (?, ?, ?, ?)"
            );
            if ( checkResultSet != 0) {
               return false;
            }
            else {
                Statement targetStatement = connection.createStatement();
                ResultSet targetId = targetStatement.executeQuery(
                        "SELECT MAX(id) AS id FROM words"
                );
                PreparedStatement addWordStatement = connection.prepareStatement(
                        "INSERT INTO words (id, content, pronunciation, favorite)" +
                                "VALUES (?, ?, ?, ?)"
                );
                addWordStatement.setInt(1, targetId.getInt("id") + 1);
                addWordStatement.setString(2, target);
                addWordStatement.setString(3, pronunciation);
                addWordStatement.setInt(4, 0);
                addDefinitionStatement.setInt(1, idResultSet.getInt("id") + 1);
                addDefinitionStatement.setString(2, explain);
                addDefinitionStatement.setInt(3, targetId.getInt("id") + 1);
                addDefinitionStatement.setInt(4, id);
                addDefinitionStatement.executeUpdate();
                addWordStatement.executeUpdate();
                connection.close();
                return true;
            }

        } catch (SQLException e) {
            return false;
        }
    }
}
