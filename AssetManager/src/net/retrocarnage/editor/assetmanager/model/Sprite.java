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
 * A sprite that can be used as a visual asset of a level.
 *
 * @author Thomas Werner
 */
public class Sprite {

    private static final Logger logger = Logger.getLogger(Sprite.class.getName());

    private String id;
    private AttributionData attributionData;
    private String relativePath;
    private String relativePathThumbnail;
    private String name;
    private List<String> tags;

    public Sprite() {
        this.tags = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public AttributionData getAttributionData() {
        return attributionData;
    }

    public void setAttributionData(final AttributionData attributionData) {
        this.attributionData = attributionData;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(final String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePathThumbnail() {
        return relativePathThumbnail;
    }

    public void setRelativePathThumbnail(final String relativePathThumbnail) {
        this.relativePathThumbnail = relativePathThumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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
                logger.log(Level.WARNING, "Failed to read sprite asset: {0}", filePath.toString());
                throw new IOException("Failed to read sprite asset " + filePath.toString(), ex);
            }
        } else {
            logger.log(Level.WARNING, "Missing asset: {0}", filePath.toString());
            throw new IOException("File not found: " + filePath.toString());
        }
    }

    /**
     * Reads the file of the thumbnail associated with this asset.
     *
     * @param out destination
     * @throws IOException
     */
    public void getThumbnail(final OutputStream out) throws IOException {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        final Path filePath = Paths.get(appFolderPath.toString(), relativePathThumbnail);
        if (filePath.toFile().exists()) {
            try (final InputStream in = new BufferedInputStream(Files.newInputStream(filePath))) {
                IOUtils.copy(in, out);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read sprite thumbnail: {0}", filePath.toString());
                throw new IOException("Failed to read sprite thumbnail " + filePath.toString(), ex);
            }
        } else {
            logger.log(Level.WARNING, "Missing thumbnail: {0}", filePath.toString());
            throw new IOException("File not found: " + filePath.toString());
        }
    }

}
