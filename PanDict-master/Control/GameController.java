package Control;

import DictionaryMain.Game;
import DictionaryMain.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class GameController {
    @FXML
    TextArea questionArea;
    @FXML
    TextArea answerArea;
    @FXML
    TextArea noticeArea;
    Word word;

    public void Change (ActionEvent e) {
        word = Game.Take();
        questionArea.setText(word.getWord_target());
    }
    public void Submit (ActionEvent e) {
        if (word.getWord_explain().equals(answerArea.getText())) {
            noticeArea.setText("Congratulation!");
        } else {
            noticeArea.setText(
                    "Correct Word:\n        " + word.getWord_explain()
            );
        }
        answerArea.setText("");
    }

    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }

}
