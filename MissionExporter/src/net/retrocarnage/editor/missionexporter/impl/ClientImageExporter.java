package net.retrocarnage.editor.missionexporter.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Sprite;

/**
 * Exports the client image file.
 *
 * @author Thomas Werner
 */
public class ClientImageExporter {

    private static final Logger logger = Logger.getLogger(ClientImageExporter.class.getName());

    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;

    /**
     * Creates a new instance of ClientImageExporter.
     *
     * @param mission the Mission to export the client image for
     * @param exportFolderStructure definition of the export folder structure
     */
    public ClientImageExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
    }

    /**
     * Copies the image of the client asset to the export folder.
     */
    public void export() {
        final Path imageFile = exportFolderStructure.getClientImageFile().toPath();
        final Sprite sprite = AssetService.getDefault().getSprite(mission.getClient());
        try {
            Files.deleteIfExists(imageFile);
            sprite.getData(Files.newOutputStream(imageFile));
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to export image of mission's client", ex);
        }
    }

}
