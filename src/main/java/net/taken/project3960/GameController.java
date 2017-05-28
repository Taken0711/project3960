package net.taken.project3960;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);
    
    @FXML
    public Canvas gameCanvas;

    private Player player;

    public void initPlayer(Player player) {
        this.player = player;
    }

    // TODO: move this to a world class
    public void tick(float timeStep) {

    }

    public void render() {
        logger.debug("render");

        CanvasUtils.clear(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        drawGround(gc);
    }

    private void drawGround(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(0, gameCanvas.getHeight() / 2, gameCanvas.getWidth(), gameCanvas.getHeight() / 2);
    }

}
