package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.model.Obstacle;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A node for an obstacle.
 *
 * @author Thomas Werner
 */
public class ObstacleNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(ObstacleNode.class.getName());
    private static final String ICON_PATH = "/net/retrocarnage/editor/gameplayeditor/images/diagonal.png";

    private static Image icon = null;

    private final Obstacle obstacle;

    public ObstacleNode() {
        super(Children.LEAF);
        obstacle = new Obstacle();
        obstacle.setBulletStopper(true);
        obstacle.setExplosiveStopper(false);
        setDisplayName(getLabel());
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Obstacle</b>";
    }

    @Override
    public Image getIcon(final int type) {
        try {
            if (null == icon) {
                icon = ImageIO.read(ObstacleNode.class.getResource(ICON_PATH));
            }
            return icon;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to read thumbnail for obstacle", ex);
            return null;
        }
    }

    @Override
    public Transferable drag() throws IOException {
        return obstacle;
    }

    private String getLabel() {
        return new StringBuilder()
                .append("<html>")
                .append("   <table cellspacing=\"0\" cellpadding=\"1\">")
                .append("       <tr>")
                .append("           <td><b>Type</b></td>")
                .append("           <td>").append("Obstacle").append("</td>")
                .append("       </tr>")
                .append("   </table>")
                .append("</html>")
                .toString();
    }

}
