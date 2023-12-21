package net.retrocarnage.editor.missionexporter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.GamePlay;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;
import net.retrocarnage.editor.model.VisualAsset;

// TODO: Currently only links to license information get exported.
//       The item will be ignored when the license is specified as text.

/**
 * Creates the Attribution.md document for an exported mission. This file contains attributions for all assets used in
 * the mission.
 *
 * @author Thomas Werner
 */
public class AttributionExporter {

    private static final Logger logger = Logger.getLogger(AttributionExporter.class.getName());
    private static final String TEMPLATE = "/net/retrocarnage/editor/missionexporter/templates/attribution-template.md";

    private final AssetService assetService;
    private final ExportFolderStructure exportFolderStructure;
    private final Mission mission;
    private final MissionService missionService;
    private String imageAttributions = null;
    private String musicAttributions = null;

    public AttributionExporter(final Mission mission, final ExportFolderStructure exportFolderStructure) {
        this.assetService = AssetService.getDefault();
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
        this.missionService = MissionService.getDefault();
    }

    public AttributionExporter(final AssetService assetService, final Mission mission,
                               final ExportFolderStructure exportFolderStructure, MissionService missionService) {
        this.assetService = assetService;
        this.exportFolderStructure = exportFolderStructure;
        this.mission = mission;
        this.missionService = missionService;
    }

    public void run() {
        final File mdFile = exportFolderStructure.getMissionAttributionFile();
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile, Charset.forName("utf-8")))) {
            for (String line : readTemplate()) {
                writer.write(replacePlaceholders(line));
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to write attribution file for mission " + mission.getName(), ex);
        }
    }

    private List<String> readTemplate() throws IOException {
        final List<String> result = new ArrayList<>();
        final InputStream templateStream = AttributionExporter.class.getResourceAsStream(TEMPLATE);
        try (final var reader = new BufferedReader(new InputStreamReader(templateStream, Charset.forName("utf-8")))) {
            String line;
            while (null != (line = reader.readLine())) {
                result.add(line);
            }
        }
        return result;
    }

    private String replacePlaceholders(final String inputLine) {
        String result = inputLine;
        if(result.contains("<MISSION>")) {
            result = result.replace("<MISSION>", mission.getName());
        }
        if(result.contains("<IMAGES>")) {
            result = result.replace("<IMAGES>", buildImageAttributions());
        }
        if(result.contains("<MUSIC>")) {
            result = result.replace("<MUSIC>", buildMusicAttributions());
        }
        return result;
    }

    private String buildImageAttributions() {
        if (null == imageAttributions) {
            final StringBuilder sbuilder = new StringBuilder();
            for(Sprite sprite: getSprites()) {
                final AttributionData attribution = sprite.getAttributionData();
                final String mdString = buildAttribution(sprite.getName(),
                                                         attribution.getAuthor(),
                                                         attribution.getWebsite(),
                                                         attribution.getLicenseLink());
                sbuilder.append(mdString).append("\n");
            }
            imageAttributions = sbuilder.toString();
        }
        return imageAttributions;
    }

    private List<Sprite> getSprites() {
        final Map<String, Sprite> sprites = new HashMap<>();
        final GamePlay gamePlay = missionService.loadGamePlay(mission.getId());
        for(Layer layer: gamePlay.getLayers()) {
            for(VisualAsset asset: layer.getVisualAssets()) {
                sprites.put(asset.getAssetId(), assetService.getSprite(asset.getAssetId()));
            }
        }
        final List<Sprite> result = new ArrayList<>(sprites.values());
        Collections.sort(result, (t1, t2) -> {
            return t1.getName().compareTo(t2.getName());
        });
        return result;
    }

    private String buildMusicAttributions() {
        if (null == musicAttributions && mission.getSong() != null && !mission.getSong().isEmpty()) {
            final Music music = assetService.getMusic(mission.getSong());
            final AttributionData attribution = music.getAttributionData();
            musicAttributions = buildAttribution(music.getName(),
                                                 attribution.getAuthor(),
                                                 attribution.getWebsite(),
                                                 attribution.getLicenseLink()) + "\n";
        }
        return musicAttributions;
    }

    /**
     * Creates a Markdown formatted list element for a singe attribution
     *
     * @param name the element created by another author
     * @param author the name of the author
     * @param website website of the author (or source)
     * @param licenseLink link to the license of this element
     * @return markdown formatted list element
     */
    private String buildAttribution(final String name, final String author, final String website,
                                    final String licenseLink) {
        if (null == name || name.isBlank() || null == author || author.isBlank())
            return "";

        String result = String.format("* %s by %s", name, author);

        String links = "";
        boolean linkPresent = false;
        if(website != null && !website.isBlank()) {
            links = String.format("[Link](%s)", website);
            linkPresent = true;
        }

        if(licenseLink != null && !licenseLink.isBlank()) {
            if(linkPresent) {
                links = String.format("%s, [License](%s)", links, licenseLink);
            } else {
                links = String.format("[License](%s)", licenseLink);
            }
        }

        return (links.isEmpty()) ? result : String.format("%s (%s)", result, links);
    }

}
