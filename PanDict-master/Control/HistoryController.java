package Control;

import Database.HistoryData;
import Database.SearchData;
import Database.SearchMethod;
import DictionaryMain.TTS;
import DictionaryMain.Word;
import DictionaryMain.WordListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static DictionaryMain.Dictionary.listHistory;

public class HistoryController implements Initializable {
    private static HistoryController instance;
    @FXML
    TextField searchTextField;
    @FXML
    ListView<Word> historyListView;
    @FXML
    TextArea pronunciationArea;
    @FXML
    TextArea explainArea;

    public ArrayList<Word> s = new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        historyListView.setCellFactory((l) -> {
            return new WordListCell();
        });
        HistoryData.readHistory();
        for (Word p : listHistory) {
            historyListView.getItems().add(p);
        }
        historyListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word>() {
            @Override
            public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                int id = (historyListView.getSelectionModel().getSelectedItem()).getId();
                Word word = SearchData.searchDatabase(id);
                explainArea.setText(word.getWord_target());
                pronunciationArea.setText(word.getWord_explain());
            }
        });

    }

    public static HistoryController getInstance() {
        return instance;
    }

    public void reload() {
        historyListView.setCellFactory((l) -> {
            return new WordListCell();
        });
        if (!historyListView.getItems().isEmpty()) historyListView.getItems().clear();
        HistoryData.readHistory();
        for (Word p : listHistory) {
            historyListView.getItems().add(p);
        }
    }

    public void readWord() {
        String text = searchTextField.getCharacters().toString();
        if (!s.isEmpty()) s.clear();
        if (text != null) {
            s = HistoryData.searchDataWord(text);
            if (!historyListView.getItems().isEmpty())
                historyListView.getItems().clear();
            if (!s.isEmpty()) {
                for (Word word : s) {
                    historyListView.getItems().add(word);
                }
            } else {
                explainArea.setText("Không có trong dữ liệu");
            }
        }

    }

    public void searchButton(ActionEvent e) throws IOException {
        if (!s.isEmpty()) {
            int id = s.get(0).getId();
            Word word = SearchData.searchDatabase(id);
            explainArea.setText(word.getWord_target());
            pronunciationArea.setText(word.getWord_explain());
        } else {
            String word = searchTextField.getText();
            if (!listHistory.isEmpty()) {
                for (Word p : listHistory) {
                    if (SearchMethod.calculate(word, p.getWord_target()) < 2) {
                        historyListView.getItems().add(p);
                    }
                }
            }
        }

    }

    public void voiceButton(ActionEvent e) {
        String word = "";
        if ( historyListView.getSelectionModel().getSelectedItem() != null)
            word = historyListView.getSelectionModel().getSelectedItem().getWord_target();
        if (word.equals("") && !searchTextField.getText().equals(""))
            word = searchTextField.getText();
        TTS voice = new TTS("kevin16");
        voice.say(word);
    }

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }
}
