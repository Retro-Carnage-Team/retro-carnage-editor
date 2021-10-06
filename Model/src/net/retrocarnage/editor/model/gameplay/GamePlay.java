package net.retrocarnage.editor.model.gameplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the gameplay of a Mission.
 *
 * @author Thomas Werner
 */
public class GamePlay {

    private String missionId;
    private List<Section> sections;

    public GamePlay() {
        sections = new ArrayList<>();
    }

    public GamePlay(final String missionId) {
        this();
        this.missionId = missionId;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
