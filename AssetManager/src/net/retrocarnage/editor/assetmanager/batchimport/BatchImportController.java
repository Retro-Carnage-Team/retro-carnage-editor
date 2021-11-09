package net.retrocarnage.editor.assetmanager.batchimport;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.model.AttributionData;
import net.retrocarnage.editor.model.Sprite;
import org.netbeans.api.progress.*;
import org.openide.WizardDescriptor;

/**
 * A controller for the BatchImportWizard.
 *
 * @author Thomas Werner
 */
public class BatchImportController {

    private static final Logger logger = Logger.getLogger(BatchImportController.class.getName());

    private final BatchImportWizardPanel1 panel;

    BatchImportController() {
        panel = new BatchImportWizardPanel1();
    }

    List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        return Collections.singletonList(panel);
    }

    void runImport() {
        final ProgressHandle handle = ProgressHandleFactory.createHandle("Sprite import");
        handle.start();

        final ImportWorker worker = new ImportWorker(
                panel.getComponent().getImportFolder(),
                panel.getComponent().isRecursive(),
                panel.getComponent().isTile(),
                panel.getComponent().getTags(),
                panel.getComponent().getAuthor(),
                panel.getComponent().getWebsite(),
                panel.getComponent().getLicenseLink(),
                panel.getComponent().getLicenseText()
        );
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            private boolean infinite = true;

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                    handle.finish();
                } else if ("progress".equals(evt.getPropertyName())) {
                    if (infinite) {
                        infinite = false;
                        handle.switchToDeterminate(100);
                    }
                    final int progress = (Integer) evt.getNewValue();
                    handle.progress(progress);
                }
            }
        });
        worker.execute();
    }

    /**
     * SwingWorker that performs the import of Sprites asynchronously.
     */
    private class ImportWorker extends SwingWorker<Void, Integer> {

        private final File folder;
        private final boolean recursive;
        private List<Path> filesToImport;
        private final List<String> tags;
        private final boolean tile;
        private final String author;
        private final String website;
        private final String licenseLink;
        private final String licenseText;

        public ImportWorker(
                final File folder,
                final boolean recursive,
                final boolean tile,
                final String tags,
                final String author,
                final String website,
                final String licenseLink,
                final String licenseText) {
            this.folder = folder;
            this.recursive = recursive;
            this.tile = tile;
            this.tags = Arrays.asList(panel.getComponent().getTags().split("\\s+"))
                    .stream()
                    .map(str -> str.trim())
                    .distinct()
                    .collect(Collectors.toList());
            this.author = author;
            this.website = website;
            this.licenseLink = licenseLink;
            this.licenseText = licenseText;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                filesToImport = findFilesToImport();
                final float total = filesToImport.size();
                float idx = 0;
                for (final Path fileToImport : filesToImport) {
                    importSprite(fileToImport);
                    idx += 1;
                    setProgress((int) (100 * (idx / total)));
                }

            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Failed to access files to import", ex);
            }
            return null;
        }

        private void importSprite(final Path spritePath) throws IOException {
            final BufferedImage tempImage = ImageIO.read(spritePath.toFile());

            final AttributionData attribution = new AttributionData();
            attribution.setAuthor(author);
            attribution.setWebsite(website);
            attribution.setLicenseLink(licenseLink);
            attribution.setLicenseText(licenseText);

            final Sprite newSprite = new Sprite();
            newSprite.setAttributionData(attribution);
            newSprite.setWidth(tempImage.getWidth());
            newSprite.setHeight(tempImage.getHeight());
            newSprite.setTile(tile);
            newSprite.setTags(tags);

            logger.log(Level.FINE, "Importing sprite file {0}", spritePath.toString());
            try (final InputStream in = new FileInputStream(spritePath.toFile())) {
                newSprite.setId(UUID.randomUUID().toString());
                AssetService.getDefault().addSprite(newSprite, in);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Failed to batch import asset", ex);
            }
        }

        /**
         * We need to know how many files we have to import to show a useful progress indicator.
         *
         * @return the files to import
         * @throws IOException if the specified import location cannot be accessed
         */
        private List<Path> findFilesToImport() throws IOException {
            final Stream<Path> pathStream = recursive
                    ? Files.walk(Paths.get(folder.toURI()))
                    : Files.list(Paths.get(folder.toURI()));
            return pathStream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }

    }

}
