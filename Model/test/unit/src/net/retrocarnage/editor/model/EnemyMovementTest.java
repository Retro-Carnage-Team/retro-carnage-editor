package net.retrocarnage.editor.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for EnemyMovement.
 *
 * @author Thomas Werner
 */
public class EnemyMovementTest {

    @Test
    public void testAdd() {
        final EnemyMovement result = new EnemyMovement();
        result.add(new EnemyMovement(1, 1));
        result.add(new EnemyMovement(3, 7));
        assertEquals(4, result.getDistanceX());
        assertEquals(8, result.getDistanceY());
    }

}
