package Control;

import Database.DatabaseConnection;
import Database.FavoriteData;
import Database.HistoryData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Quynhbes on 10/29/2021.
 */
public class PreloaderController implements Initializable {
    @FXML
    private Label labelProgress;

    public static Label label;


    @FXML
    private ProgressBar progressBar;

    public static ProgressBar statProgressBar;

    @FXML
    private void handleButtonAction(ActionEvent event) {


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label = labelProgress ;
        statProgressBar = progressBar;
    }

}
