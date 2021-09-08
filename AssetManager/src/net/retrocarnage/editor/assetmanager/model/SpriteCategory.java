package net.retrocarnage.editor.assetmanager.model;

/** 
 * Categrories that can be used to organize collections of sprites.
 *
 * @author Thomas Werner
 */
public enum SpriteCategory {
    
    Houses("Houses"), 
    Trees("Trees"), 
    Obstacles("Obstacles");
    
    private final String value;
    
    private SpriteCategory(final String value) {
        this.value = value;
    }
    
}
