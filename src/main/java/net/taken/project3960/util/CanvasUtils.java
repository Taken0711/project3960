package net.taken.project3960.util;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class CanvasUtils {

    public static void clear(Canvas gameCanvas) {
        gameCanvas.getGraphicsContext2D().clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    public static void drawLine (GraphicsContext gc, Vector2D v1, Vector2D v2) {
        gc.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }

}
