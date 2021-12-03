package net.retrocarnage.editor.model;

/**
 * A blocker is a gameplay area that block something (like players, bullets, explosives, ...).
 *
 * @author Thomas Werner
 */
public interface Blocker extends Selectable {

    boolean isBulletStopper();

    void setBulletStopper(boolean stopsBullets);

    boolean isExplosiveStopper();

    void setExplosiveStopper(boolean stopsExplosives);

    boolean isObstacle();

    void setObstacle(boolean obstacle);

}
