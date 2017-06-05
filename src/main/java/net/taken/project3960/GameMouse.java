package net.taken.project3960;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class GameMouse {

    private static final Logger logger = LogManager.getLogger(GameMouse.class);

    private static final double MOUSE_SENSITIVITY = 0.0001;

    double deltaX;
    double deltaY;
    private Canvas gameCanvas;

    public GameMouse(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        resetCursor();
        gameCanvas.setOnMouseMoved(this::mouseMoved);
    }

    private void mouseMoved(MouseEvent mouseEvent) {
        deltaX = mouseEvent.getX() - gameCanvas.getWidth() / 2;
        deltaY = mouseEvent.getY() - gameCanvas.getHeight() / 2;
    }

    /**
     *Return the delta since the last call
     * @return
     */
    public Pair<Double, Double> getAngles() {
        Pair<Double, Double> res = new Pair<>((deltaX) * MOUSE_SENSITIVITY, (deltaY) * MOUSE_SENSITIVITY);
        resetCursor();
        deltaX = 0;
        deltaY = 0;
        return res;
    }

    private void resetCursor() {
        moveCursor(gameCanvas.getWidth() / 2, gameCanvas.getHeight() / 2);
    }

    /**
     * Move the mouse to the specific screen position
     *
     * @param x the x position
     * @param y the y position
     */
    public void moveCursor(double x, double y) {
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mouseMove((int)x, (int) y);
            } catch (AWTException e) {
                logger.error(e);
            }
        });
    }


}
