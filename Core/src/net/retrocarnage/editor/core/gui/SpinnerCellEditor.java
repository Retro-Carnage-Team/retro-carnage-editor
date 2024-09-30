package net.retrocarnage.editor.core.gui;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * A simple TableCellEditor that uses a JSpinner to edit values.
 * 
 * @author Thomas Werner
 */
public final class SpinnerCellEditor extends DefaultCellEditor {

    private final JSpinner spinner;
    
    public SpinnerCellEditor() {
        super(new JTextField());        
        spinner = new JSpinner();    
        setClickCountToStart(1);
    }    

    @Override
    public Component getTableCellEditorComponent(
            final JTable table, final Object value, final boolean isSelected, final int row, final int column
    ) {
        try {
            spinner.setValue(value);
        } catch(IllegalArgumentException iae) {
            spinner.setValue(0);
        }
        return spinner;
    }    

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
