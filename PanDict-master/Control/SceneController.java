package Control;

import javafx.event.ActionEvent;

public class SceneController {


    public void searchButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.searchTabId);
    }

    public void addButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.addTabId);
    }

    public void apiButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.apiTabId);
    }

    public void favoriteButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.favoriteTabId);
    }

    public void gameButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.gameTabId);
    }

    public void guideButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.guideTabId);
    }

    public void historyButton(ActionEvent e) {
        HistoryController.getInstance().reload();
        Controller.getInstance().switchTab(Controller.historyTabId);
    }

    public void informationButton(ActionEvent e) {
        Controller.getInstance().switchTab(Controller.informationTabId);
    }





}
