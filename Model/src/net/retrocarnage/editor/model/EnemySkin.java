package net.retrocarnage.editor.model;

/**
 * EnemySkin holds the names of skins for enemies of type Person.
 *
 * @author Thomas Werner
 */
public enum EnemySkin {

    WoodlandWithSMG("enemy-0", "Enemy wearing woodland camouflage and a sub machine gun"),
    GreyJumperWithRifle("enemy-1", "Enemy wearing a grey jumpsuit and a rifle"),
    DigitalWithPistols("enemy-2", "Enemy wearing digital camouflage and two pistols"),
    WoodlandWithBulletproofVest("enemy-3", "Enemy wearing woodland camouflage and a bullet proof vest and a pistol");

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

}
