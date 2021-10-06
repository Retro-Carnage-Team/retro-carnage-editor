package net.retrocarnage.editor.model.gameplay;

import java.beans.PropertyChangeSupport;

/**
 * Gameplay sections contain the raw data for a Segment of the game.
 *
 * @author Thomas Werner
 */
public class Section {

    public static final String PROPERTY_DIRECTION = "direction";
    public static final String PROPERTY_NUMBER_OF_SCREENS = "numberOfScreens";

    private final PropertyChangeSupport propertyChangeSupport;

    private SectionDirection direction;
    private int numberOfScreens;

    public Section() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public SectionDirection getDirection() {
        return direction;
    }

    public void setDirection(final SectionDirection direction) {
        final SectionDirection oldValue = this.direction;
        this.direction = direction;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_DIRECTION, oldValue, direction);
    }

    public int getNumberOfScreens() {
        return numberOfScreens;
    }

    public void setNumberOfScreens(final int numberOfScreens) {
        final int oldValue = this.numberOfScreens;
        this.numberOfScreens = numberOfScreens;
        this.propertyChangeSupport.firePropertyChange(PROPERTY_NUMBER_OF_SCREENS, oldValue, numberOfScreens);
    }

}
