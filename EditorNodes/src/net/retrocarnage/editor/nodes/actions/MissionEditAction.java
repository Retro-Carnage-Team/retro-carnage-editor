package net.retrocarnage.editor.nodes.actions;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import net.retrocarnage.editor.model.Mission;
import org.openide.util.Lookup;

/**
 * Extension point to invoke a level editor when a mission is selected.
 *
 * @author Thomas Werner
 */
public class MissionEditAction extends AbstractAction {

    private static final Logger logger = Logger.getLogger(MissionEditAction.class.getName());
    private final Mission mission;

    public MissionEditAction(final Mission mission) {
        this.mission = mission;
        putValue(NAME, "Edit Level");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final OpenGamePlayEditorHandler handler = Lookup.getDefault().lookup(OpenGamePlayEditorHandler.class);
        if (null == handler) {
            logger.log(Level.WARNING, "No implementation for OpenGamePlayEditorHandler found");
        } else {
            handler.openGamePlayEditor(mission);
        }
    }

    /**
     * Interface used to break the dependency to the GamePlayEditor.
     *
     * Is implemented by some part of the GamePlayEditor and placed in the global lookup.
     */
    public static interface OpenGamePlayEditorHandler {

        void openGamePlayEditor(Mission mission);

    }

}
