package net.retrocarnage.editor.nodes.nodes;

import net.retrocarnage.editor.model.Selectable;

/**
 * Common interface for Nodes that represent something that can be selected in the GamePlayEditor.
 *
 * @author Thomas Werner
 */
public interface SelectableNode {

    public Selectable getSelectable();

}
