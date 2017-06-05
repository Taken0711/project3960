package net.taken.project3960.util.geometry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvancedPointTest {

    AdvancedPoint p1;

    @Before
    public void setUp() throws Exception {
        p1 = new AdvancedPoint(1,2,3);
    }

    @Test
    public void shouldHaveSamePointWhenChangeBasisToUnnormalizedStandardBasis() throws Exception {
        assertEquals(p1, p1.changeOfOrthonormalBasis(
                new AdvancedPoint(1, 0, 0),
                new AdvancedPoint(0, 2, 0),
                new AdvancedPoint(0, 0, 3)));
    }

    @Test
    public void shouldChangeBasisCorrectlyWhenChangeBasisToNormalizedBasis() throws Exception {
        assertEquals(new AdvancedPoint(2, 1, 3), p1.changeOfOrthonormalBasis(
                new AdvancedPoint(0, 1, 0),
                new AdvancedPoint(1, 0, 0),
                new AdvancedPoint(0, 0, 1)));
    }

    @Test
    public void shouldChangeBasisCorrectlyWhenChangeBasisToUnnormalizedBasis() throws Exception {
        assertEquals(new AdvancedPoint(2, 1, 3), p1.changeOfOrthonormalBasis(
                new AdvancedPoint(1, 1, 0),
                new AdvancedPoint(-1, 1, 0),
                new AdvancedPoint(0, 0, 1)));
    }
}