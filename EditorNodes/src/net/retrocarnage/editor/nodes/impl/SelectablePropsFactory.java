package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Position;
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

    public static final String PROPERTY_POSITION_X = "X";
    public static final String PROPERTY_POSITION_Y = "Y";
    public static final String PROPERTY_POSITION_WIDTH = "Width";
    public static final String PROPERTY_POSITION_HEIGHT = "Height";
    private static final String NAME_POSITION = "Position";

    private SelectablePropsFactory() {
        // Intentionally empty.
    }

    public static Sheet.Set buildFullSheet(final Selectable selectable, final boolean readonly) {
        final Sheet.Set positionSet = Sheet.createPropertiesSet();
        positionSet.setDisplayName(NAME_POSITION);
        positionSet.setName(NAME_POSITION);

        final Property<Integer> posXProp = SelectablePropsFactory.buildXProperty(selectable, readonly);
        posXProp.setName(PROPERTY_POSITION_X);
        positionSet.put(posXProp);

        final Property<Integer> posYProp = SelectablePropsFactory.buildYProperty(selectable, readonly);
        posYProp.setName(PROPERTY_POSITION_Y);
        positionSet.put(posYProp);

        final Property<Integer> posWidthProp = SelectablePropsFactory.buildWidthProperty(selectable, readonly);
        posWidthProp.setName(PROPERTY_POSITION_WIDTH);
        positionSet.put(posWidthProp);

        final Property<Integer> posHeightProp = SelectablePropsFactory.buildHeightProperty(selectable, readonly);
        posHeightProp.setName(PROPERTY_POSITION_HEIGHT);
        positionSet.put(posHeightProp);

        return positionSet;
    }

    public static Sheet.Set buildPositionSheet(final Selectable selectable, final boolean readonly) {
        final Sheet.Set positionSet = Sheet.createPropertiesSet();
        positionSet.setDisplayName(NAME_POSITION);
        positionSet.setName(NAME_POSITION);

        final Property<Integer> posXProp = SelectablePropsFactory.buildXProperty(selectable, readonly);
        posXProp.setName(PROPERTY_POSITION_X);
        positionSet.put(posXProp);

        final Property<Integer> posYProp = SelectablePropsFactory.buildYProperty(selectable, readonly);
        posYProp.setName(PROPERTY_POSITION_Y);
        positionSet.put(posYProp);

        return positionSet;
    }

    private static Property<Integer> buildXProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getX();
            }

            @Override
            public void setValue(final Integer newValue) {
                if (!readonly) {
                    final Position old = selectable.getPosition();
                    selectable.setPosition(new Position(newValue, old.getY(), old.getWidth(), old.getHeight()));
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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

    private static Property<Integer> buildYProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getY();
            }

            @Override
            public void setValue(final Integer newValue) {
                if (!readonly) {
                    final Position old = selectable.getPosition();
                    selectable.setPosition(new Position(old.getX(), newValue, old.getWidth(), old.getHeight()));
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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

    private static Property<Integer> buildWidthProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getWidth();
            }

            @Override
            public void setValue(final Integer newValue) {
                if (!readonly) {
                    final Position old = selectable.getPosition();
                    selectable.setPosition(new Position(old.getX(), old.getY(), newValue, old.getHeight()));
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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

    private static Property<Integer> buildHeightProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().getHeight();
            }

            @Override
            public void setValue(final Integer newValue) {
                if (!readonly) {                    
                    selectable.setPosition(
                        new Position(
                            selectable.getPosition().getX(), 
                            selectable.getPosition().getY(), 
                            selectable.getPosition().getWidth(), 
                            newValue
                        )
                    );
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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
