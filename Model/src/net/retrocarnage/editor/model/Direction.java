package net.retrocarnage.editor.model;

/**
 * Direction specifies one of eight possible directions (cardinal and diagonal).
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/engine/geometry/directions.go
 */
public enum Direction {
        
    UP("up"),
    UP_RIGHT("up_right"),
    RIGHT("right"),
    DOWN_RIGHT("down_right"),
    DOWN("down"),
    DOWN_LEFT("down_left"),
    LEFT("left"),
    UP_LEFT("up_left");
    
    private final String value;
    
    private Direction(String value) {
        this.value = value;
    }    
 
    public String getValue() {
        return value;
    }
    
}
