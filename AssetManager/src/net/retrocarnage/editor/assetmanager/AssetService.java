package net.retrocarnage.editor.assetmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import net.retrocarnage.editor.assetmanager.impl.AssetServiceImpl;
import net.retrocarnage.editor.core.ApplicationFolderService;
import net.retrocarnage.editor.model.Asset;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;

/**
 * Manages the assets used to create a level.
 *
 * @author Thomas Werner
 */
public abstract class AssetService {

    public static final String TAG_CLIENT = "client";

    private static final AssetService assetServiceImpl = new AssetServiceImpl(ApplicationFolderService.getDefault());

    /**
     * Searches for music and sprite assets that match the given criteria.
     *
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of Assets
     */
    public abstract Collection<Asset<?>> findAssets(String tagFilter);

    /**
     * Gets the Music for the given Id.
     *
     * @param id unique identifier of a music asset
     * @return the music asset for the given Id or NULL
     */
    public abstract Music getMusic(String id);

    /**
     * Adds a new Music asset. InputStream is expected to hold MP3 encoded sound data.
     *
     * @param music music data
     * @param in InputStream that provides a MP3 encoded sound
     */
    public abstract void addMusic(Music music, InputStream in) throws IOException;

    public abstract void updateMusicInfo(Music music);

    public abstract void updateMusicAsset(String id, InputStream in) throws IOException;

    public abstract void removeMusic(String id);

    /**
     * Gets the Sprite for the given Id.
     *
     * @param id unique identifier of a sprite asset
     * @return the sprite asset for the given Id or NULL
     */
    public abstract Sprite getSprite(String id);

    /**
     * Adds a new Sprite asset. InputStream is expected to hold GIF, PNG, JPEG, BMP, or WBMP encoded image data.
     *
     * @param sprite sprite data
     * @param in InputStream that provides a PNG encoded image
     */
    public abstract void addSprite(Sprite sprite, InputStream in) throws IOException;

    public abstract void updateSpriteInfo(Sprite sprite);

    public abstract void updateSpriteAsset(String id, InputStream in) throws IOException;

    public abstract void removeSprite(String id);

    /**
     * @return all (distinct) tags assigned sprite assets
     */
    public abstract Collection<String> getSpriteTags();

    /**
     * @return an instance of this service
     */
    public static AssetService getDefault() {
        return assetServiceImpl;
    }

}
