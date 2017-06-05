package net.taken.project3960;

import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Player {

    private Vector3D postion = new Vector3D(0.0, 0.0, GameController.CELL_SIZE);

    /* Angles are in degrees */

    /** Azimuth is around z (in (x, y) plane) within [0, 360 [ (0 is x)*/
    private double azimuth = 0.0;
    /** Elevation is above (x, y) plane, within [ -180, 180 [  (0 is in (x, y))*/
    private double elevation = 0.0;

    public Vector3D getPostion() {
        return postion;
    }

    public Vector3D getLookVector() {
        return new Vector3D(azimuth, elevation);
    }

    public Vector3D[] getLookBasis() {
        Vector3D e1 = getLookVector();
        Vector3D e2 = new Vector3D(-e1.getY(), e1.getX(), e1.getZ());
        Vector3D e3 = e1.crossProduct(e2);
        return new Vector3D[] {e1, e2, e3};
    }
}
