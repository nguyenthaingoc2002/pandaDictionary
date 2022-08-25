package DictionaryMain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static DictionaryMain.Dictionary.listHistory;

public class Game {
    public static Word Take() {
        int n = listHistory.size() - 1;
        int ranNum = ThreadLocalRandom.current().nextInt(0, n);
        String word = listHistory.get(ranNum).getWord_target();
        int a1 = ThreadLocalRandom.current().nextInt(0, word.length());
        int b1 = ThreadLocalRandom.current().nextInt(0, word.length());
        int a2 = ThreadLocalRandom.current().nextInt(0, word.length());
        int b2 = ThreadLocalRandom.current().nextInt(0, word.length());
        int a3 = ThreadLocalRandom.current().nextInt(0, word.length());
        int b3 = ThreadLocalRandom.current().nextInt(0, word.length());
        String ds = "";
        char[] newString = new char[word.length()];
        for (int i = 0; i < word.length(); ++i) {
            newString[i] = word.charAt(i);
        }
        char x = newString[a1];
        newString[a1] = newString[b1];
        newString[b1] = x;
        x = newString[a2];
        newString[a2] = newString[b2];
        newString[b2] = x;
        x = newString[a3];
        newString[a3] = newString[b3];
        newString[b3] = x;
        for (int i = 0; i < word.length(); ++i)
            ds += newString[i];

        return new Word(ds, word);


    }
}
