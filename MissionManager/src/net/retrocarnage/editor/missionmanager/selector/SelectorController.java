package net.retrocarnage.editor.missionmanager.selector;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import net.retrocarnage.editor.missionmanager.MissionEditor;
import net.retrocarnage.editor.missionmanager.MissionService;
import static net.retrocarnage.editor.missionmanager.MissionService.PROPERTY_MISSIONS;
import net.retrocarnage.editor.model.Mission;
import org.openide.util.Lookup;

/**
 * Controller for the SelectorTopComponent.
 *
 * @author Thomas Werner
 */
class SelectorController {

    private static final Logger logger = Logger.getLogger(SelectorController.class.getName());

    private final PropertyChangeListener serviceChangeListener;
    private DefaultListModel<Mission> listModel;

    SelectorController() {
        final MissionService missionService = MissionService.getDefault();
        serviceChangeListener = (PropertyChangeEvent pce) -> {
            if (PROPERTY_MISSIONS.equals(pce.getPropertyName()) && null != listModel) {
                listModel.clear();
                listModel.addAll(missionService.getMissions());
            }
        };
        missionService.addPropertyChangeListener(serviceChangeListener);
    }

    void close() {
        MissionService.getDefault().removePropertyChangeListener(serviceChangeListener);
    }

    ListModel<Mission> getListModel() {
        if (null == listModel) {
            listModel = new DefaultListModel<>();
            listModel.addAll(MissionService.getDefault().getMissions());
        }
        return listModel;
    }

    void editMission(int selectedIndex) {
        final Mission mission = listModel.get(selectedIndex);
        final MissionEditor editor = Lookup.getDefault().lookup(MissionEditor.class);
        if (null != editor) {
            editor.open(mission);
        } else {
            logger.log(Level.WARNING, "Found no implementation of MissionEditor interface :(");
        }
    }

}
