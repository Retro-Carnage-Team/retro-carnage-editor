package net.retrocarnage.editor.assetmanager.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the persistence features of AssetServiceImpl.
 *
 * @author Thomas Werner
 */
public class AssetServiceImplPeristenceTest {

    @Before
    public void setUp() {
        // Create some test assets
    }

    /**
     * Tests the persistence feature with an empty state.
     */
    @Test
    public void testEmptyDatabase() throws Exception {
        final AssetServiceImpl assetServiceOut = new AssetServiceImpl();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        assetServiceOut.saveAssets(baos);

        final AssetServiceImpl assetServiceIn = new AssetServiceImpl();
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        assetServiceIn.loadAssets(bais);

        assertEquals(0, assetServiceIn.getMusic(null).size());
        assertEquals(0, assetServiceIn.getSprites(null, null).size());
    }

    @Test
    public void testPopulatedDatabase() throws Exception {
        // TODO: Populate

        System.out.println("loadAssets");
        InputStream in = null;
        AssetServiceImpl instance = new AssetServiceImpl();
        instance.loadAssets(in);

        fail("The test case is a prototype.");
    }

    @Test
    public void testCorruptDatabase() throws Exception {
        // TODO: Populate
        fail("The test case is a prototype.");
    }

}
