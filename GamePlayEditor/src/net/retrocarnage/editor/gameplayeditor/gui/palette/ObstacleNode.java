package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.images.IconProvider;
import net.retrocarnage.editor.model.Obstacle;
import org.apache.commons.io.IOUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A node for an obstacle.
 *
 * @author Thomas Werner
 */
public final class ObstacleNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(ObstacleNode.class.getName());    
    
    private static String labelTemplate;
    private static Image icon = IconUtil.loadIcon(IconProvider.DIAGONAL_ICON.getResourcePath());

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
        return icon;        
    }

    @Override
    public Transferable drag() throws IOException {
        return obstacle;
    }

    private static String getLabel() {
        if(null == labelTemplate) {
            try(var inStream = ObstacleNode.class.getResourceAsStream("ObstacleNodeLabelTemplate.html.template")) {
                labelTemplate = IOUtils.toString(inStream, "utf-8");
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read label template", ex);
                return "";
            }
        }
        
        return labelTemplate;
    }

}
