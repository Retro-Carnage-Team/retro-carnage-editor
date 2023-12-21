package net.retrocarnage.editor.missionexporter.impl.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.Asset;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;

/**
 * Used in unit tests.
 *
 * @author Thomas Werner
 */
public class AssetServiceMock extends AssetService {

    private final Map<String, Music> musicAssets = new HashMap<>();
    private final Map<String, Sprite> spriteAssets = new HashMap<>();

    @Override
    public Collection<Asset<?>> findAssets(String tagFilter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Music getMusic(String id) {
        return musicAssets.get(id);
    }

    @Override
    public void addMusic(Music music, InputStream in) throws IOException {
        musicAssets.put(music.getId(), music);
    }

    @Override
    public void updateMusicInfo(Music music) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateMusicAsset(String id, InputStream in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMusic(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getSprite(String id) {
        return spriteAssets.get(id);
    }

    @Override
    public void addSprite(Sprite sprite, InputStream in) throws IOException {
        spriteAssets.put(sprite.getId(), sprite);
    }

    @Override
    public void updateSpriteInfo(Sprite sprite) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSpriteAsset(String id, InputStream in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeSprite(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<String> getSpriteTags() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
