package net.retrocarnage.editor.model;

/**
 * A clock-wise rotation.
 *
 * @author Thomas Werner
 */
public enum Rotation {

    NONE(0),
    ONE_QUARTER(90),
    HALF(180),
    THREE_QUARTERS(270);

    private final int degrees;

    private Rotation(final int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

    @Override
    public String toString() {
        return Integer.toString(degrees) + " degrees";
    }

}
