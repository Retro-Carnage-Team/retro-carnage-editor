package net.retrocarnage.editor.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Position.
 *
 * @author Thomas Werner
 */
public class PositionTest {

    @Test
    public void testGetCenter() {
        final Position a = new Position(1, 1, 6, 6);
        assertEquals(4, a.getCenter().x);
        assertEquals(4, a.getCenter().y);
    }

    @Test
    public void testScale() {
        final Position a = new Position(2, 2, 6, 6).scale(0.5f);
        assertEquals(1, a.getX());
        assertEquals(1, a.getY());
        assertEquals(3, a.getWidth());
        assertEquals(3, a.getHeight());
    }

}
