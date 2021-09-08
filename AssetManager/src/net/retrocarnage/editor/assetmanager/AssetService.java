package net.retrocarnage.editor.assetmanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
     * Loads the asset database from disk (or some other resource)
     *
     * @param in InputStream containing the assets database
     */
    public abstract void loadAssets(InputStream in) throws IOException;

    /**
     * Saves the asset database to disk (or some other resource)
     *
     * @param out OutputStream to store the asset database
     */
    public abstract void saveAssets(OutputStream out) throws IOException;

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
