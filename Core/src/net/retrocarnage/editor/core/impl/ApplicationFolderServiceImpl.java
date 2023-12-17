package net.retrocarnage.editor.core.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.ApplicationFolderService;

/**
 * Implementation of ApplicationFolderService.
 *
 * @author Thomas Werner
 */
public class ApplicationFolderServiceImpl extends ApplicationFolderService {

    private static final String APP_FOLDER_NAME = ".retro-carnage-editor";
    private static final Logger logger = Logger.getLogger(ApplicationFolderServiceImpl.class.getName());

    @Override
    public Path buildDatabaseFilePath(final String fileName) {
        return getApplicationFolder().resolve(fileName);
    }

    @Override
    public Path getApplicationFolder() {
        final String userHome = System.getProperty("user.home");
        return Paths.get(userHome, APP_FOLDER_NAME);
    }

    /**
     * Initializes the application folder. This has to be done once when the application starts for the first time.
     *
     * @throws IOException when one if the IO operations fails
     */
    public void initializeApplicationFolder() throws IOException {
        final File applicationFolder = getApplicationFolder().toFile();
        if (!applicationFolder.exists() && !applicationFolder.mkdir()) {
            logger.log(Level.WARNING, "Failed to create application folder {0}", applicationFolder.getPath());
        }
    }

}
