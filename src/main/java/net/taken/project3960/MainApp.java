package net.taken.project3960;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static void logFps(Integer fps) {
        logger.debug("FPS: " + fps);
    }

    public void start(Stage stage) throws Exception {

        logger.info("Starting Hello JavaFX and Maven demonstration application");

        String fxmlFile = "/fxml/game_view.fxml";
        logger.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));

        GameController gameController = loader.getController();
        gameController.initPlayer(new Player());

        logger.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 1280, 720);

        stage.setTitle("Project 3960");
        stage.setScene(scene);
        stage.show();

        new GameLoop(gameController::tick, gameController::render, MainApp::logFps).start();
    }
}
