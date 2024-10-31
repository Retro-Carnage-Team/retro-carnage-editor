package net.retrocarnage.editor.core.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.openide.modules.InstalledFileLocator;

/**
 * Implementation of ApplicationFolderService.
 *
 * @author Thomas Werner
 */
public class ApplicationFolderServiceImpl extends ApplicationFolderService {

    private static final String APP_FOLDER_NAME = ".retro-carnage-editor";
    private static final int THRESHOLD_ENTRIES = 50_000;
    private static final int THRESHOLD_SIZE = 1_000_000_000; // 1 GB
    private static final double THRESHOLD_RATIO = 10;    
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
            
            try { 
                unzip(zipArchive, applicationFolder.toPath());
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

    private static void unzip(final File inputFile, final Path targetDir) throws IOException {
        final ZipFile zipFile = new ZipFile(inputFile);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        final Path absoluteTargetDir = targetDir.toAbsolutePath();
        
        int totalSizeArchive = 0;
        int totalEntryArchive = 0;
        
        while(entries.hasMoreElements()) {
            totalEntryArchive ++;
            
            final ZipEntry ze = entries.nextElement();        
            final Path resolvedPath = absoluteTargetDir.resolve(ze.getName()).normalize();
            if (!resolvedPath.startsWith(absoluteTargetDir)) {
                throw new RuntimeException("Entry with an illegal path: " + ze.getName());
            }
            if (ze.isDirectory()) {
                Files.createDirectories(resolvedPath);
            } else {
                Files.createDirectories(resolvedPath.getParent());

                try(
                    final InputStream in = new BufferedInputStream(zipFile.getInputStream(ze));
                    final OutputStream out = new BufferedOutputStream(new FileOutputStream(resolvedPath.toFile()));
                ) {
                    int nBytes = -1;
                    byte[] buffer = new byte[2048];
                    int totalSizeEntry = 0;

                    while((nBytes = in.read(buffer)) > 0) { 
                        out.write(buffer, 0, nBytes);
                        totalSizeEntry += nBytes;
                        totalSizeArchive += nBytes;

                        double compressionRatio = totalSizeEntry / ze.getCompressedSize();
                        if(compressionRatio > THRESHOLD_RATIO) {
                            logger.log(
                                Level.WARNING, 
                                "Ratio between compressed and uncompressed data in Workspace archive is suspicious."
                            );
                            break;
                        }
                    }    
                }
            }
                
            if(totalSizeArchive > THRESHOLD_SIZE) {
                logger.log(
                    Level.WARNING, 
                    "The uncompressed data size is suspicious.",
                    inputFile.getCanonicalPath()
                );                    
                break;
            }

            if(totalEntryArchive > THRESHOLD_ENTRIES) {
                logger.log(
                    Level.WARNING, 
                    "Too many entries in workspace archive: {0}",
                    inputFile.getCanonicalPath()
                );                    
                break;
            }            
        }
    }
    
}
