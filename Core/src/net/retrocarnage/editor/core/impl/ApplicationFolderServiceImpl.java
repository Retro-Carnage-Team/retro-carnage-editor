package net.retrocarnage.editor.core.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.openide.modules.InstalledFileLocator;

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
        if (applicationFolder.exists()) {
            return;
        }
        
        if(!applicationFolder.mkdir()) {
            logger.log(Level.WARNING, "Failed to create application folder {0}", applicationFolder.getPath());
        } else {
            final File zipArchive = getWorkspaceArchive();
            if(zipArchive == null || !zipArchive.exists()) {
                logger.log(Level.WARNING, "Failed locate Workspace archive.");                
                return;
            }
            
            try(
                final FileInputStream fis = new FileInputStream(zipArchive);
                final BufferedInputStream bis = new BufferedInputStream(fis);
            ) { 
                unzip(bis, applicationFolder.toPath());
            } catch (Exception e) {
                logger.log(
                        Level.WARNING, 
                        "Failed extract workspace archive {0} to {1}",
                        new Object[]{zipArchive.getCanonicalPath(), applicationFolder.toString()}
                );
            }
        }
    }
    
    private File getWorkspaceArchive() {
        return InstalledFileLocator.getDefault().locate(
            "editor-workspace.zip",
            "net.retrocarnage.editor.core",
            false
        );
    }

    private static void unzip(final InputStream is, final Path targetDir) throws IOException {
        final Path absoluteTargetDir = targetDir.toAbsolutePath();
        try (ZipInputStream zipIn = new ZipInputStream(is)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                final Path resolvedPath = absoluteTargetDir.resolve(ze.getName()).normalize();
                if (!resolvedPath.startsWith(absoluteTargetDir)) {
                    throw new RuntimeException("Entry with an illegal path: " + ze.getName());
                }
                if (ze.isDirectory()) {
                    Files.createDirectories(resolvedPath);
                } else {
                    Files.createDirectories(resolvedPath.getParent());
                    Files.copy(zipIn, resolvedPath);
                }
            }
        }
    }
    
}
