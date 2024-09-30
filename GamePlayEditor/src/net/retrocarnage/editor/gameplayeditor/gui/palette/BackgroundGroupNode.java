package net.retrocarnage.editor.gameplayeditor.gui.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for a group of background elements.
 *
 * @author Thomas Werner
 */
public final class BackgroundGroupNode extends AbstractNode {

    public BackgroundGroupNode() {
        super(Children.create(new BackgroundNodeFactory(), false));
        setDisplayName("Backgrounds");
    }
}
