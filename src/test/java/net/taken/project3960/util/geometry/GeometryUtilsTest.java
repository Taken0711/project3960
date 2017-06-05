package net.taken.project3960.util.geometry;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.*;
import static org.junit.Assert.*;
import static net.taken.project3960.util.geometry.GeometryUtils.*;

public class GeometryUtilsTest {

    Vector3D u;

    @Before
    public void setUp() throws Exception {
        u = new Vector3D(1, 2, 3);
    }

    @Test
    public void shoudlHaveAngleWhenConvertFromRadianToDegreeAndFromDegreeToRadian() throws Exception {
        assertEquals(0.5, toRadian(toDegree(0.5)), 1e-6);
    }

    @Test
    public void shoudlHaveAngleWhenConvertFromDegreeToRadianAndFromRadianToDegree() throws Exception {
        assertEquals(0.5, toDegree(toRadian(0.5)), 1e-6);
    }

    @Test
    public void shouldComputeCorrectlyWhenComputeSomeTrigonometricConstants() throws Exception {
        assertEquals(45, toDegree(PI / 4), 1e-6);
        assertEquals(PI / 2, toRadian(90), 1e-6);
        assertEquals(180, toDegree(PI), 1e-6);
        assertEquals(2 * PI, toRadian(360), 1e-6);
    }

    @Test
    public void shouldChangeBasisCorrectlyWhenChangeToUnnormalBasis() throws Exception {
        assertEquals(new Vector3D(1, 1, 1), changeBasis(u,
                new Vector3D(1, 0, 0),
                new Vector3D(0, 2, 0),
                new Vector3D(0, 0, 3)));
    }

    @Test
    public void shouldChangeBasisCorrectlyWhenChangeToUnorthonormalBasis() throws Exception {
        Vector3D actual = changeBasis(u,
                new Vector3D(1, 1, 1),
                new Vector3D(1, 0, -1),
                new Vector3D(2, -1, -3));
        assertEquals(2.0, actual.getX(), 1e-6);
        assertEquals(-1.0, actual.getY(), 1e-6);
        assertEquals(0.0, actual.getZ(), 1e-6);
    }

}