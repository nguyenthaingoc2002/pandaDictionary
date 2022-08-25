package Control;

import Database.AddWordData;
import Database.DatabaseConnection;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutRight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddController {
    @FXML
    TextField wordTextField;
    @FXML
    TextField pronunciationTextField;
    @FXML
    TextField typeTextField;
    @FXML
    TextArea explainArea;
    @FXML
    BorderPane addSuccessBorder;

    public void applyButton(ActionEvent e) throws IOException {
        String target = wordTextField.getText();
        String pronunciation = pronunciationTextField.getText();
        String type = typeTextField.getText();
        String explain = explainArea.getText();
        boolean check = AddWordData.addWord(target, explain, pronunciation, type);
        if (target.equals("") || explain.equals("") || check == false) {
            Pane pane = FXMLLoader.load(getClass().getResource("../FXML/AddNotSuccess.fxml"));
            addSuccessBorder.setCenter(pane);
            new SlideInRight(addSuccessBorder).play();
            new SlideOutRight(addSuccessBorder).setDelay(Duration.valueOf("3500ms")).play();
            return ;
        }
        Pane pane = FXMLLoader.load(getClass().getResource("../FXML/AddSuccess.fxml"));
        addSuccessBorder.setCenter(pane);
        new SlideInRight(addSuccessBorder).play();
        new SlideOutRight(addSuccessBorder).setDelay(Duration.valueOf("3500ms")).play();
        wordTextField.setText("");
        pronunciationTextField.setText("");
        typeTextField.setText("");
        explainArea.setText("");


    }

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }


}
