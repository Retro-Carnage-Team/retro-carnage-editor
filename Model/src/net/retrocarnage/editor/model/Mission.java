package net.retrocarnage.editor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mission for Retro-Carnage.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/mission.go
 */
public class Mission {

    private String id;
    private String briefing;
    private String clientAssetId;
    private Location location;
    private String song;
    private String name;
    private int reward;
    private List<Segment> segments;

    public Mission() {
        segments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(final String briefing) {
        this.briefing = briefing;
    }

    public String getClientAssetId() {
        return clientAssetId;
    }

    public void setClientAssetId(final String client) {
        this.clientAssetId = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public String getSong() {
        return song;
    }

    public void setSong(final String song) {
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(final int reward) {
        this.reward = reward;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(final List<Segment> segments) {
        this.segments = segments;
    }

    /**
     * This creates a copy of this Mission - but only of those properties that are required to manage the mission. Thus
     * the copy will contain no information about gameplay data of the mission.
     *
     * @return a partial copy
     */
    public Mission getPartialCopyOfMetaData() {
        final Mission partialCopy = new Mission();
        partialCopy.setBriefing(getBriefing());
        partialCopy.setClientAssetId(getClientAssetId());
        partialCopy.setId(getId());
        if (null != getLocation()) {
            partialCopy.setLocation(new Location(getLocation().getLatitude(), getLocation().getLongitude()));
        }
        partialCopy.setName(getName());
        partialCopy.setReward(getReward());
        partialCopy.setSong(getSong());
        return partialCopy;
    }

}
