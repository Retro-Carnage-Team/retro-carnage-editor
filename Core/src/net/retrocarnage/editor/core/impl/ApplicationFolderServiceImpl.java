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
                logger.log(Level.WARNING, "Failed extract workspace archive", e);
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

    private static void unzip(final File inputFile, final Path targetDir) throws IOException, DangerousZipContentException {
        try(final ZipFile zipFile = new ZipFile(inputFile)) {            
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            final Path absoluteTargetDir = targetDir.toAbsolutePath();

            int totalSizeArchive = 0;
            int totalEntryArchive = 0;

            while(entries.hasMoreElements()) {
                totalEntryArchive ++;

                final ZipEntry ze = entries.nextElement();        
                final Path resolvedPath = absoluteTargetDir.resolve(ze.getName()).normalize();
                if (!resolvedPath.startsWith(absoluteTargetDir)) {
                    throw new DangerousZipContentException("ZipEntry with an illegal path: " + ze.getName());
                }
                if (ze.isDirectory()) {
                    Files.createDirectories(resolvedPath);
                } else {
                    Files.createDirectories(resolvedPath.getParent());
                    totalSizeArchive += extractZipEntry(zipFile, ze, resolvedPath.toFile());
                }

                if((totalSizeArchive > THRESHOLD_SIZE)  || (totalEntryArchive > THRESHOLD_ENTRIES)) {
                    throw new DangerousZipContentException("ZIP content exceeds security limits");
                }            
            }
        }
        
    }
    
    private static int extractZipEntry(final ZipFile zipFile, final ZipEntry ze, final File target) 
            throws DangerousZipContentException, IOException {        
        int totalSizeEntry = 0;
        try(
            final var inStream = new BufferedInputStream(zipFile.getInputStream(ze));
            final var outStream = new BufferedOutputStream(new FileOutputStream(target));
        ) {
            int nBytes;
            byte[] buffer = new byte[2048];
            while((nBytes = inStream.read(buffer)) > 0) { 
                outStream.write(buffer, 0, nBytes);
                totalSizeEntry += nBytes;

                final double compressionRatio = (double) totalSizeEntry / ze.getCompressedSize();
                if(compressionRatio > THRESHOLD_RATIO) {
                    throw new DangerousZipContentException("Compression ratio is suspicious.");
                }
            }    
        }
        return totalSizeEntry;        
    }
    
}
