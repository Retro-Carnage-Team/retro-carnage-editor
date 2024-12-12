package net.retrocarnage.editor.model;

/**
 * Values that can be set as EnemyAction.
 * 
 * @author Thomas Werner
 */
public enum EnemyActionType {
 
    BULLET("bullet"),
    GRENADE("grenade");
    
    private final String value;
    
    private EnemyActionType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
