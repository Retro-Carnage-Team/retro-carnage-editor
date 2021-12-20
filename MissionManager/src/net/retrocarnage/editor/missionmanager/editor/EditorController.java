package net.retrocarnage.editor.missionmanager.editor;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.CLOSED_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.assetmanager.AssetService;
import static net.retrocarnage.editor.assetmanager.AssetService.TAG_CLIENT;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Mission;
import net.retrocarnage.editor.model.Music;
import net.retrocarnage.editor.model.Sprite;

/**
 * A Controller for the EditorTopComponent.
 *
 * @author Thomas Werner
 */
class EditorController {

    private final EditorViewModel viewModel;
    private final EditorTopComponent view;
    private MissionTableModel missionTableModel;

    EditorController(final EditorTopComponent editorTopComponent) {
        viewModel = new EditorViewModel();
        viewModel.updateMissionsFromService();
        view = editorTopComponent;
    }

    EditorViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Adds a new mission.
     */
    void addMission() {
        selectMission(new SelectableMission());
    }

    void selectMission(final SelectableMission mission) {
        if (viewModel.isModified()) {
            final String message = "There are unsaved changes. Do you want to save them first?";
            final int selectedOption = JOptionPane.showConfirmDialog(view, message, "alert", YES_NO_CANCEL_OPTION);
            switch (selectedOption) {
                case YES_OPTION:
                    saveChanges();
                    break;
                case NO_OPTION:
                    discardChanges();
                    break;
                case CLOSED_OPTION:
                case CANCEL_OPTION:
                default:
                    return;
            }
        }
        if (null == mission.getId()) {
            viewModel.setSelectedMission(new Mission());
        } else {
            viewModel.selectMission(mission);
        }
    }

    /**
     * Deletes the currently selected mission.
     */
    void deleteMission() {
        final String message = "This will delete the selected mission. Do you want to continue?";
        final int selectedOption = JOptionPane.showConfirmDialog(view, message, "alert", YES_NO_CANCEL_OPTION);
        if (selectedOption == YES_OPTION) {
            if (null != viewModel.getSelectedMission().getId()) {
                final MissionService service = MissionService.getDefault();
                service.removeMission(viewModel.getSelectedMission().getId());
            }
            viewModel.setSelectedMission(null);
            viewModel.updateMissionsFromService();
        }
    }

    /**
     * Saves the changes.
     */
    void saveChanges() {
        final MissionService service = MissionService.getDefault();
        Mission changedMission = viewModel.getSelectedMission();
        if (null == changedMission.getId()) {
            if (changedMission instanceof MissionBean) {
                changedMission = ((MissionBean) changedMission).getMission();
            }
            changedMission.setId(UUID.randomUUID().toString());
            service.addMission(changedMission);
        } else {
            service.updateMission(changedMission);
        }
        viewModel.setChangesSaved();
        viewModel.updateMissionsFromService();
    }

    /**
     * Discards any changes that have been made to the model.
     */
    void discardChanges() {
        if (null != viewModel.getSelectedMission()) {
            if (null == viewModel.getSelectedMission().getId()) {
                viewModel.setSelectedMission(null);
            } else {
                viewModel.selectMission(new SelectableMission(viewModel.getSelectedMission()));
            }
        }
        viewModel.setChangesSaved();
    }

    /**
     * Builds a TableModel for the table of Missions.
     *
     * @return the TableModel
     */
    AbstractTableModel getTableModel() {
        if (null == missionTableModel) {
            missionTableModel = new MissionTableModel();
        }
        return missionTableModel;
    }

    /**
     * Builds a ListSelectionListener for the table of Missions.
     *
     * @return the ListSelectionListener
     */
    ListSelectionListener getTableSelectionListener(final JTable table) {
        return (ListSelectionEvent lse) -> {
            final int selectedRow = table.getSelectedRow();
            if ((!lse.getValueIsAdjusting()) && (selectedRow > -1) && (selectedRow < viewModel.getMissions().size())) {
                final SelectableMission newSelection = viewModel.getMissions().get(selectedRow);
                selectMission(newSelection);
            }
        };
    }

    ComboBoxModel<Sprite> getClientSelectionModel() {
        final AssetService assetService = AssetService.getDefault();
        final List<Sprite> clients = new LinkedList<>();
        assetService
                .findAssets(TAG_CLIENT)
                .stream()
                .filter(a -> a instanceof Sprite)
                .forEach(a -> clients.add((Sprite) a));

        final Sprite[] spriteArray = new Sprite[clients.size()];
        for (int m = 0; m < clients.size(); m++) {
            spriteArray[m] = clients.get(m);
        }

        return new DefaultComboBoxModel<>(spriteArray);
    }

    ComboBoxModel<Music> getSongSelectionModel() {
        final AssetService assetService = AssetService.getDefault();
        final List<Music> songs = new LinkedList<>();
        assetService
                .findAssets(null)
                .stream()
                .filter(a -> a instanceof Music)
                .forEach(a -> songs.add((Music) a));

        final Music[] musicArray = new Music[songs.size()];
        for (int m = 0; m < songs.size(); m++) {
            musicArray[m] = songs.get(m);
        }

        return new DefaultComboBoxModel<>(musicArray);
    }

    /**
     * The TableModel used by the mission table.
     */
    private class MissionTableModel extends AbstractTableModel {

        final String[] columnNames = {"Name"};

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            return viewModel.getMissions().size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            final SelectableMission mission = viewModel.getMissions().get(row);
            switch (column) {
                case 0:
                    return mission.getName();
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
