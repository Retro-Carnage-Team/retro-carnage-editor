package net.retrocarnage.editor.model.game;

/**
 * Direction specifies one of eight possible directions (cardinal and diagonal).
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/engine/geometry/directions.go
 */
public enum Direction {
        
    Up("up"),
    UpRight("up_right"),
    Right("right"),
    DownRight("down_right"),
    Down("down"),
    DownLeft("down_left"),
    Left("left"),
    UpLeft("up_left");
    
    private final String value;
    
    private Direction(String value) {
        this.value = value;
    }    
 
    public String getValue() {
        return value;
    }
    
}
