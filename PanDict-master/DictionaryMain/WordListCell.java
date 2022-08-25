package DictionaryMain;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class WordListCell extends ListCell<Word> {
    HBox hbox = new HBox();
    Label label = new Label();
    Image image1 = new Image("/Style/blackStar.png");
    Image image2 = new Image("/Style/yellowStar.png");
    ImageView imageView = new ImageView();

    public WordListCell() {
        super();
        hbox.getChildren().addAll(imageView, label);
        hbox.setSpacing(5);
    }

    @Override
    protected void updateItem(Word word, boolean empty) {
        super.updateItem(word, empty);

        if (empty || word == null) {
            setText(null);
            setGraphic(null);
        } else {
            label.setText(word.getWord_target());
            if (word.isCheck()) imageView.setImage(image2);
            else imageView.setImage(image1);
            setGraphic(hbox);
        }
    }

}


