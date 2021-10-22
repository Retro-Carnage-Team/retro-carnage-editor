package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.List;
import net.retrocarnage.editor.assetmanager.AssetService;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates SpriteAssetNodes for each Sprite that has a given tag.
 *
 * @author Thomas Werner
 */
public class SpriteAssetNodeFactory extends ChildFactory<String> {

    private final String groupName;

    public SpriteAssetNodeFactory(final String groupName) {
        this.groupName = groupName;
    }

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        AssetService.getDefault()
                .findAssets(groupName).stream()
                .map((asset) -> asset.getId())
                .forEach((s) -> toPopulate.add(s));
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        return new Node[]{new SpriteAssetNode(key)};
    }

}
