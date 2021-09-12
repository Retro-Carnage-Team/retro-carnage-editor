package net.retrocarnage.editor.assetmanager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static final String ASSET_DATABASE_FILENAME = "assetDatabase.xml";
    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        final AssetServiceImpl assetService = ((AssetServiceImpl) AssetService.getDefault());
        assetService.initializeFolderStructure();

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(ASSET_DATABASE_FILENAME);
        if (databaseFile.toFile().exists()) {
            try (final InputStream database = Files.newInputStream(databaseFile, StandardOpenOption.READ)) {
                assetService.loadAssets(database);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read the asset database file", ex.getMessage());
            }
        }

    }

    @Override
    public void close() {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(ASSET_DATABASE_FILENAME);
        try (final OutputStream database = Files.newOutputStream(databaseFile)) {
            final AssetServiceImpl assetService = ((AssetServiceImpl) AssetService.getDefault());
            assetService.saveAssets(database);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to write the asset database file", ex.getMessage());
        }
    }

}
