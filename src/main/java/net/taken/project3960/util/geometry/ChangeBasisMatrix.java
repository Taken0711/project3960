package net.taken.project3960.util.geometry;

import javafx.geometry.Point3D;
import net.taken.project3960.util.math.NSquaredMatrix;

public class ChangeBasisMatrix extends NSquaredMatrix{


    public ChangeBasisMatrix(int n) {
        super(n);
    }

    public ChangeBasisMatrix(NSquaredMatrix m) {
        super(m);
    }

    /**
     * Create a change of basis matrix in the standard 3d basis
     */
    public ChangeBasisMatrix(Point3D e1, Point3D e2, Point3D e3) {
        super(3);
        setColumn(0, e1);
        setColumn(1, e2);
        setColumn(2, e3);
    }

    @Override
    public ChangeBasisMatrix copy() {
        return new ChangeBasisMatrix(this);
    }
}
