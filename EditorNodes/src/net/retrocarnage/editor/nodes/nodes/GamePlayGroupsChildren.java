package net.retrocarnage.editor.nodes.nodes;

import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * A GamePlayNode contains various kinds of elements. This class builds these groups as children of the GamePlayNode.
 *
 * @author Thomas Werner
 */
public class GamePlayGroupsChildren extends Children.Keys<String> {

    private static final String KEY = "key";
    private final LayerController layerController;

    public GamePlayGroupsChildren(final LayerController layerController) {
        this.layerController = layerController;
    }

    @Override
    protected void addNotify() {
        setKeys(new String[]{KEY});
    }

    @Override
    protected Node[] createNodes(final String key) {
        return new Node[]{new LayersNode(layerController)};
    }
}
