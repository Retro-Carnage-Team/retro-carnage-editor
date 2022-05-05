package net.retrocarnage.editor.model;

/**
 * A blocker is a gameplay area that block something (like players, bullets, explosives, ...).
 *
 * @author Thomas Werner
 */
public interface Blocker extends Selectable {

    public static final String PROPERTY_OBSTACLE = "Stops players";
    public static final String PROPERTY_BULLETSTOPPER = "Stops bullets";
    public static final String PROPERTY_EXPLOSIVESTOPPER = "Stops explosives";

    boolean isBulletStopper();

    void setBulletStopper(boolean stopsBullets);

    boolean isExplosiveStopper();

    void setExplosiveStopper(boolean stopsExplosives);

    boolean isObstacle();

    void setObstacle(boolean obstacle);

}
