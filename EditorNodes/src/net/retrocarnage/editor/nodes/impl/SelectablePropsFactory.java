package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Selectable;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Sheet;

/**
 * A factory that builds properties for Selectables.
 *
 * @author Thomas Werner
 */
public final class SelectablePropsFactory {

    private SelectablePropsFactory() {
        // Intentionally empty.
    }

    public static Sheet.Set buildFullSheet(final Selectable selectable, final boolean readonly) {
        final Sheet.Set positionSet = Sheet.createPropertiesSet();
        positionSet.setDisplayName("Position");
        positionSet.setName("Position");

        final Property posXProp = SelectablePropsFactory.buildXProperty(selectable, readonly);
        posXProp.setName("X");
        positionSet.put(posXProp);

        final Property posYProp = SelectablePropsFactory.buildYProperty(selectable, readonly);
        posYProp.setName("Y");
        positionSet.put(posYProp);

        final Property posWidthProp = SelectablePropsFactory.buildWidthProperty(selectable, readonly);
        posWidthProp.setName("Width");
        positionSet.put(posWidthProp);

        final Property posHeightProp = SelectablePropsFactory.buildHeightProperty(selectable, readonly);
        posHeightProp.setName("Height");
        positionSet.put(posHeightProp);

        return positionSet;
    }

    public static Sheet.Set buildPositionSheet(final Selectable selectable, final boolean readonly) {
        final Sheet.Set positionSet = Sheet.createPropertiesSet();
        positionSet.setDisplayName("Position");
        positionSet.setName("Position");

        final Property posXProp = SelectablePropsFactory.buildXProperty(selectable, readonly);
        posXProp.setName("X");
        positionSet.put(posXProp);

        final Property posYProp = SelectablePropsFactory.buildYProperty(selectable, readonly);
        posYProp.setName("Y");
        positionSet.put(posYProp);

        return positionSet;
    }

    private static Property buildXProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getX();
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().setX(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Property buildYProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getY();
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().setY(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Property buildWidthProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getWidth();
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().setWidth(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Property buildHeightProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getHeight();
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().setHeight(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

}
