package net.retrocarnage.editor.missionmanager.editor;

import java.util.UUID;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.CLOSED_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.missionmanager.MissionService;
import net.retrocarnage.editor.model.Mission;

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
        viewModel.setSelectedMission(new Mission());
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
        final Mission changedMission = viewModel.getSelectedMission();
        if (null == changedMission.getId()) {
            changedMission.setId(UUID.randomUUID().toString());
            service.addMission(viewModel.getSelectedMission());
        } else {
            final Mission original = service.getMission(changedMission.getId());
            original.applyPartialChangesOfMetaData(changedMission);
        }
        viewModel.setChangesSaved();
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
     * The TableModel used by the mission table.
     */
    private class MissionTableModel extends AbstractTableModel {

        final String[] columnNames = {"ID", "Name"};

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
                    return mission.getId();
                case 1:
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
