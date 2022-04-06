package net.retrocarnage.editor.missionexporter;

import java.util.logging.Logger;
import net.retrocarnage.editor.model.Mission;

/**
 * Exports missions to a user specified location.
 *
 * @author Thomas Werner
 */
public class MissionExporter {

    private static final Logger logger = Logger.getLogger(MissionExporter.class.getName());

    public void exportMission(final Mission mission) {
        logger.info("Exporting mission...");
    }

}
