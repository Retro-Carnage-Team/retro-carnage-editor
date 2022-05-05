package net.retrocarnage.editor.model;

/**
 * Defines various possible speed values for enemies and some human friendly names.
 *
 * @author Thomas Werner
 */
public enum EnemySpeed {

    NONE("None", 0),
    VERY_SLOW("Very slow", 300),
    SLOW("Slow", 500),
    NORMAL("Normal", 750),
    FAST("Fast", 850),
    VERY_FAST("Very fast", 950),
    SPEEDSTER("SPEEDSTER", 1_150);

    private final String name;
    private final int speed;

    private EnemySpeed(final String name, final int speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public static EnemySpeed findBySpeed(final int speed) {
        for (EnemySpeed es : EnemySpeed.values()) {
            if (es.getSpeed() == speed) {
                return es;
            }
        }
        return null;
    }

}
