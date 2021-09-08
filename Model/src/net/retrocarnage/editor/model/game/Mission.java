
package net.retrocarnage.editor.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * A mission for Retro-Carnage.
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/mission.go
 */
public class Mission {

    private String briefing;
    private String client;
    private Location location;
    private String song;
    private String name;
    private int reward;
    private List<Segment> segments;    
    
    public Mission() {
        segments = new ArrayList<>();
    }

    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
    
}
