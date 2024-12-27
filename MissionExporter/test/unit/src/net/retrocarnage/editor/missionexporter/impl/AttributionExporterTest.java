package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private static final String ASSET_ID_3 = "asset-3";
    private static final String ASSET_ID_4 = "asset-4";
    private static final String MISSION_ID = "mission-id";
    private static final String MUSIC_ID = "music-id";
    private static final String WEBSITE = "https://www.retro-carnage.net";

    private AttributionExporter attributionExporter;
    private ExportFolderStructure exportFolderStructure;
    private Mission mission;
        
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
        
        final VisualAsset asset3 = new VisualAsset();
        asset3.setAssetId(ASSET_ID_3);
        layer.getVisualAssets().add(asset3);
        
        final VisualAsset asset4 = new VisualAsset();
        asset4.setAssetId(ASSET_ID_4);
        layer.getVisualAssets().add(asset4);

        final File tempDir = Files.createTempDirectory("rc-test-").toFile();
        exportFolderStructure = new ExportFolderStructure(tempDir, mission);

        final AssetService assetService = new AssetServiceMock();

        final AttributionData songAttribution = new AttributionData();
        songAttribution.setAuthor("Antonio Vivaldi");
        songAttribution.setLicenseLink("https://creativecommons.org/publicdomain/zero/1.0/");
        songAttribution.setWebsite(WEBSITE);

        final Music song = new Music();
        song.setId(MUSIC_ID);                       
        song.setAttributionData(songAttribution);
        song.setName("4 seasons");
        assetService.addMusic(song, null);

        final AttributionData sprite1Attribution = new AttributionData();
        sprite1Attribution.setAuthor("John Doe");
        sprite1Attribution.setLicenseLink("https://creativecommons.org/publicdomain/zero/1.0/");        
        
        final Sprite sprite1 = new Sprite();
        sprite1.setId(ASSET_ID_1);
        sprite1.setAttributionData(sprite1Attribution);        
        sprite1.setName("Sprite 1");
        assetService.addSprite(sprite1, null);

        final AttributionData sprite2Attribution = new AttributionData();
        sprite2Attribution.setAuthor("Jane Doe");
        sprite2Attribution.setWebsite(WEBSITE);
        
        final Sprite sprite2 = new Sprite();
        sprite2.setId(ASSET_ID_2);
        sprite2.setAttributionData(sprite2Attribution);
        sprite2.setName("Sprite 2");
        assetService.addSprite(sprite2, null);
        
        final AttributionData sprite3Attribution = new AttributionData();
        sprite3Attribution.setAuthor("Michael Mrotz");
        sprite3Attribution.setLicenseText("THE SOFTWARE IS PROVIDED AS IS.\nIN NO EVENT SHALL THE AUTHORS BE LIABLE.");
        
        final Sprite sprite3 = new Sprite();
        sprite3.setId(ASSET_ID_3);
        sprite3.setAttributionData(sprite3Attribution);
        sprite3.setName("Sprite 3");
        assetService.addSprite(sprite3, null);
        
        final AttributionData sprite4Attribution = new AttributionData();
        sprite4Attribution.setAuthor("Thomas Krumpholz");
        sprite4Attribution.setWebsite(WEBSITE);
        sprite4Attribution.setLicenseText("THE SOFTWARE IS PROVIDED AS IS.\nIN NO EVENT SHALL THE AUTHORS BE LIABLE.");
        
        final Sprite sprite4 = new Sprite();
        sprite4.setId(ASSET_ID_4);
        sprite4.setAttributionData(sprite4Attribution);
        sprite4.setName("Sprite 4");
        assetService.addSprite(sprite4, null);

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
            String mdContent = IOUtils.toString(fis, StandardCharsets.UTF_8);
            String attr1 = "- Sprite 1 by John Doe ([License](https://creativecommons.org/publicdomain/zero/1.0/))\n";
            assertEquals(true, mdContent.contains(attr1));

            String attr2 = "- Sprite 2 by Jane Doe ([Link](https://www.retro-carnage.net))\n";
            assertEquals(true, mdContent.contains(attr2));

            String attr3 = "- Sprite 3 by Michael Mrotz  \n  License:  \n  THE SOFTWARE IS PROVIDED AS IS.  \n  IN NO EVENT SHALL THE AUTHORS BE LIABLE.  \n";
            assertEquals(true, mdContent.contains(attr3));
            
            String attr4 = "- Sprite 4 by Thomas Krumpholz ([Link](https://www.retro-carnage.net))  \n  License:  \n  THE SOFTWARE IS PROVIDED AS IS.  \n  IN NO EVENT SHALL THE AUTHORS BE LIABLE.  \n";
            assertEquals(true, mdContent.contains(attr4));
            
            String attr5 = "- 4 seasons by Antonio Vivaldi ([Link](https://www.retro-carnage.net), [License](https://creativecommons.org/publicdomain/zero/1.0/))\n";
            assertEquals(true, mdContent.contains(attr5));
        }
    }

}