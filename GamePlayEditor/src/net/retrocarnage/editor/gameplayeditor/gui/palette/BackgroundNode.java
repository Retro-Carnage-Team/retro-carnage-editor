package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.assetmanager.AssetServiceFactory;
import net.retrocarnage.editor.model.Sprite;
import org.apache.commons.io.IOUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Defines the visual representation of a Sprite inside the palette.
 *
 * @author Thomas Werner
 */
public final class BackgroundNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(BackgroundNode.class.getName());
    private static String LABEL_TEMPLATE;

    private final Sprite sprite;

    static {
        try(var inStream = BackgroundNode.class.getResourceAsStream("BackgroundNodeLabelTemplate.html")) {
            LABEL_TEMPLATE = IOUtils.toString(inStream, "utf-8");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to read label template", ex);
            LABEL_TEMPLATE = "";
        }
    }
    
    public BackgroundNode(final String id) {
        super(Children.LEAF);
        sprite = AssetServiceFactory.buildAssetService().getSprite(id);
        setDisplayName(getLabel());
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + sprite.getName() + "</b>";
    }

    @Override
    public Image getIcon(final int type) {
        try {
            final ByteArrayOutputStream bufferWriter = new ByteArrayOutputStream();
            sprite.getThumbnail(bufferWriter);

            final byte[] buffer = bufferWriter.toByteArray();
            final ByteArrayInputStream bufferReader = new ByteArrayInputStream(buffer);
            return ImageIO.read(bufferReader);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to read thumbnail for Sprite " + sprite.getId(), ex);
            return null;
        }
    }

    @Override
    public Transferable drag() throws IOException {
        return sprite;
    }

    private String getLabel() {
        return String.format(
                LABEL_TEMPLATE, 
                sprite.getName(),
                sprite.isTile() ? "Tile" : "Sprite",
                sprite.getTags().stream().reduce("", (t, u) -> t.isEmpty() ? u : t + ", " + u),
                sprite.getWidth(),
                sprite.getHeight()
        );
    }

}
