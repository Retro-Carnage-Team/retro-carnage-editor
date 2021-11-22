package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Selectable;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;

/**
 * A factory that builds properties for Rectangles.
 *
 * @author Thomas Werner
 */
public class SelectablePropsFactory {

    public static Property buildXProperty(final Selectable selectable, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return selectable.getPosition().x;
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().x = t;
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
                return selectable.getPosition().y;
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().y = t;
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
                return selectable.getPosition().width;
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().width = t;
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
                return selectable.getPosition().height;
            }

            @Override
            public void setValue(final Integer t) {
                if (!readonly) {
                    selectable.getPosition().height = t;
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
