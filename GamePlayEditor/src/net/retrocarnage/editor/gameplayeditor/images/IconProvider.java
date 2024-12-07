package net.retrocarnage.editor.gameplayeditor.images;

/**
 * Provides icon images.
 * 
 * @author Thomas Werner
 */
public enum IconProvider {
    
    ACCESSORIES_TEXT_EDITOR_ICON("accessories-text-editor.png"),
    DIAGONAL_ICON("diagonal.png"),
    ENEMY_TYPE_0_ICON("enemy-type-0.png"),
    ENEMY_TYPE_1_ICON("enemy-type-1.png");
        
    private final String fileName;
        
    private IconProvider(final String fileName) {
        this.fileName = getResourcePath(fileName);                    
    } 
    
    public String getResourcePath() {
        return fileName;
    }
    
    public static String getResourcePath(final String iconFileName) throws IllegalArgumentException {
        return "/" + IconProvider.class.getPackageName().replaceAll("\\.", "/") + "/" + iconFileName;
    }       
        
}
