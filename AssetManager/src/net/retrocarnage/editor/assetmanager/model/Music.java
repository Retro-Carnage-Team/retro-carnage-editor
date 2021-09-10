package net.retrocarnage.editor.assetmanager.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.apache.commons.io.IOUtils;

/**
 * A song that can be used as background music for a level.
 *
 * @author Thomas Werner
 */
public class Music {

    private static final Logger logger = Logger.getLogger(Music.class.getName());

    private String id;
    private AttributionData attributionData;
    private String name;
    private String relativePath;
    private List<String> tags;

    public Music() {
        this.tags = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AttributionData getAttributionData() {
        return attributionData;
    }

    public void setAttributionData(AttributionData attributionData) {
        this.attributionData = attributionData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    /**
     * Reads the file associated with this asset.
     *
     * @param out destination
     * @throws IOException
     */
    public void getData(final OutputStream out) throws IOException {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        final Path filePath = Paths.get(appFolderPath.toString(), relativePath);
        if (filePath.toFile().exists()) {
            try (final InputStream in = new BufferedInputStream(Files.newInputStream(filePath))) {
                IOUtils.copy(in, out);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read music asset: {0}", filePath.toString());
                throw new IOException("Failed to read music asset " + filePath.toString(), ex);
            }
        } else {
            logger.log(Level.WARNING, "Missing asset: {0}", filePath.toString());
            throw new IOException("File not found: " + filePath.toString());
        }
    }

}
