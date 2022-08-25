package DictionaryMain;

public class Word {
    private String word_target;
    private String word_explain;
    private int id;
    private boolean check;

    public Word(String word_target_, String word_explain_) {
        word_target = word_target_;
        word_explain = word_explain_;

    }

    public Word(String word_target, int id) {
        this.word_target = word_target;
        this.id = id;
    }

    public Word(String word_target, int id, boolean check) {
        this.word_target = word_target;
        this.id = id;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
