package net.retrocarnage.editor.assetmanager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;
import net.retrocarnage.editor.assetmanager.model.SpriteCategory;
import net.retrocarnage.editor.core.ApplicationFolderService;

/**
 * Implementation of the AssetService.
 *
 * @author Thomas Werner
 */
public class AssetServiceImpl extends AssetService {

    private static final String MUSIC_FOLDER_NAME = "music";
    private static final String SPRITE_FOLDER_NAME = "sprites";
    private static final Logger logger = Logger.getLogger(AssetServiceImpl.class.getName());

    private final AssetDatabase assets;
    private final Path musicFolder;
    private final Path spriteFolder;

    public AssetServiceImpl() {
        assets = new AssetDatabase();

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        musicFolder = Paths.get(appFolderPath.toString(), MUSIC_FOLDER_NAME);
        spriteFolder = Paths.get(appFolderPath.toString(), SPRITE_FOLDER_NAME);
    }

    void loadAssets(InputStream in) throws IOException {
        assets.load(in);
    }

    void saveAssets(OutputStream out) throws IOException {
        assets.save(out);
    }

    void initializeFolderStructure() {
        if (!musicFolder.toFile().exists() && !musicFolder.toFile().mkdir()) {
            logger.log(Level.WARNING, "Failed to create folder for music assets: {0}", musicFolder.toString());
        }
        if (!spriteFolder.toFile().exists() && !spriteFolder.toFile().mkdir()) {
            logger.log(Level.WARNING, "Failed to create folder for sprite assets: {0}", spriteFolder.toString());
        }
    }

    @Override
    public Collection<Music> findMusic(final String tagFilter) {
        if (null == tagFilter || tagFilter.isEmpty()) {
            return assets.getMusic().values();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Music getMusic(final String id) {
        return assets.getMusic().get(id);
    }

    @Override
    public void addMusic(final Music music, final InputStream in) {
        // TODO: Create copy of asset
        // TODO: Update music with relative path
        assets.getMusic().put(music.getId(), music);
    }

    @Override
    public void updateMusicInfo(final Music music) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateMusicAsset(final InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMusic(final String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Sprite> findSprites(final SpriteCategory category, final String tagFilter) {
        if (null == category && (null == tagFilter || tagFilter.isEmpty())) {
            return assets.getSprites().values();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getSprite(final String id) {
        return assets.getSprites().get(id);
    }

    @Override
    public void addSprite(final Sprite sprite, final InputStream in) {
        // TODO: Create copy of asset
        // TODO: Update sprite with relative path
        assets.getSprites().put(sprite.getId(), sprite);
    }

    @Override
    public void updateSpriteInfo(final Sprite sprite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSpriteAsset(final InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeSprite(final String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Path getMusicFolder() {
        return musicFolder;
    }

    public Path getSpriteFolder() {
        return spriteFolder;
    }

}
