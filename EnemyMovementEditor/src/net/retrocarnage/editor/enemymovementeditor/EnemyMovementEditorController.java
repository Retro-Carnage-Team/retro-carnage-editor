package net.retrocarnage.editor.enemymovementeditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemyMovement;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * GUI controller class for EnemyMovementEditor.
 *
 * @author Thomas Werner
 */
public class EnemyMovementEditorController {

    static final String PROPERTY_ENABLED = "enabled";
    static final String PROPERTY_MOVEMENTS = "movements";
    static final String PROPERTY_SELECTION = "selection";

    private static final Logger logger = Logger.getLogger(EnemyMovementEditorController.class.getName());
    
    private final PropertyChangeSupport propertyChangeSupport;
    private final LookupListener lookupListener;
    private final Lookup.Result<Enemy> lookupResult;

    private Enemy enemy;
    private EnemyMovement selectedMovement;
    private List<EnemyMovement> movements;
    private EnemyMovementTableModel tableModel;

    EnemyMovementEditorController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        movements = Collections.emptyList();

        lookupListener = (final LookupEvent le) -> handleLookupResultChanged();
        lookupResult = GamePlayEditorProxy.getDefault().getLookup().lookupResult(Enemy.class);
        lookupResult.addLookupListener(lookupListener);
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    void close() {
        lookupResult.removeLookupListener(lookupListener);
    }

    List<EnemyMovement> getMovements() {
        return movements;
    }

    EnemyMovement getSelectedMovement() {
        return selectedMovement;
    }

    /**
     * Adds a new section at the end of the current list of sections
     */
    void addMovement() {
        if (null != enemy) {
            final EnemyMovement newMovement = new EnemyMovement();
            newMovement.setDistanceX(50);
            newMovement.setDistanceY(50);
            movements.add(newMovement);

            if (null != tableModel) {
                tableModel.fireTableDataChanged();
            }
            propertyChangeSupport.firePropertyChange(PROPERTY_MOVEMENTS, null, movements);
        }
    }

    /**
     * Deletes the selected movement - if possible
     */
    void deleteMovement() {
        if (!movements.isEmpty() && null != selectedMovement) {
            final int idx = movements.indexOf(selectedMovement);
            if (-1 != idx) {
                movements.remove(idx);
                selectedMovement = null;
            }

            if (null != tableModel) {
                tableModel.fireTableDataChanged();
            }
            propertyChangeSupport.firePropertyChange(PROPERTY_MOVEMENTS, null, movements);
        }
    }

    AbstractTableModel getTableModel() {
        if (null == tableModel) {
            tableModel = new EnemyMovementTableModel();
        }
        return tableModel;
    }

    /**
     * Builds a ListSelectionListener for the sections table.
     *
     * @return the ListSelectionListener
     */
    ListSelectionListener getTableSelectionListener(final JTable table) {
        return (ListSelectionEvent lse) -> {
            final int selectedRow = table.getSelectedRow();
            if (!lse.getValueIsAdjusting()) {
                final EnemyMovement oldValue = selectedMovement;
                selectedMovement = (selectedRow > -1) && (selectedRow < movements.size())
                        ? movements.get(selectedRow)
                        : null;
                propertyChangeSupport.firePropertyChange(PROPERTY_SELECTION, oldValue, selectedMovement);
            }
        };
    }

    private void handleLookupResultChanged() {
        final boolean oldEnabled = null != enemy;
        final List<EnemyMovement> oldMovements = movements;

        enemy = null;
        movements = Collections.emptyList();

        final Collection<? extends Enemy> items = lookupResult.allInstances();
        if (!items.isEmpty()) {
            enemy = items.iterator().next();
            movements = enemy.getMovements();
        }

        propertyChangeSupport.firePropertyChange(PROPERTY_ENABLED, oldEnabled, null != enemy);
        propertyChangeSupport.firePropertyChange(PROPERTY_MOVEMENTS, oldMovements, movements);

        if (null != tableModel) {
            tableModel.fireTableDataChanged();
        }
    }

    /**
     * The TableModel for the GUI.
     */
    private class EnemyMovementTableModel extends AbstractTableModel {

        private final String[] columnNames = {"X", "Y"};

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            return null == movements ? 0 : movements.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            if (null == movements || movements.size() <= row) {
                return null;
            }

            final EnemyMovement movement = movements.get(row);
            switch (column) {
                case 0:
                    return movement.getDistanceX();
                case 1:
                    return movement.getDistanceY();
                default:
                    return "";
            }
        }

        @Override
        public void setValueAt(final Object value, final int row, final int column) {
            try {
                final EnemyMovement movement = movements.get(row);
                switch (column) {
                    case 0:
                        movement.setDistanceX((int) value);
                        fireTableCellUpdated(row, 0);
                        break;
                    case 1:
                        movement.setDistanceY((int) value);
                        fireTableCellUpdated(row, 1);
                        break;
                    default:
                }
            } catch (IndexOutOfBoundsException ioobe) {
                logger.info("Caught IndexOutOfBoundsException");
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return true;
        }

    }

}
