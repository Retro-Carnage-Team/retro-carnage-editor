package net.retrocarnage.editor.assetmanager.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    private static final String THUMBNAIL_FOLDER_NAME = "thumbnails";
    private static final int THUMBNAIL_SIZE = 100;                                                                      // pixels width & height
    private static final Logger logger = Logger.getLogger(AssetServiceImpl.class.getName());

    private final AssetDatabase assets;
    private final Path musicFolder;
    private final Path spriteFolder;
    private final Path thumbnailFolder;

    public AssetServiceImpl() {
        assets = new AssetDatabase();

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        musicFolder = Paths.get(appFolderPath.toString(), MUSIC_FOLDER_NAME);
        spriteFolder = Paths.get(appFolderPath.toString(), SPRITE_FOLDER_NAME);
        thumbnailFolder = Paths.get(appFolderPath.toString(), THUMBNAIL_FOLDER_NAME);
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
        if (!thumbnailFolder.toFile().exists() && !thumbnailFolder.toFile().mkdir()) {
            logger.log(Level.WARNING, "Failed to create folder for sprite thumbnails: {0}", thumbnailFolder.toString());
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
    public void updateMusicAsset(final String id, final InputStream in) {
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
    public void addSprite(final Sprite sprite, final InputStream in) throws IOException {
        sprite.setId(UUID.randomUUID().toString());
        sprite.setRelativePath(Paths.get(SPRITE_FOLDER_NAME, sprite.getId() + ".png").toString());
        sprite.setRelativePathThumbnail(Paths.get(THUMBNAIL_FOLDER_NAME, sprite.getId() + ".png").toString());

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();

        final Path spritePath = Paths.get(appFolderPath.toString(), sprite.getRelativePath());
        final Path thumbnailPath = Paths.get(appFolderPath.toString(), sprite.getRelativePathThumbnail());
        storeSpriteToDisk(in, spritePath, thumbnailPath);
        assets.getSprites().put(sprite.getId(), sprite.deepCopy());
    }

    @Override
    public void updateSpriteInfo(final Sprite sprite) {
        if (null == getSprite(sprite.getId())) {
            logger.log(Level.WARNING, "No such asset for given id: {0}", sprite.getId());
            throw new IllegalArgumentException("No such asset for given id: " + sprite.getId());
        }
        assets.getSprites().put(sprite.getId(), sprite.deepCopy());
    }

    @Override
    public void updateSpriteAsset(final String id, final InputStream in) throws IOException {
        final Sprite sprite = getSprite(id);
        if (null == sprite) {
            logger.log(Level.WARNING, "No such asset for given id: {0}", id);
            throw new IllegalArgumentException("No such asset for given id: " + id);
        }

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();

        final Path spritePath = Paths.get(appFolderPath.toString(), sprite.getRelativePath());
        final Path thumbnailPath = Paths.get(appFolderPath.toString(), sprite.getRelativePathThumbnail());
        storeSpriteToDisk(in, spritePath, thumbnailPath);
    }

    @Override
    public void removeSprite(final String id) {
        final Sprite sprite = assets.getSprites().get(id);
        if (null != sprite) {
            final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
            final Path appFolderPath = appFolderService.getApplicationFolder();
            final File spriteFile = Paths.get(appFolderPath.toString(), sprite.getRelativePath()).toFile();
            if (spriteFile.exists() && !spriteFile.delete()) {
                spriteFile.deleteOnExit();
                logger.warning("Failed to delete sprite file immediatly. Scheduling delete on exit.");
            }
            final File thumbnailFile = Paths.get(appFolderPath.toString(), sprite.getRelativePathThumbnail()).toFile();
            if (thumbnailFile.exists() && !thumbnailFile.delete()) {
                thumbnailFile.deleteOnExit();
                logger.warning("Failed to delete thumbnail file immediatly. Scheduling delete on exit.");
            }
            assets.getSprites().remove(id);
        }
    }

    /**
     * Reads the given data stream and converts the image to PNG. Stores the transcoded image to the specified
     * spritePath. Scales the image to THUMBNAIL_SIZE and stores it to the specified thumbnailPath.
     *
     * @param in image
     * @param spritePath path for the transcoded sprite image
     * @param thumbnailPath path for the scaled sprite thumbnail image
     * @throws IOException when things blow up
     */
    private void storeSpriteToDisk(
            final InputStream in,
            final Path spritePath,
            final Path thumbnailPath
    ) throws IOException {
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(spritePath))) {
            final BufferedImage sourceImage = ImageIO.read(in);
            final int targetWidth = sourceImage.getWidth();
            final int targetHeight = sourceImage.getHeight();
            final BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            outputImage.getGraphics().drawImage(sourceImage, 0, 0, null);
            ImageIO.write(outputImage, "PNG", out);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to add sprite asset: {0}", spritePath.toString());
            throw new IOException("Failed to add sprite asset " + spritePath.toString(), ex);
        }

        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(thumbnailPath))) {
            final BufferedImage sourceImage = ImageIO.read(spritePath.toFile());
            final int maxDimension = Math.max(sourceImage.getHeight(), sourceImage.getWidth());
            final float scale = THUMBNAIL_SIZE / (float) maxDimension;
            final int targetWidth = (int) (sourceImage.getWidth() * scale);
            final int targetHeight = (int) (sourceImage.getHeight() * scale);
            final Image scaledImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            final BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
            ImageIO.write(outputImage, "PNG", out);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to create sprite thumbnail: {0}", spritePath.toString());
            throw new IOException("Failed to create sprite thumbnail " + spritePath.toString(), ex);
        }
    }

}
