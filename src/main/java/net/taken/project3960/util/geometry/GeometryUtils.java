package net.taken.project3960.util.geometry;

import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;

public class GeometryUtils {

    private GeometryUtils() {
    }

    public static double toDegree(double radian) {
        return radian * 180 / Math.PI;
    }

    public static double toRadian(double degree) {
        return degree * Math.PI / 180;
    }

    public static Vector3D changeBasis(Vector3D v, Vector3D e1, Vector3D e2, Vector3D e3) {
        RealMatrix p = MatrixUtils.createRealMatrix(3, 3);
        p.setColumn(0, e1.toArray());
        p.setColumn(1, e2.toArray());
        p.setColumn(2, e3.toArray());
        RealMatrix pPrime = MatrixUtils.inverse(p);
        RealMatrix x = MatrixUtils.createColumnRealMatrix(v.toArray());

        RealMatrix xPrime = pPrime.multiply(x);

        return new Vector3D(xPrime.getColumn(0));
    }

    public static Vector3D changeBasis(Vector3D v, Vector3D[] basis) {
        if (basis.length != 3)
            throw new IllegalArgumentException("Basis must be in a 3D space");
        return changeBasis(v, basis[0], basis[1], basis[2]);
    }


        public static Vector3D changeOrigin(Vector3D v, Vector3D newOrigin) {
        return v.subtract(newOrigin);
    }

}
