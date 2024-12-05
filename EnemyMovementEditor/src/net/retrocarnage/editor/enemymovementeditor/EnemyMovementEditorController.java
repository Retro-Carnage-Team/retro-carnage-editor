package net.retrocarnage.editor.enemymovementeditor;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
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
    static final String PROPERTY_RECORDING = "recording";

    private static final Logger logger = Logger.getLogger(EnemyMovementEditorController.class.getName());
    
    private final PropertyChangeSupport propertyChangeSupport;    
    private final LookupListener enemyLookupListener;
    private final Lookup.Result<Enemy> enemyLookupResult;
    private final LookupListener selectionControllerLookupListener;
    private final Lookup.Result<SelectionController> selectionControllerLookupResult;
    private final PropertyChangeListener pointSelectedChangeListener;
    private final VetoableChangeListener selectionChangeListener;

    private boolean recording = false;
    private Enemy enemy;
    private EnemyMovement selectedMovement;
    private List<EnemyMovement> movements;
    private EnemyMovementTableModel tableModel;
    private SelectionController selectionController;
    

    EnemyMovementEditorController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        movements = Collections.emptyList();        
        
        selectionControllerLookupListener = (final LookupEvent le) -> handleSelectionControllerLookupResultChanged();
        selectionControllerLookupResult = GamePlayEditorProxyFactory
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookupResult(SelectionController.class);
        selectionControllerLookupResult.addLookupListener(selectionControllerLookupListener);
        
        enemyLookupListener = (final LookupEvent le) -> handleEnemyLookupResultChanged();
        enemyLookupResult = GamePlayEditorProxyFactory
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookupResult(Enemy.class);
        enemyLookupResult.addLookupListener(enemyLookupListener);
        
        pointSelectedChangeListener = pce -> {
            if(SelectionController.PROPERTY_POINT_SELECTED.equals(pce.getPropertyName()))
                handlePointSelected((Point) pce.getNewValue());
        };
        selectionChangeListener = (PropertyChangeEvent pce) -> {
            if(recording && (pce.getNewValue() != pce.getOldValue()))
                throw new PropertyVetoException( "Recording of enemy movements is in progress", pce);
        };
    }

    void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    void close() {
        enemyLookupResult.removeLookupListener(enemyLookupListener);
    }

    List<EnemyMovement> getMovements() {
        return movements;
    }

    EnemyMovement getSelectedMovement() {
        return selectedMovement;
    }

    /**
     * @return whether or not the component is currently recording movements.
     */
    public boolean isRecording() {
        return recording;
    }
    
    /**
     * Starts recording movements
     */
    public void startRecording() {
        if (null != enemy && !recording) {
            recording = true;
            propertyChangeSupport.firePropertyChange(PROPERTY_RECORDING, false, true);
        }
    }
    
    public boolean isEnabled() {
        return null != enemy;
    }
    
    /**
     * Stops recording movements
     */
    public void stopRecording() {
        if (recording) {
            recording = false;
            propertyChangeSupport.firePropertyChange(PROPERTY_RECORDING, true, false);
        }
    }

    /**
     * Deletes the selected movement - if possible
     */
    void deleteMovements() {
        if (!movements.isEmpty()) {            
            movements.clear();            
            if (null != tableModel) {
                tableModel.fireTableDataChanged();
            }
            selectionController.selectionModified();
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

    private void handleEnemyLookupResultChanged() {
        final boolean oldEnabled = null != enemy;
        final List<EnemyMovement> oldMovements = movements;

        enemy = null;
        movements = Collections.emptyList();

        final Collection<? extends Enemy> items = enemyLookupResult.allInstances();
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

    private void handleSelectionControllerLookupResultChanged() {
        stopRecording();
        if(null != selectionController) {
            selectionController.removePropertyChangeListener(pointSelectedChangeListener);
            selectionController.removeVetoableChangeListener(selectionChangeListener);
        }
        selectionControllerLookupResult.allInstances().stream().findAny().ifPresent(t -> {
           selectionController = t; 
           selectionController.addPropertyChangeListener(pointSelectedChangeListener);
           selectionController.addVetoableChangeListener(selectionChangeListener);
        });        
    }

    private void handlePointSelected(final Point nextPosition) {
        if (null != enemy && recording) {                        
            final EnemyMovement combinedMovement = new EnemyMovement(); // 0, 0
            for(EnemyMovement m: movements) {
                combinedMovement.add(m);
            }            
            
            final Point enemyPosition = enemy.getPosition().getCenter();
            enemyPosition.translate(combinedMovement.getDistanceX(), combinedMovement.getDistanceY());
            
            final EnemyMovement newMovement = new EnemyMovement();
            newMovement.setDistanceX(nextPosition.x - enemyPosition.x);
            newMovement.setDistanceY(nextPosition.y - enemyPosition.y);
            movements.add(newMovement);
            selectionController.selectionModified();
            
            if (null != tableModel) {
               tableModel.fireTableDataChanged();
            }
                
            propertyChangeSupport.firePropertyChange(PROPERTY_MOVEMENTS, null, movements);
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
