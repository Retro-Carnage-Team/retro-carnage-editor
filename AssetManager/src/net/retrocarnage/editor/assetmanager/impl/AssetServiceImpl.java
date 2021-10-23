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
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.core.ApplicationFolderService;
import net.retrocarnage.editor.model.Asset;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;
import org.apache.commons.io.IOUtils;

/**
 * Implementation of the AssetService.
 *
 * @author Thomas Werner
 */
public class AssetServiceImpl extends AssetService {

    private static final String ASSET_DATABASE_FILENAME = "assetDatabase.xml";

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

    void loadAssets() {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(ASSET_DATABASE_FILENAME);
        if (databaseFile.toFile().exists()) {
            try (final InputStream database = Files.newInputStream(databaseFile, StandardOpenOption.READ)) {
                loadAssets(database);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to read the asset database file", ex.getMessage());
            }
        }
    }

    void saveAssets(OutputStream out) throws IOException {
        assets.save(out);
    }

    void saveAssets() {
        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path databaseFile = appFolderService.buildDatabaseFilePath(ASSET_DATABASE_FILENAME);
        try (final OutputStream database = Files.newOutputStream(databaseFile)) {
            saveAssets(database);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to write the asset database file", ex.getMessage());
        }
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
    public Collection<Asset<?>> findAssets(final String tagFilter) {
        final Collection<Asset<?>> result = new ArrayList<>();
        if (null == tagFilter || tagFilter.isEmpty()) {
            result.addAll(assets.getMusic().values());
            result.addAll(assets.getSprites().values());
        } else {
            final String[] filterTags = tagFilter.split("\\s+");
            for (String filterTag : filterTags) {
                final List<Music> taggedMusic = assets
                        .getMusic()
                        .values()
                        .stream()
                        .filter(m -> m.isTagged(filterTag)).collect(Collectors.toList());
                result.addAll(taggedMusic);
                final List<Sprite> taggedSprites = assets
                        .getSprites()
                        .values()
                        .stream()
                        .filter(m -> m.isTagged(filterTag)).collect(Collectors.toList());
                result.addAll(taggedSprites);
            }
        }
        return result;
    }

    @Override
    public Music getMusic(final String id) {
        return assets.getMusic().get(id);
    }

    @Override
    public void addMusic(final Music music, final InputStream in) throws IOException {
        music.setId(UUID.randomUUID().toString());
        music.setRelativePath(Paths.get(MUSIC_FOLDER_NAME, music.getId() + ".mp3").toString());

        final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
        final Path appFolderPath = appFolderService.getApplicationFolder();
        storeMusicToDisk(in, Paths.get(appFolderPath.toString(), music.getRelativePath()));

        assets.getMusic().put(music.getId(), music.deepCopy());
        saveAssets();
    }

    @Override
    public void updateMusicInfo(final Music music) {
        if (null == getMusic(music.getId())) {
            logger.log(Level.WARNING, "No such asset for given id: {0}", music.getId());
            throw new IllegalArgumentException("No such asset for given id: " + music.getId());
        }
        assets.getMusic().put(music.getId(), music.deepCopy());
        saveAssets();
    }

    @Override
    public void updateMusicAsset(final String id, final InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMusic(final String id) {
        final Music music = assets.getMusic().get(id);
        if (null != music) {
            final ApplicationFolderService appFolderService = ApplicationFolderService.getDefault();
            final Path appFolderPath = appFolderService.getApplicationFolder();
            final File musicFile = Paths.get(appFolderPath.toString(), music.getRelativePath()).toFile();
            if (musicFile.exists() && !musicFile.delete()) {
                musicFile.deleteOnExit();
                logger.warning("Failed to delete music file immediatly. Scheduling delete on exit.");
            }
            assets.getMusic().remove(id);
            saveAssets();
        }
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
        saveAssets();
    }

    @Override
    public void updateSpriteInfo(final Sprite sprite) {
        if (null == getSprite(sprite.getId())) {
            logger.log(Level.WARNING, "No such asset for given id: {0}", sprite.getId());
            throw new IllegalArgumentException("No such asset for given id: " + sprite.getId());
        }
        assets.getSprites().put(sprite.getId(), sprite.deepCopy());
        saveAssets();
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
            saveAssets();
        }
    }

    /**
     * Reads the given data stream and resamples the music to the required sample ratio. Stores the resampled musicto
     * the specified musicPath.
     *
     * @param in music
     * @param spritePath path for the resampled music
     * @throws IOException when things blow up
     */
    private void storeMusicToDisk(final InputStream in, final Path musicPath) throws IOException {
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(musicPath))) {
            // TODO: Resample using JAVE
            IOUtils.copy(in, out);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to add music asset: {0}", musicPath.toString());
            throw new IOException("Failed to add music asset " + musicPath.toString(), ex);
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
            ImageIO.write(getThumbnailImage(spritePath.toFile()), "PNG", out);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to create sprite thumbnail: {0}", spritePath.toString());
            throw new IOException("Failed to create sprite thumbnail " + spritePath.toString(), ex);
        }
    }

    /**
     * Scales the given image to THUMBNAIL_SIZE and returns it.
     *
     * @param inputFile some image file
     * @return a scaled rendered image
     * @throws IOException when things blow up
     */
    public static BufferedImage getThumbnailImage(final File inputFile) throws IOException {
        final BufferedImage sourceImage = ImageIO.read(inputFile);
        final int maxDimension = Math.max(sourceImage.getHeight(), sourceImage.getWidth());
        final float scale = THUMBNAIL_SIZE / (float) maxDimension;
        final int targetWidth = (int) (sourceImage.getWidth() * scale);
        final int targetHeight = (int) (sourceImage.getHeight() * scale);
        final Image scaledImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        final BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        return outputImage;
    }

    @Override
    public Collection<String> getSpriteTags() {
        final Collection<String> result = new HashSet<>();
        assets.getSprites().values().stream().forEach((sprite) -> result.addAll(sprite.getTags()));
        return result;
    }

}
