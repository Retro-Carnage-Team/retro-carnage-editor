package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.model.Goal;
import net.retrocarnage.editor.renderer.common.TextureFactory;
import org.apache.commons.io.IOUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A node for an obstacle.
 *
 * @author Thomas Werner
 */
public final class GoalNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(GoalNode.class.getName());
    
    private static Image icon = null;
    private static String labelTemplate;

    private final Goal goal;    
    
    public GoalNode() {
        super(Children.LEAF);
        goal = new Goal();
        setDisplayName(getLabel());
    }

    @Override
    public String getHtmlDisplayName() {
        return "<b>Goal</b>";
    }

    @Override
    public Image getIcon(final int type) {
        if (null == icon) {
            final BufferedImage texture = TextureFactory.buildGoalTexture(10);
            final Rectangle anchor = new Rectangle(0, 0, 10, 10);

            final BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2d = (Graphics2D) canvas.getGraphics();
            g2d.setPaint(new TexturePaint(texture, anchor));
            g2d.fill(new Rectangle(100, 100));
            g2d.dispose();
            icon = canvas;
        }
        return icon;
    }

    @Override
    public Transferable drag() throws IOException {
        return goal;
    }

    private String getLabel() {
        if(null == labelTemplate) {
            try(var inStream = GoalNode.class.getResourceAsStream("GoalNodeLabelTemplate.html")) {
                labelTemplate = IOUtils.toString(inStream, "utf-8");
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read label template", ex);
                return "";
            }
        }
        
        
        return labelTemplate;
    }

}
