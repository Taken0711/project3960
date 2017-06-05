package net.taken.project3960;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.taken.project3960.util.CanvasUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Math.*;
import static net.taken.project3960.util.geometry.GeometryUtils.*;

public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    public static final double CELL_SIZE = 256.0;
    public static final double DISTANCE_VIEW = 50.0;
    public static final double VANISHING_DISTANCE = 1e4;
    public static final double RATIO = 16.0 / 9.0;
    /**
     * Horizontal FOV in degrees
     */
    public static final double HORIZONTAL_FOV = 80.0;
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
        gc.setLineWidth(3.0);
        drawCell(gc, 5, 0);
        drawCell(gc, 5, 1);
        drawCell(gc, 4, 0);
        drawCell(gc, 4, 1);
        //gc.fillRect(0, gameCanvas.getHeight() / 2, gameCanvas.getWidth(), gameCanvas.getHeight() / 2);
    }

    // TODO: May be custom utility class with a GraphicsContext field
    private void drawCell(GraphicsContext gc, int x, int y) {
        //gc.fillRect(0, gameCanvas.getHeight() / 2, gameCanvas.getWidth(), gameCanvas.getHeight() / 2);
        /*double alpha = 0.5 - 0.2;
        double xPx = x * CELL_SIZE;
        double yPx = y * CELL_SIZE;
        double zPx = 1.0 * CELL_SIZE;
        double d = (zPx * CELL_SIZE) / (xPx + CELL_SIZE);
        double delta = d * atan(0.5 - alpha);

        double[] xs = {
                gameCanvas.getWidth() / 2 - d / 2,
                gameCanvas.getWidth() / 2 + d / 2,
                gameCanvas.getWidth() / 2 + d / 2 - delta,
                gameCanvas.getWidth() / 2 - d / 2 + delta};
        double[] ys = {
                yPx,
                yPx,
                yPx - d,
                yPx - d};
        logger.debug(Arrays.toString(xs));
        logger.debug(Arrays.toString(ys));
        gc.strokePolygon(xs, ys, 4);*/

        double xPx = x * CELL_SIZE;
        double yPx = y * CELL_SIZE;

        Vector2D a = meshPointToCanvasPoint2(new Vector3D(xPx, yPx, 0));
        Vector2D b = meshPointToCanvasPoint2(new Vector3D(xPx+CELL_SIZE, yPx, 0));
        Vector2D c = meshPointToCanvasPoint2(new Vector3D(xPx+CELL_SIZE, yPx+CELL_SIZE, 0));
        Vector2D d = meshPointToCanvasPoint2(new Vector3D(xPx, yPx+CELL_SIZE, 0));

        CanvasUtils.drawLine(gc, a, b);
        CanvasUtils.drawLine(gc, b, c);
        CanvasUtils.drawLine(gc, c, d);
        CanvasUtils.drawLine(gc, d, a);
    }

    // TODO: relative computation must be in the calling method, as well as the fov check
    private Vector2D meshPointToCanvasPoint(Vector3D meshPoint) {
        Vector3D[] lookBasis = player.getLookBasis();
        Vector3D lookVector = lookBasis[0];
        Vector3D lookRelativePoint = changeBasis(changeOrigin(meshPoint, player.getPostion()), lookBasis);

        Vector3D lookRelativePointWithinXY = new Vector3D(lookRelativePoint.getX(), lookRelativePoint.getY(), 0);
        Vector3D lookRelativePointWithinXZ = new Vector3D(lookRelativePoint.getX(), 0, lookRelativePoint.getZ());

        double horizontalAngle = Vector3D.angle(lookRelativePointWithinXY, lookVector);
        double verticalAngle = Vector3D.angle(lookRelativePointWithinXZ, lookVector);

        // TODO Vanishing point must be in player's x and y, at a huge distance, a bit higher than the look (higher than the middle on the screen)


        if (horizontalAngle <= HORIZONTAL_FOV / 2 && verticalAngle <= VERTICAL_FOV / 2) {
            // The point is in the fov
            // TODO compute vanishong points in the calling methods
            Vector3D vanishingPointX = changeBasis(new Vector3D(VANISHING_DISTANCE, 0, 0), lookBasis);
            Vector3D vanishingPointY = changeBasis(new Vector3D(0, VANISHING_DISTANCE, 0), lookBasis);

            //Vector2D onScreenVanishingPointX =

            // TODO: ignoring z right now

            /*Vector3D deltaX = new Vector3D(lookRelativePoint.getX(), 0, 0);

            Vector3D tmp = vanishingPointX.negate().add(deltaX).normalize();*/
        }


        return null;
    }

    private Vector2D meshPointToCanvasPoint2(Vector3D meshPoint) {

        double fov = 1.0 / tan(toRadian(VERTICAL_FOV / 2.0));
        double far = 80.0 * CELL_SIZE;
        double near = 1;

        RealMatrix clipMatrix = MatrixUtils.createRealMatrix(4, 4);
        /*clipMatrix.setEntry(0, 0, fov * RATIO * (near / gameCanvas.getWidth()));
        clipMatrix.setEntry(1, 1, fov * (near / gameCanvas.getHeight()));
        clipMatrix.setEntry(2, 2, (far + near) / (far - near));
        clipMatrix.setEntry(2, 3, 1);
        clipMatrix.setEntry(3, 2, (2 * near * far) / (near - far));*/

        /*clipMatrix.setEntry(0, 0, 1.0 / (tan(toRadian(HORIZONTAL_FOV / 2.0))));
        clipMatrix.setEntry(1, 1, 1.0 / tan(toRadian(VERTICAL_FOV / 2.0)));
        clipMatrix.setEntry(2, 2, (- far - near) / (far - near));
        clipMatrix.setEntry(2, 3, 1);
        clipMatrix.setEntry(3, 2, (2 * near * far) / (near - far));*/

        clipMatrix.setEntry(0, 0, 1.0 / (tan(toRadian(HORIZONTAL_FOV / 2.0))));
        clipMatrix.setEntry(1, 1, 1.0 / tan(toRadian(VERTICAL_FOV / 2.0)));
        clipMatrix.setEntry(2, 2, (- far - near) / (far - near));
        clipMatrix.setEntry(2, 3, (2 * near * far) / (near - far));
        clipMatrix.setEntry(3, 2, 1);

        Vector3D[] lookBasis = player.getLookBasis();
        Vector3D lookRelativePoint = changeBasis(changeOrigin(meshPoint, player.getPostion()), lookBasis);

        RealVector v = new ArrayRealVector(new double[]{-lookRelativePoint.getY(), lookRelativePoint.getZ(), lookRelativePoint.getX(), 1.0});

        RealVector res = clipMatrix.preMultiply(v);

        double w = res.getEntry(2);
        double x = res.getEntry(0);
        double y = res.getEntry(1);

        return new Vector2D(x * gameCanvas.getWidth() / (2.0 * w) + (gameCanvas.getWidth() / 2),
                y * gameCanvas.getHeight() / (2.0 * w) + (gameCanvas.getHeight() / 2));

    }

}