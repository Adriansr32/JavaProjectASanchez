package application;

import controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for the JavaFX application.
 * Initializes and launches the main controller.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and shows the start view.
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        MainController mainController = new MainController(primaryStage);
        mainController.showStartView();
    }

    /**
     * Launches the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
