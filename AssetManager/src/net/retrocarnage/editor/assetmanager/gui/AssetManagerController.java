package net.retrocarnage.editor.assetmanager.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.model.Asset;
import net.retrocarnage.editor.assetmanager.model.AttributionData;
import net.retrocarnage.editor.assetmanager.model.Music;
import net.retrocarnage.editor.assetmanager.model.Sprite;

/**
 * Controller for the AssetManagerTopComponent.
 *
 * @author Thomas Werner
 */
class AssetManagerController {

    public static final String PROPERTY_SELECTED_ASSET = "selectedAsset";
    private static final Logger logger = Logger.getLogger(AssetManagerTopComponent.class.getName());

    private final AssetService assetService = AssetService.getDefault();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private List<Asset<?>> assets = getAssetList();
    private Asset<?> selectedAsset;
    private File newAssetResource;
    private AssetTableModel assetTableModel;

    private List<Asset<?>> getAssetList() {
        return new ArrayList<>(assetService.findAssets(null));
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    void newMusicAsset(final File assetResourceFile) {
        newAssetResource = assetResourceFile;

        final Music newMusic = new Music();
        newMusic.setAttributionData(new AttributionData());
        replaceAsset(newMusic);
    }

    void newSpriteAsset(final File assetResourceFile) {
        newAssetResource = assetResourceFile;

        final Sprite newSprite = new Sprite();
        newSprite.setAttributionData(new AttributionData());
        replaceAsset(newSprite);
    }

    boolean saveChanges() {
        if (null == selectedAsset.getId()) {
            try (final InputStream in = new FileInputStream(newAssetResource)) {
                selectedAsset.setId(UUID.randomUUID().toString());
                if (selectedAsset instanceof Music) {
                    assetService.addMusic((Music) selectedAsset, in);
                } else {
                    assetService.addSprite((Sprite) selectedAsset, in);
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Failed to add asset", ex);
                return false;
            }
        } else {
            if (selectedAsset instanceof Music) {
                assetService.updateMusicInfo((Music) selectedAsset);
            } else {
                assetService.updateSpriteInfo((Sprite) selectedAsset);
            }
        }
        assets = getAssetList();
        assetTableModel.fireTableDataChanged();
        return true;
    }

    /**
     * @return the Asset that the user selected
     */
    Asset<?> getSelectedAsset() {
        return selectedAsset;
    }

    /**
     * Builds a TableModel for the table of Assets.
     *
     * @return the TableModel
     */
    AbstractTableModel getTableModel() {
        if (null == assetTableModel) {
            assetTableModel = new AssetTableModel();
        }
        return assetTableModel;
    }

    /**
     * Builds a ListSelectionListener for the table of Assets. Used to update the asset property accordingly.
     *
     * @return the ListSelectionListener
     */
    ListSelectionListener getTableSelectionListener(final JTable table) {
        return (ListSelectionEvent lse) -> {
            if (!lse.getValueIsAdjusting()) {
                replaceAsset(assets.get(table.getSelectedRow()));
            }
        };
    }

    /**
     * Replaces the currently selected asset.
     *
     * @param newValue the new asset.
     */
    private void replaceAsset(final Asset<?> newValue) {
        Asset<?> oldValue = null;
        if (null != selectedAsset) {
            oldValue = selectedAsset.deepCopy();
        }

        selectedAsset = newValue;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_ASSET, oldValue, selectedAsset);
    }

    /**
     * The TableModel used by the asset table.
     */
    private class AssetTableModel extends AbstractTableModel {

        final String[] columnNames = {"ID", "Name", "Type", "Tags"};

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            return assets.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            final Asset<?> asset = assets.get(row);
            switch (column) {
                case 0:
                    return asset.getId();
                case 1:
                    return asset.getName();
                case 2:
                    return (asset instanceof Music) ? "Music" : "Sprite";
                case 3:
                    final List<String> tags = new ArrayList<>(asset.getTags());
                    Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
                    return String.join(", ", tags);
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

}
