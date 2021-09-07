package net.retrocarnage.editor.model;

/**
 * Values that can be set as EnemyAction.
 * 
 * @author Thomas Werner
 */
public enum EnemyActionType {
 
    Bullet("bullet"),
    Grenade("grenade");
    
    private final String value;
    
    private EnemyActionType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
