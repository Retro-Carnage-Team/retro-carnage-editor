package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import net.retrocarnage.editor.missionexporter.MissionExporter;
import net.retrocarnage.editor.model.Mission;

/**
 * Exports the selected mission to a user specified location (typically a Retro-Carnage installation).
 *
 * @author Thomas Werner
 */
public class MissionExportAction extends AbstractAction {

    private final Mission mission;

    public MissionExportAction(final Mission mission) {
        this.mission = mission;
        putValue(NAME, "Export Level");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        new MissionExporter().exportMission(mission);
    }
}
