package Control;

import Database.*;
import DictionaryMain.*;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutRight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchController extends Dictionary implements Initializable {
    public ArrayList<Word> s = new ArrayList<>();

    @FXML
    private TextField searchTf;

    @FXML
    private TextArea explainArea;

    @FXML
    private ListView<Word> listView;
    @FXML
    private TextArea pronunciationArea;
    @FXML
    private BorderPane borderPane;

    private static SearchController instance;

    public static SearchController getInstance() {
        return instance;
    }

    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        listView.setCellFactory((l) -> {
            return new WordListCell();
        });
        DatabaseConnection.read();
        for (Word p : listWord) {
            listView.getItems().add(p);
        }
        if (listView.getItems().isEmpty()) return;
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word>() {
            @Override
            public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    int id = (listView.getSelectionModel().getSelectedItem()).getId();
                    Word word = SearchData.searchDatabase(id);
                    explainArea.setText(word.getWord_target());
                    pronunciationArea.setText(word.getWord_explain());
                    SearchData.addHistory(id);
                }

            }
        });

    }

    public void readWord() {
        String text = searchTf.getCharacters().toString();
        if (!s.isEmpty()) s.clear();
        if (text == null) return;
        s = SearchData.searchDataWord(text);
        if (!listView.getItems().isEmpty()) listView.getItems().clear();
        if (!s.isEmpty()) {
            for (Word word : s) {
                listView.getItems().add(word);
            }
        }

    }

    public void reload() {
        listView.setCellFactory((l) -> {
            return new WordListCell();
        });
        listView.getItems().removeAll();
        DatabaseConnection.read();
        for (Word p : listWord) {
            listView.getItems().add(p);
        }
    }

    public void searchButton(ActionEvent e) throws IOException {
        if (!s.isEmpty()) {
            int id = s.get(0).getId();
            Word word = SearchData.searchDatabase(id);
            explainArea.setText(word.getWord_target());
            pronunciationArea.setText(word.getWord_explain());
            SearchData.addHistory(id);
        } else {
            String word = searchTf.getText();
            for (Word p : listWord) {
                if (SearchMethod.calculate(word, p.getWord_target()) < 2) {
                    listView.getItems().add(p);
                }
            }
        }

    }

    public void editButton(ActionEvent e) {
        int id = (listView.getSelectionModel().getSelectedItem()).getId();
        String word = explainArea.getText();
        SearchData.update(id, word);

    }

    public void addFavoriteButton(ActionEvent e) throws IOException {
        Word word = listView.getSelectionModel().getSelectedItem();
        if (word.isCheck()) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/AddError.fxml"));
            borderPane.setCenter(pane);
            new SlideInRight(borderPane).play();
            new SlideOutRight(borderPane).setDelay(Duration.valueOf("3500ms")).play();
            return;
        }
        int id = word.getId();
        SearchData.addFavorite(id);
        FavoriteController.getInstance().reload();
        int index = listView.getSelectionModel().getSelectedIndex();
        listView.getItems().set(index, new Word(word.getWord_target(), word.getId(), true));

    }

    public void deleteButton(ActionEvent e) {
        int id = (listView.getSelectionModel().getSelectedItem()).getId();
        SearchData.delete(id);
        listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
    }

    public void voiceButton(ActionEvent e) {
        String word = "";
        if (listView.getSelectionModel().getSelectedItem() != null)
            word = listView.getSelectionModel().getSelectedItem().getWord_target();
        if (word == "" && !searchTf.getText().equals(""))
            word = searchTf.getText();
        TTS voice = new TTS("kevin16");
        voice.say(word);
    }

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }
    public void synonymButton(ActionEvent e) throws Exception {
        String word = "";
        if (listView.getSelectionModel().getSelectedItem() != null)
            word = listView.getSelectionModel().getSelectedItem().getWord_target();
        if (word == "" && !searchTf.getText().equals(""))
            word = searchTf.getText();
        HttpURLConnectionExample http = new HttpURLConnectionExample();
        String result = http.searchSynonym(word);
        if (result == null) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/InternetError.fxml"));
            borderPane.setCenter(pane);
            new SlideInRight(borderPane).play();
            new SlideOutRight(borderPane).setDelay(Duration.valueOf("3500ms")).play();
            return;
        }
        explainArea.setText(result);
    }

    public void oppositeButton(ActionEvent e) throws Exception {
        String word = "";
        if (listView.getSelectionModel().getSelectedItem() != null)
            word = listView.getSelectionModel().getSelectedItem().getWord_target();
        if (word == "" && !searchTf.getText().equals(""))
            word = searchTf.getText();
        HttpURLConnectionExample http = new HttpURLConnectionExample();
        String result = http.searchOpposite(word);
        if (result == null) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/InternetError.fxml"));
            borderPane.setCenter(pane);
            new SlideInRight(borderPane).play();
            new SlideOutRight(borderPane).setDelay(Duration.valueOf("3500ms")).play();
            return;
        }
        explainArea.setText(result);
    }

}

