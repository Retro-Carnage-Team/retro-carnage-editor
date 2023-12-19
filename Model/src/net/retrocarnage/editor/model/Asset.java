package net.retrocarnage.editor.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.apache.commons.io.IOUtils;

/**
 * An asset that can be managed by the AssetManager.
 *
 * @author Thomas Werner
 */
public abstract class Asset<T extends Asset<?>> {

    private static final Logger logger = Logger.getLogger(Asset.class.getName());

    protected final ApplicationFolderService appFolderService;
    private String id;
    private AttributionData attributionData;
    private String name;
    private String relativePath;
    private List<String> tags;

    protected Asset() {
        this.tags = new ArrayList<>();
        this.appFolderService = ApplicationFolderService.getDefault();
    }

    protected Asset(final ApplicationFolderService appFolderService) {
        this.tags = new ArrayList<>();
        this.appFolderService = appFolderService;
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
     * Checks whether or not this Asset has a tag that starts with the given String.
     *
     * @param tag search criteria
     * @return true if this Asset has a matching tag
     */
    public boolean isTagged(final String tag) {
        return tags
                .stream()
                .anyMatch(t -> t.trim().toLowerCase(Locale.ENGLISH).startsWith(tag.trim().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Reads the file associated with this asset.
     *
     * @param out destination
     * @throws IOException
     */
    public void getData(final OutputStream out) throws IOException {
        final Path appFolderPath = appFolderService.getApplicationFolder();
        final Path filePath = Paths.get(appFolderPath.toString(), getRelativePath());
        if (filePath.toFile().exists()) {
            try (final InputStream in = new BufferedInputStream(Files.newInputStream(filePath))) {
                IOUtils.copy(in, out);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read asset: {0}", filePath.toString());
                throw new IOException("Failed to read asset " + filePath.toString(), ex);
            }
        } else {
            logger.log(Level.WARNING, "Missing asset: {0}", filePath.toString());
            throw new IOException("File not found: " + filePath.toString());
        }
    }

    /**
     * Creates a deep copy of this object.
     *
     * @return the copy
     */
    public abstract T deepCopy();

}
