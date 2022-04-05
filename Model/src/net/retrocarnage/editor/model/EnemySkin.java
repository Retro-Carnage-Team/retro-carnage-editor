package net.retrocarnage.editor.model;

/**
 * EnemySkin holds the names of skins for enemies of type Person.
 *
 * @author Thomas Werner
 */
public enum EnemySkin {

    WoodlandWithSMG("enemy-0", "Woodland camouflage / sub machine gun"),
    GreyJumperWithRifle("enemy-1", "Grey jumpsuit / rifle"),
    DigitalWithPistols("enemy-2", "Digital camouflage / pistols"),
    WoodlandWithBulletproofVest("enemy-3", "Woodland camouflage & vest / pistol");

    private final String name;
    private final String label;

    private EnemySkin(final String name, final String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public static EnemySkin findByName(final String name) {
        for (EnemySkin es : EnemySkin.values()) {
            if (es.getName().equals(name)) {
                return es;
            }
        }
        return null;
    }

}
