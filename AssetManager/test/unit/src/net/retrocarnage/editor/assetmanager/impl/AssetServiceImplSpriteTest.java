package net.retrocarnage.editor.assetmanager.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.retrocarnage.editor.assetmanager.impl.mocks.ApplicationFolderServiceMock;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.Sprite;
import org.junit.After;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the music assets management features of AssetServiceImpl.
 *
 * @author Thomas Werner
 */
public class AssetServiceImplSpriteTest {

    private ApplicationFolderServiceMock appFolderSvc;
    private Sprite sprite;

    @Before
    public void setUp() throws IOException {
        appFolderSvc = new ApplicationFolderServiceMock();

        final AttributionData attributionData = new AttributionData();
        attributionData.setAuthor("Google Inc.");
        attributionData.setWebsite("https://www.google.com");

        sprite = new Sprite(appFolderSvc);
        sprite.setAttributionData(attributionData);
        sprite.setName("Google Logo");
        sprite.getTags().add("Google");
        sprite.getTags().add("Alphabet");
        sprite.getTags().add("Logo");

    }

    @After
    public void tearDown() throws IOException {
        appFolderSvc.cleanUp();
    }

    @Test
    public void testAddAndRemoveAsset() throws Exception {
        final AssetServiceImpl service = new AssetServiceImpl(appFolderSvc);
        service.initializeFolderStructure();
        try (final InputStream logoStream = getTestDataInputStream("test-image-1.jpeg")) {
            service.addSprite(sprite, logoStream);
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sprite.getData(baos);
        assertTrue(0 < baos.toByteArray().length);
        assertNotNull(sprite.getId());
        assertNotNull(service.getSprite(sprite.getId()));

        service.removeSprite(sprite.getId());
        assertNull(service.getSprite(sprite.getId()));
    }

    @Test
    public void testReplaceAsset() throws Exception {
        final AssetServiceImpl service = new AssetServiceImpl(appFolderSvc);
        service.initializeFolderStructure();
        try (final InputStream logoStream = getTestDataInputStream("test-image-1.jpeg")) {
            service.addSprite(sprite, logoStream);
        }

        int googleLogoSize;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            sprite.getData(baos);
            googleLogoSize = baos.toByteArray().length;
        }

        try (final InputStream logoStream = getTestDataInputStream("test-image-2.jpeg"); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            service.updateSpriteAsset(sprite.getId(), logoStream);
            sprite.getData(baos);
            assertNotEquals(googleLogoSize, baos.toByteArray().length);
            assertTrue(0 < baos.toByteArray().length);
            service.removeSprite(sprite.getId());
        }
    }

    private static InputStream getTestDataInputStream(final String fileName) {
        return AssetServiceImplSpriteTest.class.getResourceAsStream("testdata/" + fileName);
    }

}
