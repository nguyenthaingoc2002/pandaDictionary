package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GuideController implements Initializable {
    @FXML
    TextArea guideArea;
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File dic = new File("src/Text/GuideText");
            Scanner scanner = new Scanner(dic);
            String text = "";
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                text = text + word + "\n";
            }
            scanner.close();
            guideArea.setText(text);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }

}
