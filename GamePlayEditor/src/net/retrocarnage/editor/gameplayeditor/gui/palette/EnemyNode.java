package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.gameplayeditor.images.IconProvider;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemyType;
import org.apache.commons.io.IOUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Defines the visual representation of an Enemy inside the palette.
 *
 * @author Thomas Werner
 */
public final class EnemyNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(EnemyNode.class.getName());    

    private String labelTemplate;
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
        if (null == icon) {
            final String resourceName = String.format("enemy-type-%d.png", enemy.getType());
            final String resourcePath = IconProvider.getResourcePath(resourceName);
            icon = IconUtil.loadIcon(resourcePath);
        }
        return icon;
    }

    @Override
    public Transferable drag() throws IOException {
        try {
            return enemy.clone();
        } catch (CloneNotSupportedException ex) {            
            throw new IOException("Failed to clone enemy template", ex);
        }
    }

    private String getLabel() {
        if(null == labelTemplate) {
            try(var inStream = EnemyNode.class.getResourceAsStream("EnemyNodeLabelTemplate.html.template")) {
                labelTemplate = IOUtils.toString(inStream, "utf-8");
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read label template", ex);
                return "";
            }
        }
                
        return String.format(labelTemplate, type);
    }
}
