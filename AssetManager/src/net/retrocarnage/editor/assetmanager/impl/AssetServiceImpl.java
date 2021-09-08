package net.retrocarnage.editor.assetmanager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;
import net.retrocarnage.editor.assetmanager.model.SpriteCategory;

/**
 * Implementation of the AssetService.
 *
 * @author Thomas Werner
 */
public class AssetServiceImpl extends AssetService {

    private final AssetDatabase assets;

    public AssetServiceImpl() {
        assets = new AssetDatabase();
    }

    @Override
    public void loadAssets(InputStream in) throws IOException {
        assets.load(in);
    }

    @Override
    public void saveAssets(OutputStream out) throws IOException {
        assets.save(out);
    }

    @Override
    public Collection<Music> getMusic(String tagFilter) {
        if (null == tagFilter || tagFilter.isEmpty()) {
            return assets.getMusic().values();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Sprite> getSprites(SpriteCategory category, String tagFilter) {
        if (null == category && (null == tagFilter || tagFilter.isEmpty())) {
            return assets.getSprites().values();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addMusic(Music music) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateMusic(Music music) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMusic(Music music) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addSprite(Sprite sprite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSprite(Sprite sprite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeSprite(Sprite sprite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
