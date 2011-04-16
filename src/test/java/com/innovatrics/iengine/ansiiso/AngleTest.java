package com.innovatrics.iengine.ansiiso;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the {@link Angle} class.
 * @author Martin Vysny
 */
public class AngleTest {

    @Test
    public void testSimpleCasesDeg() {
	assertEquals(0, new Angle((byte) 0).getDegree());
	assertEquals(270, new Angle((byte) 64).getDegree());
	assertEquals(180, new Angle((byte) 128).getDegree());
	assertEquals(90, new Angle((byte) 192).getDegree());
    }

    @Test
    public void testSimpleCasesRad() {
	assertEquals(0, new Angle((byte) 0).getRadian(), 0);
	assertEquals(3 * Math.PI / 2, new Angle((byte) 64).getRadian(), 0);
	assertEquals(Math.PI, new Angle((byte) 128).getRadian(), 0);
	assertEquals(Math.PI / 2, new Angle((byte) 192).getRadian(), 0);
    }

    @Test
    public void testRadToDeg() {
	assertEquals(0, Angle.radToDeg(0));
	assertEquals(-90, Angle.radToDeg(3 * Math.PI / 2));
	assertEquals(180, Angle.radToDeg(Math.PI));
	assertEquals(90, Angle.radToDeg(Math.PI / 2));
    }
}
