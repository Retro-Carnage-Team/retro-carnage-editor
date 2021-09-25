package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String client;
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

    /**
     * @return the ID of a sprite asset used for the client
     */
    public String getClient() {
        return client;
    }

    /**
     * Updates the client for the mission.
     *
     * @param client ID of a sprite asset
     */
    public void setClient(final String client) {
        this.client = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    /**
     * @return the ID of a music asset used as background music
     */
    public String getSong() {
        return song;
    }

    /**
     * Updates the song used as background music for the mission.
     *
     * @param song ID of a music asset
     */
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
    @JsonIgnore
    public Mission getPartialCopyOfMetaData() {
        final Mission partialCopy = new Mission();
        partialCopy.setBriefing(getBriefing());
        partialCopy.setClient(getClient());
        partialCopy.setId(getId());
        if (null != getLocation()) {
            partialCopy.setLocation(new Location(getLocation().getLatitude(), getLocation().getLongitude()));
        }
        partialCopy.setName(getName());
        partialCopy.setReward(getReward());
        partialCopy.setSong(getSong());
        return partialCopy;
    }

    /**
     * This overwrites the values of this instance with the values of the given Mission - but only of those properties
     * that are required to manage the mission. Thus gameplay will not be affected.
     *
     * @param otherMission Mission object to copy the values from
     */
    public void applyPartialChangesOfMetaData(final Mission otherMission) {
        setBriefing(otherMission.getBriefing());
        setClient(otherMission.getClient());
        setId(otherMission.getId());
        setLocation(otherMission.getLocation());
        setName(otherMission.getName());
        setReward(otherMission.getReward());
        setSong(otherMission.getSong());
    }

}
