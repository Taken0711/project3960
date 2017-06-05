package net.taken.project3960;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.taken.project3960.exception.NonDisplayabledPoint;
import net.taken.project3960.util.CanvasUtils;
import net.taken.project3960.util.math.TransformationMatrix;
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

    }

    public void render() {

        gameScene.render();

    }


}