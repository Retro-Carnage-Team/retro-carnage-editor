package net.retrocarnage.editor.missionmanager.editor;

import javax.swing.table.AbstractTableModel;

/**
 * A Controller for the EditorTopComponent.
 *
 * @author Thomas Werner
 */
class EditorController {

    private final EditorViewModel viewModel;
    private MissionTableModel missionTableModel;

    private EditorController() {
        viewModel = new EditorViewModel();
    }

    EditorViewModel getViewModel() {
        return viewModel;
    }

    void addMission() {
        throw new UnsupportedOperationException("Oh oh!");
    }

    void deleteMission() {
        throw new UnsupportedOperationException("Oh oh!");
    }

    void saveChanges() {
        throw new UnsupportedOperationException("Oh oh!");
    }

    void discardChanges() {
        throw new UnsupportedOperationException("Oh oh!");
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
