package Control;

import Database.DatabaseConnection;
import Database.FavoriteData;
import Database.SearchData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static Controller instance;
    public static String searchTabId = "searchTab";
    public static String menuTabId = "menuTab";
    public static String addTabId = "addTab";
    public static String apiTabId = "apiTab";
    public static String favoriteTabId = "favoriteTab";
    public static String guideTabId = "guideTab";
    public static String historyTabId = "historyTab";
    public static String informationTabId = "informationTab";
    public static String gameTabId = "gameTab";
    @FXML
    private AnchorPane root;
    @FXML
    private Pane loadingPane;

    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
    }

    public void openLoading() {
        loadingPane.visibleProperty().set(true);
    }

    public void closeLoading() {
        loadingPane.visibleProperty().set(false);
    }

    public static Controller getInstance() {
        return instance;
    }

    public void switchTab(String tabId) {

        for (Node node : root.getChildren()) {
            if (node.getId() != null && node.getId().equals(tabId))
                node.visibleProperty().set(true);
            else node.visibleProperty().set(false);
        }

    }

}
