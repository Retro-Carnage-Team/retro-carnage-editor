package net.retrocarnage.editor.nodes.icons;

/**
 * Provides icon images.
 * 
 * @author Thomas Werner
 */
public enum IconProvider {
    
    COPY_ICON("copy.png"),
    DELETE_ICON("delete.png"),
    DOWN_ICON("down.png"),
    ENEMIES_ICON("enemies.png"),
    ENEMY_ICON("enemy.png"),
    GOAL_ICON("goal.png"),
    LAYERS_ICON("layers.png"),
    LAYER_LOCKED_ICON("locked.png"),
    LAYER_UNLOCKED_ICON("unlocked.png"),
    MISSION_ICON("mission.png"),
    OBSTACLES_ICON("obstacles.png"),
    OBSTACLE_ICON("obstacle.png"),
    TILE_ICON("tile.png"),
    UP_ICON("up.png"),
    VISUAL_ASSETS_ICON("visualAssets.png"),
    VISUAL_ASSET_ICON("visualAsset.png");
        
    private final String fileName;
        
    private IconProvider(final String fileName) {
        this.fileName = fileName;
    }     
 
    public String getResourcePath() {
        return fileName;
    }
    
    public static String getResourcePath(final String iconFileName) throws IllegalArgumentException {
        return "/" + IconProvider.class.getPackageName().replace('.', '/') + "/" + iconFileName;
    }    

}
