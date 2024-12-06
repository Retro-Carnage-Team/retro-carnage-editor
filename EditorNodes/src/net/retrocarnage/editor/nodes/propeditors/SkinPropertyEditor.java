package net.retrocarnage.editor.nodes.propeditors;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import net.retrocarnage.editor.core.IconUtil;
import net.retrocarnage.editor.model.EnemySkin;
import net.retrocarnage.editor.nodes.icons.IconProvider;
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
    public void attachEnv(final PropertyEnv propertyEnv) {
        propertyEnv.registerInplaceEditorFactory(this);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        if (null == ed) {
            ed = new SkinInplaceEditor();
        }
        return ed;
    }

    /**
     * InplaceEditor that displays a (non editable) JComboBox.
     */
    private static class SkinInplaceEditor implements InplaceEditor {

        private PropertyEditor editor = null;
        private PropertyModel model = null;
        private ActionListener actionListener = null;
        private final JComboBox<String> comboBox;

        private SkinInplaceEditor() {
            final String[] skins = Arrays.asList(EnemySkin.values())
                    .stream()
                    .map(es -> es.getName())
                    .collect(Collectors.toList())
                    .toArray(String[]::new);
            comboBox = new JComboBox<>(skins);
            comboBox.setRenderer(new SkinPropertyRenderer());
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
            final String skin = (String) editor.getValue();
            if (null != skin) {
                this.comboBox.setSelectedItem(skin);
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
     * Renderer component for the InplaceEditor that displays a preview of the skins.
     */
    private static class SkinPropertyRenderer extends JLabel implements ListCellRenderer<String> {

        private final java.util.Map<String, ImageIcon> skinToIcons;

        public SkinPropertyRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);

            skinToIcons = new HashMap<>();
            for (EnemySkin es : EnemySkin.values()) {
                
                final Image iconImage = IconUtil.loadIcon(IconProvider.getIcon(es.getName() + ".png"));
                skinToIcons.put(es.getName(), new ImageIcon(iconImage));
            }
        }
        
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, final String value, final int index, 
                final boolean isSelected, final boolean cellHasFocus
        ) {
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            if (null == value) {
                setIcon(null);
            } else {
                final EnemySkin es = EnemySkin.findByName(value);
                if (null != es) {
                    setIcon(skinToIcons.get(es.getName()));
                }
            }

            return this;
        }

    }

}
