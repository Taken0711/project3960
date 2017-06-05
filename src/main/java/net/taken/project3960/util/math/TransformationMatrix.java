package net.taken.project3960.util.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class TransformationMatrix {

    private TransformationMatrix() {
    }

    public static RealMatrix getTranslationMatrix(Vector3D translation) {
        return MatrixUtils.createRealMatrix(new double[][]{
                {1, 0, 0, translation.getX()},
                {0, 1, 0, translation.getY()},
                {0, 0, 1, translation.getZ()},
                {0, 0, 0, 1},
        });
    }

    public static RealMatrix getChangeBasisMatrix(Vector3D e1, Vector3D e2, Vector3D e3) {
        RealMatrix p = MatrixUtils.createRealMatrix(new double[][]{
                {e1.getX(), e2.getX(), e3.getX(), 0},
                {e1.getY(), e2.getY(), e3.getY(), 0},
                {e1.getZ(), e2.getZ(), e3.getZ(), 0},
                {0,         0,         0,         1},
        });
        return MatrixUtils.inverse(p);
    }

    public static RealMatrix getChangeBasisMatrix(Vector3D[] basis) {
        if (basis.length != 3)
            throw new IllegalArgumentException("Basis must be in a 3D space");
        return getChangeBasisMatrix(basis[0], basis[1], basis[2]);
    }
}
