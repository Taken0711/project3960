package net.taken.project3960;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.*;

public class Player {

    private Vector3D postion = new Vector3D(0.0, 0.0, GameScene.CELL_SIZE);

    /* Angles are in degrees */

    /** Azimuth is around z (in (x, y) plane) within [0, 360 [ (0 is x)*/
    private double azimuth = 0.0;
    /** Elevation is above (x, y) plane, within [ -90, 90 ]  (0 is in (x, y))*/
    private double elevation = 0.0;

    public Vector3D getPostion() {
        return postion;
    }

    public Vector3D getLookVector() {
        return new Vector3D(toRadians(azimuth), toRadians(elevation));
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

    public void setAzimuth(double azimuth) {
        if (azimuth >= 0)
            this.azimuth = (azimuth % 360);
        else
            this.azimuth = -(azimuth % 360);
    }

    public void setElevation(double elevation) {
        if (-90 <= elevation && elevation <= 90)
            this.elevation = elevation;
    }

    public void addAzimuth(double delta) {
        setAzimuth(azimuth + delta);
    }

    public void addElevation(double delta) {
        setElevation(elevation + delta);
    }
}
