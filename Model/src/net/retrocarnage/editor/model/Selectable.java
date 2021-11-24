package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    Position getPosition();

    void setPosition(final Position position);

    /**
     * Returns the position - scaled by a given factor.
     *
     * @param factor scaling factor to be applied
     * @return the scaled position
     */
    @JsonIgnore
    default Position getScaledPosition(float factor) {
        final Position position = getPosition();
        return new Position(
                (int) (position.getX() * factor),
                (int) (position.getY() * factor),
                (int) (position.getWidth() * factor),
                (int) (position.getHeight() * factor)
        );
    }

}
