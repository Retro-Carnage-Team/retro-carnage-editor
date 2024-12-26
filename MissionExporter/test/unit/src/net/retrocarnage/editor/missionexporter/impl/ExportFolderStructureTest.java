package net.retrocarnage.editor.missionexporter.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.missionexporter.impl.mock.AssetServiceMock;
import net.retrocarnage.editor.model.Location;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for ExportFolderStructure.
 * 
 * @author Thomas Werner
 */
public class ExportFolderStructureTest {
    
    private Path fixedLocation;
    private Mission mission;
    private Path tempFolder;
    
    @Before
    public void setUp() throws IOException {
        mission = buildMission();
        fixedLocation = Path.of("/home/tom/retro-carnage");
        tempFolder = Files.createTempDirectory("rce-ut-");
    }
    
    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(tempFolder.toFile());
    }

    /**
     * Test of getRootFolder method, of class ExportFolderStructure.
     */
    @Test
    public void testGetRootFolder() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getRootFolder();
        assertEquals("/home/tom/retro-carnage", result.toString());
    }

    /**
     * Test of getMissionFile method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMissionFile() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getMissionFile();
                
        assertEquals("/home/tom/retro-carnage/missions/Antartica.json", result.getAbsolutePath());
    }

    /**
     * Test of getMissionAttributionFile method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMissionAttributionFile() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getMissionAttributionFile();
        
        assertEquals("/home/tom/retro-carnage/Antartica-attributions.md", result.getAbsolutePath());
    }

    /**
     * Test of prepareFolderStructure method, of class ExportFolderStructure.
     */
    @Test
    public void testPrepareFolderStructure() {
        final ExportFolderStructure efs = new ExportFolderStructure(tempFolder.toFile(), mission);
        efs.prepareFolderStructure();
        
        assertEquals(true, efs.getClientsFolder().exists());
        assertEquals(true, efs.getMissionBackgroundFolder().exists());
        assertEquals(true, efs.getMissionsFolder().exists());
        assertEquals(true, efs.getMusicFolder().exists());
    }

    /**
     * Test of getMissionBackgroundFolder method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMissionBackgroundFolder() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getMissionBackgroundFolder();
        
        assertEquals("/home/tom/retro-carnage/images/levels/Antartica", result.getAbsolutePath());
    }

    /**
     * Test of getMissionsFolder method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMissionsFolder() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getMissionsFolder();
        
        assertEquals("/home/tom/retro-carnage/missions", result.getAbsolutePath());
    }

    /**
     * Test of getBackgroundImageFile method, of class ExportFolderStructure.
     */
    @Test
    public void testGetBackgroundImageFile() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getBackgroundImageFile(6, 0);
        
        assertEquals("/home/tom/retro-carnage/images/levels/Antartica/6-0.png", result.getAbsolutePath());
    }

    /**
     * Test of getBackgroundImageRelativePath method, of class ExportFolderStructure.
     */
    @Test
    public void testGetBackgroundImageRelativePath() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final String result = efs.getBackgroundImageRelativePath(4, 12);
        
        assertEquals("images/levels/Antartica/4-12.png", result);
    }

    /**
     * Test of getClientImageRelativePath method, of class ExportFolderStructure.
     */
    @Test
    public void testGetClientImageRelativePath() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final String result = efs.getClientImageRelativePath();
        
        assertEquals("images/clients/Antartica.png", result);
    }

    /**
     * Test of getClientImageFile method, of class ExportFolderStructure.
     */
    @Test
    public void testGetClientImageFile() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getClientImageFile();
        
        assertEquals("/home/tom/retro-carnage/images/clients/Antartica.png", result.getAbsolutePath());
    }

    /**
     * Test of getClientsFolder method, of class ExportFolderStructure.
     */
    @Test
    public void testGetClientsFolder() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getClientsFolder();
        
        assertEquals("/home/tom/retro-carnage/images/clients", result.getAbsolutePath());
    }

    /**
     * Test of getMusicRelativePath method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMusicRelativePath() throws IOException {
        final Music song = new Music();
        song.setId(mission.getSong());
        song.setName("Ave maria?!?");
        
        final AssetService assetService = new AssetServiceMock();
        assetService.addMusic(song, null);
        
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission, assetService);
        final String result = efs.getMusicRelativePath();
        
        assertEquals("sounds/music/Ave maria___.mp3", result);
    }

    /**
     * Test of getMusicFile method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMusicFile() throws IOException {
        final Music song = new Music();
        song.setId(mission.getSong());
        song.setName("Reign in blood");
        
        final AssetService assetService = new AssetServiceMock();
        assetService.addMusic(song, null);
        
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission, assetService);
        final File result = efs.getMusicFile();
        
        assertEquals("/home/tom/retro-carnage/sounds/music/Reign in blood.mp3", result.getAbsolutePath());
    }

    /**
     * Test of getMusicFolder method, of class ExportFolderStructure.
     */
    @Test
    public void testGetMusicFolder() {
        final ExportFolderStructure efs = new ExportFolderStructure(fixedLocation.toFile(), mission);
        final File result = efs.getMusicFolder();
        
        assertEquals("/home/tom/retro-carnage/sounds/music", result.getAbsolutePath());
    }
    
    private static Mission buildMission() {
        Mission result = new Mission();
        result.setId("912739324ß02346");
        result.setName("Antartica");
        result.setClient("Robert Falcon Scott");
        result.setLocation(new Location(23, 38));
        result.setBriefing("It's cold. Get me out of here.");
        result.setReward(500);
        result.setSegments(Collections.emptyList());
        result.setSong("23409466286723");
        return result;
    }
    
}
