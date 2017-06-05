package net.taken.project3960.util.geometry;

import javafx.geometry.Point3D;
import net.taken.project3960.util.math.NSquaredMatrix;

public class AdvancedPoint extends Point3D {
    /**
     * Creates a new instance of {@code Point3D}.
     *
     * @param x The X coordinate of the {@code Point3D}
     * @param y The Y coordinate of the {@code Point3D}
     * @param z The Z coordinate of the {@code Point3D}
     */
    public AdvancedPoint(double x, double y, double z) {
        super(x, y, z);
    }

    public AdvancedPoint(Point3D p) {
        super(p.getX(), p.getY(), p.getZ());
    }


    public AdvancedPoint changeOfOrthonormalBasis(Point3D e1, Point3D e2, Point3D e3) {
        ChangeBasisMatrix m = new ChangeBasisMatrix(e1.normalize(), e2.normalize(), e3.normalize());
        double[] tmp = m.multiplyVector(getX(), getY(), getZ());
        return new AdvancedPoint(tmp[0], tmp[1], tmp[2]);
    }

    public AdvancedPoint copy() {
        return new AdvancedPoint(this);
    }
}
