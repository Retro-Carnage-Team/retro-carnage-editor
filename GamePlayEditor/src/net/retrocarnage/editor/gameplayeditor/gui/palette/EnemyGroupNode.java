package net.retrocarnage.editor.gameplayeditor.gui.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for a group of enemy elements.
 *
 * @author Thomas Werner
 */
public class EnemyGroupNode extends AbstractNode {

    public EnemyGroupNode() {
        super(Children.create(new EnemyNodeFactory(), false));
        setDisplayName("Enemies");
    }
}
