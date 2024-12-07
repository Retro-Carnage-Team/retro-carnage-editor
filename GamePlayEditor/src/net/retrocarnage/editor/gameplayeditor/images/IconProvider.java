package net.retrocarnage.editor.gameplayeditor.images;

import java.io.InputStream;

/**
 * Provides icon images.
 * 
 * @author Thomas Werner
 */
public final class IconProvider {
    
    public enum IconPath {
        ACCESSORIES_TEXT_EDITOR_ICON("accessories-text-editor"),
        DIAGONAL_ICON("diagonal.png"),
        ENEMY_TYPE_0_ICON("enemy-type-0.png"),
        ENEMY_TYPE_1_ICON("enemy-type-1.png");
        
        private final String fileName;
        
        private IconPath(String fileName) {
            this.fileName = fileName;
        } 
    }    
    
    private IconProvider() { 
        // private constructor to prevent instantiation of util class
    }
 
    public static InputStream getIcon(final IconPath icon) {
        return IconProvider.class.getResourceAsStream(icon.fileName);
    }
    
    public static InputStream getIcon(final String iconFileName) throws IllegalArgumentException {
        if(iconFileName.contains("/") || iconFileName.contains("\\")) {
            throw new IllegalArgumentException("Only packaged icons can be used here.");
        }
        return IconProvider.class.getResourceAsStream(iconFileName);
    }

}
