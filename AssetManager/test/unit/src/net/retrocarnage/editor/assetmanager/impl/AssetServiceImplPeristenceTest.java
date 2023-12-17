package net.retrocarnage.editor.assetmanager.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import net.retrocarnage.editor.assetmanager.impl.mocks.ApplicationFolderServiceMock;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.Music;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the persistence features of AssetServiceImpl.
 *
 * @author Thomas Werner
 */
public class AssetServiceImplPeristenceTest {

    private ApplicationFolderServiceMock appFolderSvc;

    private Music music1;
    private Music music2;

    @Before
    public void setUp() throws IOException {
        final AttributionData attributionData = new AttributionData();
        attributionData.setAuthor("Thomas Werner");
        attributionData.setLicenseLink("http://apache.org/license/2.0");
        attributionData.setWebsite("http://retro-carnage.net");

        music1 = new Music();
        music1.setId(UUID.randomUUID().toString());
        music1.setName("TestSong 1");
        music1.setRelativePath("./music/TestSong-1.mp3");
        music1.getTags().add("Music");
        music1.getTags().add("Test");
        music1.setAttributionData(attributionData);

        music2 = new Music();
        music2.setId(UUID.randomUUID().toString());
        music2.setName("TestSong 2");
        music2.setRelativePath("./music/TestSong-2.mp3");
        music2.getTags().add("Test");
        music2.getTags().add("Music");
        music2.setAttributionData(attributionData);

        appFolderSvc = new ApplicationFolderServiceMock();
    }

    @After
    public void tearDown() throws IOException {
        appFolderSvc.cleanUp();
    }

    /**
     * Tests the persistence feature with an empty state.
     *
     * @throws Exception when reading / writing test data fails
     */
    @Test
    public void testEmptyDatabase() throws Exception {
        final AssetServiceImpl assetServiceOut = new AssetServiceImpl(appFolderSvc);
        assetServiceOut.initializeFolderStructure();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        assetServiceOut.saveAssets(baos);

        final AssetServiceImpl assetServiceIn = new AssetServiceImpl(appFolderSvc);
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        assetServiceIn.loadAssets(bais);

        assertEquals(0, assetServiceIn.findAssets(null).size());
    }

    /**
     * Tests the persistence feature with a non-empty state.
     */
    @Test
    public void testPopulatedDatabase() {
        final AssetServiceImpl assetServiceOut = new AssetServiceImpl(appFolderSvc);
        assetServiceOut.initializeFolderStructure();

        try (var musicIn1 = getTestDataInputStream(); var musicIn2 = getTestDataInputStream()) {
            assetServiceOut.addMusic(music1, musicIn1);
            assetServiceOut.addMusic(music2, musicIn2);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assetServiceOut.saveAssets(baos);

            final AssetServiceImpl assetServiceIn = new AssetServiceImpl(appFolderSvc);
            final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            assetServiceIn.loadAssets(bais);

            assertEquals(2, assetServiceIn.findAssets(null).size());
        } catch (Exception ex) {
            fail();
        }

    }

    private static InputStream getTestDataInputStream() {
        return AssetServiceImplPeristenceTest.class.getResourceAsStream("testdata/sample-3s.mp3");
    }

}
