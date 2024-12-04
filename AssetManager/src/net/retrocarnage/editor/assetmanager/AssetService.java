package net.retrocarnage.editor.assetmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import net.retrocarnage.editor.model.Asset;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;

/**
 * Manages the assets used to create a level.
 *
 * @author Thomas Werner
 */
public interface AssetService {

    public static final String TAG_CLIENT = "client";

    /**
     * Searches for music and sprite assets that match the given criteria.
     *
     * @param tagFilter a search term containing zero, one or more tags. Can be NULL.
     * @return a List of Assets
     */
    Collection<Asset<?>> findAssets(String tagFilter);

    /**
     * Gets the Music for the given Id.
     *
     * @param id unique identifier of a music asset
     * @return the music asset for the given Id or NULL
     */
    Music getMusic(String id);

    /**
     * Adds a new Music asset. InputStream is expected to hold MP3 encoded sound data.
     *
     * @param music music data
     * @param in InputStream that provides a MP3 encoded sound
     */
    void addMusic(Music music, InputStream in) throws IOException;

    void updateMusicInfo(Music music);

    void updateMusicAsset(String id, InputStream in) throws IOException;

    void removeMusic(String id);

    /**
     * Gets the Sprite for the given Id.
     *
     * @param id unique identifier of a sprite asset
     * @return the sprite asset for the given Id or NULL
     */
    Sprite getSprite(String id);

    /**
     * Adds a new Sprite asset. InputStream is expected to hold GIF, PNG, JPEG, BMP, or WBMP encoded image data.
     *
     * @param sprite sprite data
     * @param in InputStream that provides a PNG encoded image
     */
    void addSprite(Sprite sprite, InputStream in) throws IOException;

    void updateSpriteInfo(Sprite sprite);

    void updateSpriteAsset(String id, InputStream in) throws IOException;

    void removeSprite(String id);

    /**
     * @return all (distinct) tags assigned sprite assets
     */
    Collection<String> getSpriteTags();

    void initializeFolderStructure();
    
    void loadAssets();
    
}
