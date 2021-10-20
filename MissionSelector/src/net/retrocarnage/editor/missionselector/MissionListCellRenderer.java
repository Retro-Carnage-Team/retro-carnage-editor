package net.retrocarnage.editor.missionselector;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import net.retrocarnage.editor.model.Mission;

/**
 * A ListCellRenderer that gets used to show the Missions in the selector window.
 *
 * @author Thomas Werner
 */
class MissionListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            final JList<?> list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(((Mission) value).getName());
        return this;
    }

}
