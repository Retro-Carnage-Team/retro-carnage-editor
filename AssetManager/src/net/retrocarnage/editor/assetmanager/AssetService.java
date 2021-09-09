package net.retrocarnage.editor.assetmanager;

import java.util.Collection;
import net.retrocarnage.editor.assetmanager.impl.AssetServiceImpl;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;
import net.retrocarnage.editor.assetmanager.model.SpriteCategory;
import org.openide.util.Lookup;

/**
 * Manages the assets used to create a level.
 *
 * @author Thomas Werner
 */
public abstract class AssetService {

    /**
     * Searches for music assets that match the given criteria.
     *
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of Music assets
     */
    public abstract Collection<Music> getMusic(String tagFilter);

    /**
     * Searches for sprite assets that match the given criteria.
     *
     * @param category a SpriteCategory or NULL
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of sprite assets
     */
    public abstract Collection<Sprite> getSprites(SpriteCategory category, String tagFilter);

    public abstract void addMusic(Music music);

    public abstract void updateMusic(Music music);

    public abstract void removeMusic(Music music);

    public abstract void addSprite(Sprite sprite);

    public abstract void updateSprite(Sprite sprite);

    public abstract void removeSprite(Sprite sprite);

    /**
     * @return an instance of this service
     */
    public static AssetService getDefault() {
        AssetService assetService = Lookup.getDefault().lookup(AssetService.class);
        if (null == assetService) {
            assetService = new AssetServiceImpl();
        }
        return assetService;
    }

}
