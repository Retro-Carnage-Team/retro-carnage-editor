package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemyType;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Defines the visual representation of an Enemy inside the palette.
 *
 * @author Thomas Werner
 */
public final class EnemyNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(EnemyNode.class.getName());
    private static final String ICON_PATH = "/net/retrocarnage/editor/gameplayeditor/images/enemy-type-%d.png";
    private Image icon = null;

    private final Enemy enemy;
    private final String type;

    public EnemyNode(final Enemy enemy) {
        super(Children.LEAF);

        this.enemy = enemy;
        final EnemyType enemyType = EnemyType.findByValue(enemy.getType());
        type = (null != enemyType ? enemyType.getName() : "");

        setDisplayName(getLabel());
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>" + type + "</b>";
    }

    @Override
    public Image getIcon(final int type) {
        try {
            if (null == icon) {
                icon = ImageIO.read(EnemyNode.class.getResource(String.format(ICON_PATH, enemy.getType())));
            }
            return icon;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to read thumbnail for enemy", ex);
            return null;
        }
    }

    @Override
    public Transferable drag() throws IOException {
        try {
            return enemy.clone();
        } catch (CloneNotSupportedException ex) {
            logger.log(Level.SEVERE, "Failed to clone enemy template", ex);
            throw new IOException("Failed to clone enemy template", ex);
        }
    }

    private String getLabel() {
        return new StringBuilder()
                .append("<html>")
                .append("   <table cellspacing=\"0\" cellpadding=\"1\">")
                .append("       <tr>")
                .append("           <td><b>Type</b></td>")
                .append("           <td>").append(type).append("</td>")
                .append("       </tr>")
                .append("   </table>")
                .append("</html>")
                .toString();
    }
}
