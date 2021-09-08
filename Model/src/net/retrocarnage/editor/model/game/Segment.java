
package net.retrocarnage.editor.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Segment is a part of the Mission that follows one specific direction.
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/segment.go
 */
public class Segment {

    private List<String> backgrounds;
    private String direction;
    private List<Enemy>	enemies;
    private Rectangle goal;
    private List<Obstacle> obstacles;

    public Segment() {
        backgrounds = new ArrayList<>();
        enemies = new ArrayList<>();
        obstacles = new ArrayList<>();
    }    
    
    public List<String> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(List<String> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Rectangle getGoal() {
        return goal;
    }

    public void setGoal(Rectangle goal) {
        this.goal = goal;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }        
    
}
