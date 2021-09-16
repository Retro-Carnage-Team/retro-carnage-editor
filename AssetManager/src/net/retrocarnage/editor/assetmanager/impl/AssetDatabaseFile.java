package net.retrocarnage.editor.assetmanager.impl;

import java.util.ArrayList;
import java.util.List;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;

/**
 * POJO used to serialize / deserialize the asset database.
 *
 * @author Thomas Werner
 */
class AssetDatabaseFile {

    private List<Music> music;
    private List<Sprite> sprites;

    public AssetDatabaseFile() {
        music = new ArrayList<>();
        sprites = new ArrayList<>();
    }

    public List<Music> getMusic() {
        return music;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
    }

}
