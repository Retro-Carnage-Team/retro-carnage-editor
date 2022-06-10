package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * A rectangle.
 *
 * @author Thomas Werner
 */
@JsonPropertyOrder({"x", "y", "width", "height"})
public class Position {

    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Default constructor. Initializes location and dimension with 0, 0.
     */
    public Position() {
        // This constructor is intentionally empty.
    }

    public Position(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Position clone() {
        return new Position(x, y, width, height);
    }

    public java.awt.Rectangle toRectangle() {
        return new java.awt.Rectangle(x, y, width, height);
    }
    
    /**
     * @return the new Position
     */
    @JsonIgnore
    public java.awt.Point getCenter() {
        return new java.awt.Point(x + (width / 2), y + (height / 2));
    }

    /**
     * Returns the position - scaled by a given factor.
     *
     * @param factor scaling factor to be applied
     * @return the scaled position
     */
    public Position scale(float factor) {
        if (1.0f == factor) {
            return this;
        }
        return new Position(
                (int) (getX() * factor),
                (int) (getY() * factor),
                (int) (getWidth() * factor),
                (int) (getHeight() * factor)
        );
    }
}
