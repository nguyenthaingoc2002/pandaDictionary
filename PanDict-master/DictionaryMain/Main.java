package DictionaryMain;

import animatefx.animation.FadeIn;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int COUNT_LIMIT = 100;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/MainSceneTab.fxml"));
        primaryStage.setTitle("JavaFX Dictionary");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress =(double) i/100;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(50);
        }
    }


    public static void main(String[] args) {

        LauncherImpl.launchApplication(Main.class, preloader.class, args);
    }
}
