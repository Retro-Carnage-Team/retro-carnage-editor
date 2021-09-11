package net.retrocarnage.editor.assetmanager.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private final AssetService assetService = AssetService.getDefault();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private List<Asset<?>> assets = getAssetList();
    private Asset<?> selectedAsset;
    private File newAssetResource;

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
        // TODO: Check that the currently selected asset has no unsaved changes
    }

    void newSpriteAsset(final File assetResourceFile) {
        newAssetResource = assetResourceFile;

        final Sprite newSprite = new Sprite();
        newSprite.setAttributionData(new AttributionData());
        replaceAsset(newSprite);
    }

    boolean isAssetModified() {
        // TODO: Check that the currently selected asset has no unsaved changes
        return false;
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
        final String[] columnNames = {"ID", "Type", "Tags"};
        return new AbstractTableModel() {
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
                        return (asset instanceof Music) ? "Music" : "Sprite";
                    case 2:
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
        };
    }

    /**
     * Builds a ListSelectionListener for the table of Assets. Used to update the asset property accordingly.
     *
     * @return the ListSelectionListener
     */
    ListSelectionListener getTableSelectionListener() {
        return (ListSelectionEvent lse) -> {
            if (!lse.getValueIsAdjusting()) {
                replaceAsset(assets.get(lse.getFirstIndex()));
            }
        };
    }

    private void replaceAsset(final Asset<?> newValue) {
        final Asset<? extends Asset<?>> oldValue = null;
        if (null != selectedAsset) {
            selectedAsset = oldValue.deepCopy();
        }

        selectedAsset = newValue;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_ASSET, oldValue, selectedAsset);
    }

}
