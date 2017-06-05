package net.taken.project3960;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.taken.project3960.exception.NonDisplayabledPoint;
import net.taken.project3960.util.CanvasUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static java.lang.Math.*;
import static net.taken.project3960.util.geometry.GeometryUtils.*;

public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    public static final double CELL_SIZE = 256.0;
    public static final double DISTANCE_VIEW = 50.0;
    private final double FAR_DISTANCE = DISTANCE_VIEW * CELL_SIZE;
    private final double NEAR_DISTANCE = 0;
    public static final double RATIO = 16.0 / 9.0;
    /**
     * Horizontal FOV in degrees
     */
    public static final double HORIZONTAL_FOV = 90.0;
    /**
     * Vertical FOV in degrees
     */
    public static final double VERTICAL_FOV = HORIZONTAL_FOV * 1/RATIO;


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
        CanvasUtils.clear(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        drawGround(gc);
    }

    private void drawGround(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setLineWidth(1.0);
        /*drawCell(gc, -5, 1);
        drawCell(gc, 5, 0);
        drawCell(gc, 5, 1);*/
        /*drawCell(gc, 4, 0);
        drawCell(gc, 4, 1);*/
        //gc.fillRect(0, gameCanvas.getHeight() / 2, gameCanvas.getWidth(), gameCanvas.getHeight() / 2);
        for (int i = -(int)DISTANCE_VIEW; i < DISTANCE_VIEW; i++) {
            for (int j = -(int)DISTANCE_VIEW; j < DISTANCE_VIEW; j++) {
                drawCell(gc, i, j);
            }
        }
    }

    // TODO: May be custom utility class with a GraphicsContext field
    private void drawCell(GraphicsContext gc, int x, int y) {

        double xPx = x * CELL_SIZE;
        double yPx = y * CELL_SIZE;

        try {
            Vector2D a = meshPointToCanvasPoint2(new Vector3D(xPx, yPx, 0));
            Vector2D b = meshPointToCanvasPoint2(new Vector3D(xPx+CELL_SIZE, yPx, 0));
            Vector2D c = meshPointToCanvasPoint2(new Vector3D(xPx+CELL_SIZE, yPx+CELL_SIZE, 0));
            Vector2D d = meshPointToCanvasPoint2(new Vector3D(xPx, yPx+CELL_SIZE, 0));

            CanvasUtils.drawLine(gc, a, b);
            CanvasUtils.drawLine(gc, b, c);
            CanvasUtils.drawLine(gc, c, d);
            CanvasUtils.drawLine(gc, d, a);
        } catch (NonDisplayabledPoint e) {
        }
    }

    private Vector2D meshPointToCanvasPoint2(Vector3D meshPoint) throws NonDisplayabledPoint {

        RealMatrix clipMatrix = MatrixUtils.createRealMatrix(4, 4);

        clipMatrix.setEntry(0, 0, 1.0 / (tan(toRadian(HORIZONTAL_FOV / 2.0))));
        clipMatrix.setEntry(1, 1, 1.0 / tan(toRadian(VERTICAL_FOV / 2.0)));
        clipMatrix.setEntry(2, 2, (-FAR_DISTANCE - NEAR_DISTANCE) / (FAR_DISTANCE - NEAR_DISTANCE));
        clipMatrix.setEntry(2, 3, (2 * NEAR_DISTANCE * FAR_DISTANCE) / (NEAR_DISTANCE - FAR_DISTANCE));
        clipMatrix.setEntry(3, 2, 1);

        Vector3D[] lookBasis = player.getLookBasis();
        Vector3D lookRelativePoint = changeBasis(changeOrigin(meshPoint, player.getPostion()), lookBasis[1].negate(), lookBasis[2], lookBasis[0]);

        RealVector v = new ArrayRealVector(new double[]{lookRelativePoint.getX(), lookRelativePoint.getY(), lookRelativePoint.getZ(), 1.0});

        RealVector res = clipMatrix.preMultiply(v);

        double w = res.getEntry(2);

        // With this projection, if w > 0, the vertice must be clipped

        if (w > 0)
            throw new NonDisplayabledPoint();

        double x = res.getEntry(0);
        double y = res.getEntry(1);

        return new Vector2D(x * gameCanvas.getWidth() / (2.0 * w) + (gameCanvas.getWidth() / 2),
                y * gameCanvas.getHeight() / (2.0 * w) + (gameCanvas.getHeight() / 2));

    }

}