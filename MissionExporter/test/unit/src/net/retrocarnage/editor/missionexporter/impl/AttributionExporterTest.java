package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.missionexporter.impl.mock.AssetServiceMock;
import net.retrocarnage.editor.missionexporter.impl.mock.MissionServiceMock;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit test for AttributionExporter.
 *
 * @author Thomas Werner
 */
public class AttributionExporterTest {

    private static final String ASSET_ID_1 = "asset-1";
    private static final String ASSET_ID_2 = "asset-2";
    private static final String MISSION_ID = "mission-id";
    private static final String MUSIC_ID = "music-id";

    private AttributionExporter attributionExporter;
    private ExportFolderStructure exportFolderStructure;
    private Mission mission;
    private Music song;
    private Sprite sprite1;
    private Sprite sprite2;

    @Before
    public void setUp() throws IOException {

        mission = new Mission();
        mission.setId(MISSION_ID);
        mission.setName("AttributionTest");
        mission.setSong(MUSIC_ID);

        final GamePlay gamePlay = new GamePlay(MISSION_ID);
        final Layer layer = new Layer();
        gamePlay.getLayers().add(layer);

        final VisualAsset asset1 = new VisualAsset();
        asset1.setAssetId(ASSET_ID_1);
        layer.getVisualAssets().add(asset1);

        final VisualAsset asset2 = new VisualAsset();
        asset2.setAssetId(ASSET_ID_2);
        layer.getVisualAssets().add(asset2);

        final File tempDir = Files.createTempDirectory("rc-test-").toFile();
        exportFolderStructure = new ExportFolderStructure(tempDir, mission);

        final AssetService assetService = new AssetServiceMock();

        song = new Music();
        song.setId(MUSIC_ID);
        song.setAttributionData(new AttributionData() {
                {
                    setAuthor("Antonio Vivaldi");
                    setLicenseLink("https://creativecommons.org/publicdomain/zero/1.0/");
                    setWebsite("https://www.retro-carnage.net");
                }
        });
        song.setName("4 seasons");
        assetService.addMusic(song, null);


        sprite1 = new Sprite();
        sprite1.setId(ASSET_ID_1);
        sprite1.setAttributionData(new AttributionData() {
                {
                    setAuthor("John Doe");
                    setLicenseLink("https://creativecommons.org/publicdomain/zero/1.0/");
                }
        });
        sprite1.setName("Sprite 1");
        assetService.addSprite(sprite1, null);

        sprite2 = new Sprite();
        sprite2.setId(ASSET_ID_2);
        sprite2.setAttributionData(new AttributionData() {
                {
                    setAuthor("Jane Doe");
                    setWebsite("https://www.retro-carnage.net");
                }
        });
        sprite2.setName("Sprite 2");
        assetService.addSprite(sprite2, null);

        final MissionService missionService = new MissionServiceMock(mission, gamePlay);
        attributionExporter = new AttributionExporter(assetService, mission, exportFolderStructure, missionService);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(exportFolderStructure.getRootFolder());
    }

    @Test
    public void testRun() throws IOException {
        attributionExporter.export();

        final File attributionFile = exportFolderStructure.getMissionAttributionFile();
        assertEquals(true, attributionFile.exists());
        assertEquals(true, attributionFile.getAbsolutePath().contains(mission.getName()));
        assertEquals(true, attributionFile.getAbsolutePath().endsWith(".md"));

        try(var fis = new FileInputStream(attributionFile)) {
            String mdContent = IOUtils.toString(fis, Charset.forName("utf-8"));
            String attr1 = "* Sprite 1 by John Doe ([License](https://creativecommons.org/publicdomain/zero/1.0/))\n";
            assertEquals(true, mdContent.contains(attr1));

            String attr2 = "* Sprite 2 by Jane Doe ([Link](https://www.retro-carnage.net))\n";
            assertEquals(true, mdContent.contains(attr2));

            String attr3 = "* 4 seasons by Antonio Vivaldi ([Link](https://www.retro-carnage.net), [License](https://creativecommons.org/publicdomain/zero/1.0/))\n";
            assertEquals(true, mdContent.contains(attr3));
        }
    }

}