package net.retrocarnage.editor.assetmanager.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;

/**
 * A database containing various types of assets.
 *
 * @author Thomas Werner
 */
class AssetDatabase {

    private final Map<String, Music> music;
    private final Map<String, Sprite> sprites;

    public AssetDatabase() {
        // We need concorrent maps here as we want to do the media handling outside of the UI thread
        music = new ConcurrentHashMap<>();
        sprites = new ConcurrentHashMap<>();
    }

    public Map<String, Music> getMusic() {
        return music;
    }

    public Map<String, Sprite> getSprites() {
        return sprites;
    }

    /**
     * Restores a persisted state from given source.
     *
     * @param in InputStream containing the persisted state.
     * @throws IOException
     */
    public void load(final InputStream in) throws IOException {
        final ObjectMapper xmlMapper = new XmlMapper();
        final AssetDatabaseFile dataStore = xmlMapper.readValue(in, AssetDatabaseFile.class);

        music.clear();
        for (Music m : dataStore.getMusic()) {
            music.put(m.getId(), m);
        }

        sprites.clear();
        for (Sprite s : dataStore.getSprites()) {
            sprites.put(s.getId(), s);
        }
    }

    /**
     * Save the current state to OutputStream.
     *
     * @param out Stream to write to
     * @throws IOException
     */
    public void save(final OutputStream out) throws IOException {
        final AssetDatabaseFile dataStore = new AssetDatabaseFile();
        dataStore.getMusic().addAll(music.values());
        dataStore.getSprites().addAll(sprites.values());

        new XmlMapper().writeValue(out, dataStore);
    }

}
