package net.retrocarnage.editor.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for EnemySkin.
 *
 * @author Thomas Werner
 */
public class EnemySkinTest {

    @Test
    public void testFindByName() {
        final EnemySkin skin = EnemySkin.findByName("enemy-2");
        assertEquals(EnemySkin.DigitalWithPistols.getName(), skin.getName());
    }

}
