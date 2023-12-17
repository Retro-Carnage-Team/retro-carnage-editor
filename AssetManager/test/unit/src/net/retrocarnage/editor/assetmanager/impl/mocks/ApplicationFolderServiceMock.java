package net.retrocarnage.editor.assetmanager.impl.mocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.apache.commons.io.FileUtils;

/**
 * ApplicationFolderServiceMock is a ApplicationFolderService that can be used in unit tests.
 *
 * @author Thomas Werner
 */
public class ApplicationFolderServiceMock extends ApplicationFolderService {

    private final String testFolderPath;

    public ApplicationFolderServiceMock() throws IOException {
        final File testFolder = Files.createTempDirectory("rc-test-").toFile();
        testFolderPath = testFolder.getAbsolutePath();
    }

    @Override
    public Path buildDatabaseFilePath(String fileName) {
        return getApplicationFolder().resolve(fileName);
    }

    @Override
    public Path getApplicationFolder() {
        return Paths.get(testFolderPath);
    }

    public void cleanUp() throws IOException {
        FileUtils.deleteDirectory(new File(testFolderPath));
    }

}
