package net.taken.project3960;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.taken.project3960.exception.NonDisplayabledPoint;
import net.taken.project3960.util.CanvasUtils;
import net.taken.project3960.util.math.TransformationMatrix;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import static java.lang.Math.tan;
import static net.taken.project3960.util.geometry.GeometryUtils.toRadian;

public class GameScene {

    public static final double CELL_SIZE = 256.0;
    public static final double DISTANCE_VIEW = 50.0;

    private final double NEAR_DISTANCE = 0;
    private final double FAR_DISTANCE = DISTANCE_VIEW * CELL_SIZE;

    public static final double RATIO = 16.0 / 9.0;
    /**
     * Horizontal FOV in degrees
     */
    public static final double HORIZONTAL_FOV = 95.0;
    /**
     * Vertical FOV in degrees
     */
    public static final double VERTICAL_FOV = HORIZONTAL_FOV * 1/RATIO;

    private Canvas gameCanvas;
    private Player player;
    private final RealMatrix clippingMatrix;

    public GameScene(Canvas gameCanvas, Player player) {
        this.gameCanvas = gameCanvas;
        this.player = player;

        //TODO: Move this out
        clippingMatrix = MatrixUtils.createRealMatrix(4, 4);
        clippingMatrix.setEntry(0, 0, 1.0 / (tan(toRadian(HORIZONTAL_FOV / 2.0))));
        clippingMatrix.setEntry(1, 1, 1.0 / tan(toRadian(VERTICAL_FOV / 2.0)));
        clippingMatrix.setEntry(2, 2, (-FAR_DISTANCE - NEAR_DISTANCE) / (FAR_DISTANCE - NEAR_DISTANCE));
        clippingMatrix.setEntry(2, 3, (2 * NEAR_DISTANCE * FAR_DISTANCE) / (NEAR_DISTANCE - FAR_DISTANCE));
        clippingMatrix.setEntry(3, 2, 1);
    }

    public void render() {
        CanvasUtils.clear(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        RealMatrix cameraSpaceMatrix = TransformationMatrix.getTranslationMatrix(player.getPostion().negate());
        Vector3D[] lookBasis = player.getLookBasis();
        RealMatrix projectionMatrix = TransformationMatrix.getChangeBasisMatrix(lookBasis);


        RealMatrix transformMatrix = cameraSpaceMatrix.preMultiply(projectionMatrix).preMultiply(clippingMatrix);

        drawGround(gc, transformMatrix);
    }

    private void drawGround(GraphicsContext gc, RealMatrix transformMatrix) {
        gc.setFill(Color.GREEN);
        gc.setLineWidth(1.0);
        for (int i = -(int)DISTANCE_VIEW; i < DISTANCE_VIEW; i++) {
            for (int j = -(int)DISTANCE_VIEW; j < DISTANCE_VIEW; j++) {
                drawCell(gc, i, j, transformMatrix);
            }
        }
    }

    private void drawCell(GraphicsContext gc, int x, int y, RealMatrix transformMatrix) {

        double xPx = x * CELL_SIZE;
        double yPx = y * CELL_SIZE;

        try {
            Vector2D a = meshPointToCanvasPoint(new Vector3D(xPx, yPx, 0), transformMatrix);
            Vector2D b = meshPointToCanvasPoint(new Vector3D(xPx+CELL_SIZE, yPx, 0), transformMatrix);
            Vector2D c = meshPointToCanvasPoint(new Vector3D(xPx+CELL_SIZE, yPx+CELL_SIZE, 0), transformMatrix);
            Vector2D d = meshPointToCanvasPoint(new Vector3D(xPx, yPx+CELL_SIZE, 0), transformMatrix);

            CanvasUtils.drawLine(gc, a, b);
            CanvasUtils.drawLine(gc, b, c);
            CanvasUtils.drawLine(gc, c, d);
            CanvasUtils.drawLine(gc, d, a);
        } catch (NonDisplayabledPoint e) {
        }
    }

    protected Vector2D meshPointToCanvasPoint(Vector3D meshPoint, RealMatrix transformMatrix) throws NonDisplayabledPoint {

        RealMatrix v = MatrixUtils.createColumnRealMatrix(new double[]{meshPoint.getX(), meshPoint.getY(), meshPoint.getZ(), 1.0});
        RealVector res = v.preMultiply(transformMatrix).getColumnVector(0);
        double w = res.getEntry(2);

        // With this projection, if w > 0, the vertex must be clipped
        if (w > 0)
            throw new NonDisplayabledPoint();

        double x = res.getEntry(0);
        double y = res.getEntry(1);
        return new Vector2D(x * gameCanvas.getWidth() / (2.0 * w) + (gameCanvas.getWidth() / 2),
                y * gameCanvas.getHeight() / (2.0 * w) + (gameCanvas.getHeight() / 2));

    }

}
