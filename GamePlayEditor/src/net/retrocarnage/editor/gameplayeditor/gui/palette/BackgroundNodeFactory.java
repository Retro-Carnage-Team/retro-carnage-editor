package net.retrocarnage.editor.gameplayeditor.gui.palette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.retrocarnage.editor.assetmanager.AssetService;
import static net.retrocarnage.editor.assetmanager.AssetService.TAG_CLIENT;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * Creates GroupNodes for each distinct tag assigned to all known sprite assets.
 *
 * @author Thomas Werner
 */
public class BackgroundNodeFactory extends ChildFactory<String> {

    @Override
    protected boolean createKeys(final List<String> toPopulate) {
        final List<String> sortedEntries = new ArrayList<>(AssetService.getDefault().getSpriteTags());
        sortedEntries.remove(TAG_CLIENT);
        Collections.sort(sortedEntries);
        toPopulate.addAll(sortedEntries);
        return true;
    }

    @Override
    protected Node[] createNodesForKey(final String key) {
        return AssetService.getDefault()
                .findAssets(key).stream()
                .map((asset) -> asset.getId())
                .map((k) -> new SpriteAssetNode(k))
                .toArray(SpriteAssetNode[]::new);
    }

}
