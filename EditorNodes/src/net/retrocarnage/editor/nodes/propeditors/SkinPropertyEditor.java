package net.retrocarnage.editor.nodes.propeditors;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import net.retrocarnage.editor.model.EnemySkin;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 * A property editor for Enemy.Skin.
 *
 * Shows a JComboBox that the user can use to select the enemy skin.
 *
 * @author Thomas Werner
 */
public class SkinPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    private InplaceEditor ed = null;

    @Override
    public void attachEnv(PropertyEnv propertyEnv) {
        propertyEnv.registerInplaceEditorFactory(this);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        if (null == ed) {
            ed = new SkinInplaceEditor();
        }
        return ed;
    }

    private static class SkinInplaceEditor implements InplaceEditor {

        private PropertyEditor editor = null;
        private PropertyModel model = null;
        private final JComboBox<String> comboBox;

        private SkinInplaceEditor() {
            final String[] skins = Arrays.asList(EnemySkin.values())
                    .stream()
                    .map(es -> es.getName())
                    .collect(Collectors.toList())
                    .toArray(String[]::new);
            comboBox = new JComboBox<>(skins);
            // TODO: ListCellRenderer einbinden, um lesbare Version des Skins anzuzeigen
        }

        @Override
        public void connect(PropertyEditor pe, PropertyEnv pe1) {
            this.editor = pe;
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
        public void setValue(Object o) {
            comboBox.setSelectedItem(o);
        }

        @Override
        public boolean supportsTextEntry() {
            return false;
        }

        @Override
        public void reset() {
            final String skin = (String) editor.getValue();
            if (null != skin) {
                this.comboBox.setSelectedItem(skin);
            }
        }

        @Override
        public void addActionListener(ActionListener al) {
            comboBox.addActionListener(al);
        }

        @Override
        public void removeActionListener(ActionListener al) {
            comboBox.removeActionListener(al);
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
        public void setPropertyModel(PropertyModel pm) {
            model = pm;
        }

        @Override
        public boolean isKnownComponent(Component cmpnt) {
            return cmpnt == comboBox;
        }

    }

}
