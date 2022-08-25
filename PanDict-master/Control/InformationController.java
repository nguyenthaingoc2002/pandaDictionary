package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    public void backButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.menuTabId);
    }
    @FXML
    TextArea informationArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String text = "# Sản phẩm được tạo bởi nhóm 10 gồm \n" +
                "3 thành viên :\n" +
                "- Nguyễn Minh Ngọc\n" +
                "_ Nguyễn Thái Ngọc\n" +
                "- Lê Hoàng Nam Quân.";
        informationArea.setText(text);
    }
}
