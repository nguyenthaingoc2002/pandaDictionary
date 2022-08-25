package DictionaryMain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class HttpURLConnectionExample {
    private final String USER_AGENT = "Mozilla/5.0";

    private static HttpURLConnectionExample instance;

    public static HttpURLConnectionExample getInstance() {
        return instance;
    }

    public String searchSynonym(String wordToSearch) throws Exception {
        String url = "https://api.datamuse.com/words?rel_trg=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        StringBuilder response;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );

           String s = "";
            if(words.size() > 0) {
                for(Word word : words) {
                    s = s + (words.indexOf(word) + 1) + ". " + word.getWord() + "\n";
                }
                return s;
            }
            else {
               return "Không có từ đồng nghĩa!";
            }
        }
        catch (IOException e) {
            return null;
        }
    }

    public String searchOpposite(String wordToSearch) throws Exception {
        String url = "https://api.datamuse.com/words?rel_ant=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );
            String s = "";
            if(words.size() > 0) {
                for(Word word : words) {
                    s += (words.indexOf(word) + 1) + ". " + word.getWord() + "\n";
                }
            }
            else {
                s = "Không có từ trái nghĩa!";
            }
            return s;
        }
        catch (IOException e) {
            return null;
        }
    }

    // word and score attributes are from DataMuse API
    static class Word {
        private String word;
        private int score;

        public String getWord() {return this.word;}
        public int getScore() {return this.score;}
    }
}
