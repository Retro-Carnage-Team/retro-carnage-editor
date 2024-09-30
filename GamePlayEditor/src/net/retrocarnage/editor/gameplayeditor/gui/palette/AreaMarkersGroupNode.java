package net.retrocarnage.editor.gameplayeditor.gui.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for a group of area markers, like obstacles and mission goals.
 *
 * @author Thomas Werner
 */
public final class AreaMarkersGroupNode extends AbstractNode {

    public AreaMarkersGroupNode() {
        super(Children.create(new AreaMarkersNodeFactory(), false));
        setDisplayName("Area markers");
    }
}
