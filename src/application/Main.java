package application;

import controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController mainController = new MainController(primaryStage);
        mainController.showStartView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
