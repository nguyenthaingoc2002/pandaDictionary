package Control;

import Database.DatabaseConnection;
import Database.FavoriteData;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static DictionaryMain.Dictionary.listFavorite;

public class FavoriteController  implements Initializable{

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }
    public ArrayList<Word> s = new ArrayList<>();
    private static FavoriteController instance;
    @FXML
    ListView<Word> listViewFavorite;
    @FXML
    TextArea textAreaFavorite;
    @FXML
    TextField searchTextField;
    @FXML
    TextArea pronunciationArea;

    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        listViewFavorite.setCellFactory((l) -> {
            return new WordListCell();
        });
        FavoriteData.readFavorite();
        for (Word p : listFavorite) {
            listViewFavorite.getItems().add(p);
        }
        if (listViewFavorite.getItems().isEmpty()) return;
        listViewFavorite.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word>() {
            @Override
            public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                if (listViewFavorite.getItems().isEmpty()) return;
                int id = (listViewFavorite.getSelectionModel().getSelectedItem()).getId();
                Word word = SearchData.searchDatabase(id);
                textAreaFavorite.setText(word.getWord_target());
                pronunciationArea.setText(word.getWord_explain());
            }
        });

    }

    public static FavoriteController getInstance() {
        return instance;
    }

    public void reload() {
        listViewFavorite.setCellFactory((l) -> {
            return new WordListCell();
        });
        FavoriteData.readFavorite();
        listViewFavorite.getItems().clear();
        for (Word p : listFavorite) {
            listViewFavorite.getItems().add(p);
        }
    }

    public void readWord() {
        String text = searchTextField.getCharacters().toString();
        if (!s.isEmpty()) s.clear();
        if (text != null) {
            s = FavoriteData.searchDataWord(text);
            if (!listViewFavorite.getItems().isEmpty())
                listViewFavorite.getItems().clear();
            if (!s.isEmpty()) {
                for (Word word : s) {
                    listViewFavorite.getItems().add(word);
                }
            } else {
                textAreaFavorite.setText("Không có trong dữ liệu");
            }
        }

    }

    public void searchButton(ActionEvent e) throws IOException {
        if (!s.isEmpty()) {
            int id = s.get(0).getId();
            Word word = SearchData.searchDatabase(id);
            textAreaFavorite.setText(word.getWord_target());
            pronunciationArea.setText(word.getWord_explain());
        } else {
            String word = searchTextField.getText();
            if (!listFavorite.isEmpty()) {
                for (Word p : listFavorite) {
                    if (SearchMethod.calculate(word, p.getWord_target()) < 2) {
                        listViewFavorite.getItems().add(p);
                    }
                }
            }
        }

    }

    public void voiceButton(ActionEvent e) {
        String word = "";
        if (listViewFavorite.getSelectionModel().getSelectedItem() != null)
            word = listViewFavorite.getSelectionModel().getSelectedItem().getWord_target();
        if (word == "" && !searchTextField.getText().equals(""))
            word = searchTextField.getText();
        TTS voice = new TTS("kevin16");
        voice.say(word);
    }

    public void deleteButton(ActionEvent e) {
        int id = listViewFavorite.getSelectionModel().getSelectedItem().getId();
        FavoriteData.deleteFavorite(id);
        listViewFavorite.getItems().remove(listViewFavorite.getSelectionModel().getSelectedItem());
        SearchController.getInstance().reload();
    }


}
