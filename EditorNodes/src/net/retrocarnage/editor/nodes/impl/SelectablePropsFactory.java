package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Selectable;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;

/**
 * A factory that builds properties for Selectables.
 *
 * @author Thomas Werner
 */
public class SelectablePropsFactory {

    public static Property buildXProperty(final Selectable selectable, final boolean readonly) {
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

    public static Property buildYProperty(final Selectable selectable, final boolean readonly) {
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

    public static Property buildWidthProperty(final Selectable selectable, final boolean readonly) {
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

    public static Property buildHeightProperty(final Selectable selectable, final boolean readonly) {
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
