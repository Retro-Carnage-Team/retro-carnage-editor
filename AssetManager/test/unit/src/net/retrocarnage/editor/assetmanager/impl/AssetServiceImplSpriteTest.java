package net.retrocarnage.editor.assetmanager.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.Sprite;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for the music assets management features of AssetServiceImpl.
 *
 * @author Thomas Werner
 */
public class AssetServiceImplSpriteTest {

    private static final String GOOGLE_LOGO = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    private static final String WIKIPEDIA_LOGO = "https://en.wikipedia.org/static/images/project-logos/enwiki-1.5x.png";

    private static Sprite sprite;

    @BeforeClass
    public static void setUpClass() {
        final AttributionData attributionData = new AttributionData();
        attributionData.setAuthor("Google Inc.");
        attributionData.setWebsite("https://www.google.com");

        sprite = new Sprite();
        sprite.setAttributionData(attributionData);
        sprite.setName("Google Logo");
        sprite.getTags().add("Google");
        sprite.getTags().add("Alphabet");
        sprite.getTags().add("Logo");
    }

    @Test
    public void testAddAndRemoveAsset() throws Exception {
        final AssetServiceImpl service = (AssetServiceImpl) AssetService.getDefault();
        try (final InputStream logoStream = new URL(GOOGLE_LOGO).openStream()) {
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
        final AssetServiceImpl service = (AssetServiceImpl) AssetService.getDefault();
        try (final InputStream logoStream = new URL(GOOGLE_LOGO).openStream()) {
            service.addSprite(sprite, logoStream);
        }

        int googleLogoSize;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            sprite.getData(baos);
            googleLogoSize = baos.toByteArray().length;
        }

        try (final InputStream logoStream = new URL(WIKIPEDIA_LOGO).openStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            service.updateSpriteAsset(sprite.getId(), logoStream);
            sprite.getData(baos);
            assertNotEquals(googleLogoSize, baos.toByteArray().length);
            assertTrue(0 < baos.toByteArray().length);
            service.removeSprite(sprite.getId());
        }
    }

}
