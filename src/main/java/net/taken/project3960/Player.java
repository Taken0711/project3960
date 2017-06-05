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

    /**
     * Return the lookbasis with z align to the look and (x, y) as undirect 2d basis
     * @return
     */
    public Vector3D[] getLookBasis() {
        Vector3D e3 = getLookVector();
        Vector3D e1 = new Vector3D(e3.getY(), -e3.getX(), -e3.getZ());
        Vector3D e2 = e1.crossProduct(e3);
        return new Vector3D[] {e1, e2, e3};
    }
}
