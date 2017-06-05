package net.taken.project3960;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    @FXML
    public Canvas gameCanvas;

    private Player player;
    private GameScene gameScene;


    public void initPlayer(Player player) {
        this.player = player;
        gameScene = new GameScene(gameCanvas, player);

    }

    // TODO: move this to a world class
    public void tick(float timeStep) {
        gameScene.tick();
    }

    public void render() {

        gameScene.render();

    }


}