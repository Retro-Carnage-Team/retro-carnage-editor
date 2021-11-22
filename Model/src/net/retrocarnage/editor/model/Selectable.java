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

    Rectangle getPosition();

    void setPosition(final Rectangle position);

    /**
     * Returns the position - scaled by a given factor.
     *
     * @param factor scaling factor to be applied
     * @return the scaled position
     */
    default Rectangle getScaledPosition(float factor) {
        final Rectangle position = getPosition();
        return new Rectangle(
                (int) (position.x * factor),
                (int) (position.y * factor),
                (int) (position.width * factor),
                (int) (position.height * factor)
        );
    }

}
