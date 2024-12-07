package net.retrocarnage.editor.core;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Unit test for IconUtil.
 *
 * @author Thomas Werner
 */
public class IconUtilTest {

    @Test
    public void testLoadIconOk() throws IOException {
        try (final InputStream is = IconUtilTest.class.getResourceAsStream("testdata/1x1.png")) {
            final Image icon = IconUtil.loadIcon(is);
            assertNotNull(icon);
            assertEquals(1, icon.getWidth(null));
            assertEquals(1, icon.getHeight(null));
        }
    }

    @Test
    public void testLoadIconFallback() throws IOException {
        final Image icon = IconUtil.loadIcon((InputStream)null);
        assertNotNull(icon);
        assertEquals(1, icon.getWidth(null));
        assertEquals(1, icon.getHeight(null));
    }

}
