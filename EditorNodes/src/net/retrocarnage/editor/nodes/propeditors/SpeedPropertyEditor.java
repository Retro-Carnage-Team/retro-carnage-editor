package net.retrocarnage.editor.nodes.propeditors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import net.retrocarnage.editor.model.EnemySpeed;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 * A property editor for Enemy.Speed.
 *
 * Shows a JComboBox that the user can use to select the enemy speed.
 *
 * @author Thomas Werner
 */
public class SpeedPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    private InplaceEditor ed = null;

    @Override
    public void attachEnv(final PropertyEnv propertyEnv) {
        propertyEnv.registerInplaceEditorFactory(this);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        if (null == ed) {
            ed = new SpeedInplaceEditor();
        }
        return ed;
    }

    /**
     * InplaceEditor that displays a (non editable) JComboBox.
     */
    private static class SpeedInplaceEditor implements InplaceEditor {

        private PropertyEditor editor = null;
        private PropertyModel model = null;
        private ActionListener actionListener = null;
        private final JComboBox<Integer> comboBox;

        private SpeedInplaceEditor() {
            final Integer[] values = Arrays.asList(EnemySpeed.values())
                    .stream()
                    .map(es -> es.getSpeed())
                    .collect(Collectors.toList())
                    .toArray(Integer[]::new);
            comboBox = new JComboBox<>(values);
            comboBox.setRenderer(new SpeedPropertyRenderer());
            comboBox.addItemListener((ItemEvent ie) -> {
                if ((ItemEvent.SELECTED == ie.getStateChange()) && (null != actionListener)) {
                    actionListener.actionPerformed(new ActionEvent(
                            this,
                            ActionEvent.ACTION_PERFORMED,
                            InplaceEditor.COMMAND_SUCCESS
                    ));
                }
            });
        }

        @Override
        public void connect(final PropertyEditor propertyEditor, final PropertyEnv propertyEnvironment) {
            editor = propertyEditor;
        }

        @Override
        public JComponent getComponent() {
            return comboBox;
        }

        @Override
        public void clear() {
            comboBox.setSelectedIndex(-1);
        }

        @Override
        public Object getValue() {
            return comboBox.getSelectedItem();
        }

        @Override
        public void setValue(final Object o) {
            comboBox.setSelectedItem(o);
        }

        @Override
        public boolean supportsTextEntry() {
            return false;
        }

        @Override
        public void reset() {
            final Integer speed = (Integer) editor.getValue();
            if (null != speed) {
                this.comboBox.setSelectedItem(speed);
            }
        }

        @Override
        public void addActionListener(final ActionListener al) {
            actionListener = al;
        }

        @Override
        public void removeActionListener(final ActionListener al) {
            actionListener = null;
        }

        @Override
        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[]{};
        }

        @Override
        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        @Override
        public PropertyModel getPropertyModel() {
            return model;
        }

        @Override
        public void setPropertyModel(final PropertyModel pm) {
            model = pm;
        }

        @Override
        public boolean isKnownComponent(final Component cmpnt) {
            return cmpnt == comboBox;
        }

    }

    /**
     * Renderer component for the InplaceEditor that displays a human readable name for the predefined speed values.
     */
    private static class SpeedPropertyRenderer extends JLabel implements ListCellRenderer {

        public SpeedPropertyRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                final JList list, final Object value, final int index, final boolean isSelected,
                final boolean cellHasFocus
        ) {
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            if (null == value) {
                setText("");
            } else {
                final EnemySpeed es = EnemySpeed.findBySpeed((Integer) value);
                if (null != es) {
                    setText(es.getName());
                }
            }

            return this;
        }

    }

}
