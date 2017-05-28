package net.taken.project3960;

import javafx.scene.canvas.Canvas;

public class CanvasUtils {

    public static void clear(Canvas gameCanvas) {
        gameCanvas.getGraphicsContext2D().clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

}
