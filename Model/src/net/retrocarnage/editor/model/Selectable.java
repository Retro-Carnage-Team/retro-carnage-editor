package net.retrocarnage.editor.model;

import java.awt.Rectangle;

/**
 * Something that can be selected.
 *
 * This might be e.g. a VisualAsset or an Enemy.
 *
 * @author Thomas Werner
 */
public interface Selectable {

    boolean isMovable();

    boolean isResizable();

    public Rectangle getPosition();

    void setPosition(final Rectangle position);

}
