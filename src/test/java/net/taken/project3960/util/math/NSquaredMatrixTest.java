package net.taken.project3960.util.math;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class NSquaredMatrixTest {

    NSquaredMatrix identity;
    NSquaredMatrix m1;
    NSquaredMatrix m2;
    NSquaredMatrix m3;

    @Before
    public void setUp() throws Exception {
        identity = new NSquaredMatrix(3);
        for (int i = 0; i < 3; i++)
            identity.set(i, i, 1);

        m1 = new NSquaredMatrix(3, new double[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        });

        m2 = new NSquaredMatrix(3, new double[][]{
                {1, 2, 1},
                {2, 1, 2},
                {1, 2, 1}
        });

        m3 = new NSquaredMatrix(4, new double[][]{
                {1, 2, 1, 3},
                {2, 1, 2, 4},
                {1, 2, 1, 5},
                {9, 8, 7, 6}
        });
    }

    @Test
    public void shouldSetRowCorrectly() throws Exception {
        m1.setRow(1, 2, 2, 2);
        assertTrue(Arrays.deepEquals(new double[][]{
                {1, 1, 1},
                {2, 2, 2},
                {1, 1, 1}
        }, m1.matrix));
    }

    @Test
    public void shouldSetColumnCorrectly() throws Exception {
        m1.setColumn(1, 2, 2, 2);
        assertTrue(Arrays.deepEquals(new double[][]{
                {1, 2, 1},
                {1, 2, 1},
                {1, 2, 1}
        }, m1.matrix));
    }

    @Test
    public void shouldAddCorrectly() throws Exception {
        m1.add(m2);
        assertTrue(Arrays.deepEquals(new double[][]{
                {2, 3, 2},
                {3, 2, 3},
                {2, 3, 2}
        }, m1.matrix));
    }

    @Test
    public void shouldSubstractCorrectly() throws Exception {
        m2.substract(m1);
        assertTrue(Arrays.deepEquals(new double[][]{
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        }, m2.matrix));
    }

    @Test
    public void shouldMultiplyScalarCorrectly() throws Exception {
        m1.multiplyScalar(3);
        assertTrue(Arrays.deepEquals(new double[][]{
                {3, 3, 3},
                {3, 3, 3},
                {3, 3, 3}
        }, m1.matrix));
    }

    @Test
    public void shouldMultiplyVectorCorrectly() throws Exception {
        assertTrue(Arrays.equals(new double[]{6, 6, 6}, m1.multiplyVector(1, 2, 3)));
    }

    @Test
    public void shouldMultiplyVectorCorrectlyWithIdentityMatrix() throws Exception {
        assertTrue(Arrays.equals(new double[]{1, 2, 3}, identity.multiplyVector(1, 2, 3)));
    }

    @Test
    public void shouldHaveIdentityWhenTransposeIdentity() throws Exception {
        NSquaredMatrix actual = identity.copy();
        actual.transpose();
        assertEquals(identity, actual);
    }

    @Test
    public void shouldTransposeCorrectlyWhenSymmetricMatrix() throws Exception {
        NSquaredMatrix actual = m2.copy();
        actual.transpose();
        assertEquals(m2, actual);
    }

    @Test
    public void shouldTransposeCorrectlyWhenNonSymmetricMatrix() throws Exception {
        NSquaredMatrix actual = m3.copy();
        actual.transpose();
        assertNotEquals(m3, actual);
        assertTrue(Arrays.deepEquals(new double[][]{
                {1, 2, 1, 9},
                {2, 1, 2, 8},
                {1, 2, 1, 7},
                {3, 4, 5, 6}
        }, actual.matrix));
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifySmallerVector() throws Exception {
        m1.checkVectorSize(1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifyLargerVector() throws Exception {
        m1.checkVectorSize(1, 2, 3, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifySmallerMatrix() throws Exception {
        m1.checkMatrixSize(new NSquaredMatrix(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void identifyLargerMatrix() throws Exception {
        m1.checkMatrixSize(new NSquaredMatrix(4));
    }
}