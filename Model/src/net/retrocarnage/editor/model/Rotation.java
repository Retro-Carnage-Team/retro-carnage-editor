package net.retrocarnage.editor.model;

/**
 * A clock-wise rotation.
 *
 * @author Thomas Werner
 */
public enum Rotation {

    None(0),
    OneQuarter(90),
    TwoQuarters(180),
    ThreeQuarters(270);

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
