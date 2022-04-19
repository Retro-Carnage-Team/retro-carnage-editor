package net.retrocarnage.editor.model;

/**
 * Something that can be selected.
 *
 * This might be e.g. a VisualAsset or an Enemy.
 *
 * @author Thomas Werner
 */
public interface Selectable {

    public static final String PROPERTY_POSITION = "position";

    boolean isMovable();

    boolean isResizable();

    Position getPosition();

    void setPosition(final Position position);

}
