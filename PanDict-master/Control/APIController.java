package Control;

import Database.DatabaseConnection;
import Database.SearchData;
import DictionaryMain.GGAPI;
import DictionaryMain.Word;
import DictionaryMain.WordListCell;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutRight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class APIController implements Initializable {
    @FXML
    TextArea textAreaEnglish;
    @FXML
    TextArea textAreaVietnamese;
    @FXML
    ProgressBar progressBar;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    Pane loadingPane;
    @FXML
    BorderPane borderPane;

    public void initialize(URL location, ResourceBundle resources) {
        task = new doWork();
        closeLoaing();
    }
    boolean check = false;
    String word = "";
    public void EtoV(ActionEvent e) throws IOException {
        word = GGAPI.translate("en", "vi", textAreaEnglish.getText());
        if (word == null) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/InternetError.fxml"));
            borderPane.setCenter(pane);
            new SlideInRight(borderPane).play();
            new SlideOutRight(borderPane).setDelay(Duration.valueOf("3500ms")).play();
            return;

        }
        openLoading();


    }
    public void VtoE(ActionEvent e) throws IOException {
        word = GGAPI.translate("vi", "en", textAreaVietnamese.getText());
        if (word == null) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/InternetError.fxml"));
            borderPane.setCenter(pane);
            new SlideInRight(borderPane).play();
            new SlideOutRight(borderPane).setDelay(Duration.valueOf("3500ms")).play();
            return;
        }
        check = true;
        openLoading();
    }
    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }


    doWork task;
    public void openLoading() {
        loadingPane.visibleProperty().set(true);
        task = new doWork();
        progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

    }

    public void closeLoaing() {
        task.cancel();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        progressIndicator.progressProperty().unbind();
        progressIndicator.setProgress(0);
        loadingPane.visibleProperty().set(false);
    }

    class doWork extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            for (int i = 0; i < 10; ++i) {
                if (isCancelled()) {
                    updateMessage("Cancelled");
                    break;
                }
                updateProgress(i + 1, 10);
                updateMessage("loading");
                Thread.sleep(200);
            }
            if (progressBar.getProgress() == 1.0) {
                closeLoaing();
                if (check == false) textAreaVietnamese.setText(word);
                else textAreaEnglish.setText(word);
            }
            updateMessage("finished");
            return null;
        }
    }


}
