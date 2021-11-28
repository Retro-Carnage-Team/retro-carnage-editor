package net.retrocarnage.editor.gameplayeditor.gui.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * A Node for a group of Sprite assets.
 *
 * @author Thomas Werner
 */
public class SpriteGroupNode extends AbstractNode {

    public SpriteGroupNode(final String groupName) {
        super(Children.create(new SpriteAssetNodeFactory(groupName), false));
        setDisplayName(groupName);
    }

}
