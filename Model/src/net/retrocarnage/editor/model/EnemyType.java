package net.retrocarnage.editor.model;

/**
 * Enumeration of different types of enemies.
 *
 * @author Thomas Werner
 */
public enum EnemyType {

    Person(0, "Person"),
    Landmine(1, "Landmine");

    private final String name;
    private final int value;

    private EnemyType(final int value, final String name) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static EnemyType findByValue(final int value) {
        for (EnemyType et : EnemyType.values()) {
            if (value == et.value) {
                return et;
            }
        }
        return null;
    }

}
