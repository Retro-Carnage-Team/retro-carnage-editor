package net.retrocarnage.editor.sectioneditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.retrocarnage.editor.model.gameplay.GamePlay;
import net.retrocarnage.editor.model.gameplay.Section;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * GUI Controller class for the SectionEditor.
 *
 * @author Thomas Werner
 */
public class SectionEditorController {

    static final String PROPERTY_SECTIONS = "sections";
    static final String PROPERTY_SELECTION = "selection";

    private final PropertyChangeSupport propertyChangeSupport;
    private final LookupListener lookupListener;
    private final Lookup.Result<GamePlay> lookupResult;

    private Section selectedSection;
    private List<Section> sections;
    private SectionTableModel tableModel;

    SectionEditorController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        sections = Collections.emptyList();

        lookupListener = (final LookupEvent le) -> handleLookupResultChanged();
        lookupResult = Utilities.actionsGlobalContext().lookupResult(GamePlay.class);
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

    List<Section> getSections() {
        return sections;
    }

    Section getSelectedSection() {
        return selectedSection;
    }

    AbstractTableModel getTableModel() {
        if (null == tableModel) {
            tableModel = new SectionTableModel();
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
                final Section oldValue = selectedSection;
                selectedSection = (selectedRow > -1) && (selectedRow < sections.size())
                        ? sections.get(selectedRow)
                        : null;
                propertyChangeSupport.firePropertyChange(PROPERTY_SELECTION, oldValue, selectedSection);
            }
        };
    }

    void increaseLengthOfSection(Section section) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void decreaseLengthOfSection(Section section) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Adds a new section at the end of the current list of sections
     */
    void addSection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Deletes the selected section - if possible
     */
    void deleteSection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void handleLookupResultChanged() {
        final Collection<? extends GamePlay> items = lookupResult.allInstances();
        final List<Section> oldValue = sections;
        sections = items.isEmpty()
                ? Collections.emptyList()
                : items.iterator().next().getSections();
        propertyChangeSupport.firePropertyChange(PROPERTY_SECTIONS, oldValue, sections);

        if (null != tableModel) {
            tableModel.fireTableDataChanged();
        }
    }

    /**
     * The TableModel used by the section table.
     */
    private class SectionTableModel extends AbstractTableModel {

        final String[] columnNames = {"Direction", "Length", "+", "-"};

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            return null == sections
                    ? 0
                    : sections.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            if (null == sections || sections.size() <= row) {
                return null;
            }

            final Section section = sections.get(row);
            switch (column) {
                case 0:
                    return section.getDirection().getExportName();
                case 1:
                    return section.getNumberOfScreens();
                case 2:
                    return "+";
                case 3:
                    return "-";
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
