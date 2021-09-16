package net.retrocarnage.editor.missionmanager.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.retrocarnage.editor.model.Mission;

/**
 * A database containing the missions managed.
 *
 * @author Thomas Werner
 */
class MissionDatabase {

    private final Map<String, Mission> missions;

    MissionDatabase() {
        missions = new ConcurrentHashMap<>();
    }

    Mission getMission(final String id) {
        return missions.get(id);
    }

    void putMission(final Mission mission) {
        missions.put(mission.getId(), mission);
    }

    /**
     * Restores a persisted state from given source.
     *
     * @param in InputStream containing the persisted state.
     * @throws IOException
     */
    public void load(final InputStream in) throws IOException {
        final ObjectMapper xmlMapper = new XmlMapper();
        final MissionDatabaseFile dataStore = xmlMapper.readValue(in, MissionDatabaseFile.class);
        missions.clear();
        dataStore.getMissions().forEach(m -> missions.put(m.getId(), m));
    }

    /**
     * Save the current state to OutputStream.
     *
     * @param out Stream to write to
     * @throws IOException
     */
    public void save(final OutputStream out) throws IOException {
        final MissionDatabaseFile dataStore = new MissionDatabaseFile();
        dataStore.getMissions().addAll(missions.values());
        new XmlMapper().writeValue(out, dataStore);
    }

}
