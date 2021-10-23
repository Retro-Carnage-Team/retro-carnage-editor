package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.apache.commons.io.IOUtils;

/**
 * A sprite that can be used as a visual asset of a level.
 *
 * @author Thomas Werner
 */
public class Sprite extends Asset<Sprite> implements Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(Sprite.class, "sprite");

    private static final Logger logger = Logger.getLogger(Sprite.class.getName());

    private int width;
    private int height;
    private boolean tile;
    private String relativePathThumbnail;

    /**
     * @return the width in pixel
     */
    public int getWidth() {
        if (width == 0) {
            try {
                return readImage().getWidth();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read image for Sprite " + getId(), ex);
            }
        }
        return width;
    }

    /**
     * Sets the width of this Sprite (in pixels)
     *
     * @param width the new width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height in pixel
     */
    public int getHeight() {
        if (height == 0) {
            try {
                return readImage().getHeight();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read image for Sprite " + getId(), ex);
            }
        }
        return height;
    }

    /**
     * Sets the height of this Sprite (in pixels)
     *
     * @param height the new height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return true if this sprite should be used as tile (instead of being scaled)
     */
    public boolean isTile() {
        return tile;
    }

    /**
     * Sets whether or not this Sprite should be used as tile (instead of being scaled).
     *
     * @param tile true to use as tile
     */
    public void setTile(boolean tile) {
        this.tile = tile;
    }

    /**
     * @return the relative path of the thumbnail file (in the data folder)
     */
    public String getRelativePathThumbnail() {
        return relativePathThumbnail;
    }

    public void setRelativePathThumbnail(final String relativePathThumbnail) {
        this.relativePathThumbnail = relativePathThumbnail;
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

    /**
     * Creates a deep copy of this object.
     *
     * @return the copy
     */
    @Override
    public Sprite deepCopy() {
        try {
            final ObjectMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xmlMapper.writeValueAsString(this), Sprite.class);
        } catch (JsonProcessingException ex) {
            logger.log(Level.SEVERE, "Failed to serialize / deserialize Sprite instance", ex);
            throw new IllegalArgumentException("Sprite can't be serialized / deserialized", ex);
        }
    }

    @JsonIgnore
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(final DataFlavor df) {
        return DATA_FLAVOR == df;
    }

    @Override
    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (DATA_FLAVOR == df) {
            return this;
        }
        throw new UnsupportedFlavorException(df);
    }

    /**
     * Reads and returns the Sprite image.
     *
     * @return a BufferedImage containing the Sprite data
     * @throws IOException in case that the sprite file cannot be found / read
     */
    private BufferedImage readImage() throws IOException {
        final ByteArrayOutputStream bufferWriter = new ByteArrayOutputStream();
        getThumbnail(bufferWriter);

        final byte[] buffer = bufferWriter.toByteArray();
        final ByteArrayInputStream bufferReader = new ByteArrayInputStream(buffer);
        return ImageIO.read(bufferReader);
    }

}
