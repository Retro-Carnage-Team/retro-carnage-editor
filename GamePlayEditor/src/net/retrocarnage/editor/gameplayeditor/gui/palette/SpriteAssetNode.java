package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Sprite;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Defines the visual representation of a Sprite inside the palette.
 *
 * @author Thomas Werner
 */
public class SpriteAssetNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(SpriteAssetNode.class.getName());

    private final Sprite sprite;

    public SpriteAssetNode(final String id) {
        super(Children.LEAF);
        sprite = AssetService.getDefault().getSprite(id);
        setDisplayName(getLabel());
    }

    @Override
    public String getHtmlDisplayName() {
        // TODO: It would be great to have additional information about the size (x * y) of the sprite
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
        return new StringBuilder()
                .append("<html>")
                .append("   <table cellspacing=\"0\" cellpadding=\"1\">")
                .append("       <tr>")
                .append("           <td><b>Name</b></td>")
                .append("           <td>").append(sprite.getName()).append("</td>")
                .append("       </tr>")
                // TODO: width, height, size (in bytes)
                .append("   </table>")
                .append("</html>")
                .toString();
    }

}
