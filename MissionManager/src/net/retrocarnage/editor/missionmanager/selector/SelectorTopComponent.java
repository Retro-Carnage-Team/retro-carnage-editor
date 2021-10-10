package net.retrocarnage.editor.missionmanager.selector;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import net.retrocarnage.editor.model.Mission;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the list of Missions. Clicking on a mission will open a editor so that the user can work
 * on it.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.missionmanager.selector//Selector//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SelectorTopComponent",
        iconBase = "net/retrocarnage/editor/missionmanager/selector/icon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "net.retrocarnage.editor.missionmanager.selector.SelectorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SelectorAction",
        preferredID = "SelectorTopComponent"
)
@Messages({
    "CTL_SelectorAction=Select a mission",
    "CTL_SelectorTopComponent=Missions",
    "HINT_SelectorTopComponent=List of missions"
})
public final class SelectorTopComponent extends TopComponent {

    private final SelectorController controller;

    public SelectorTopComponent() {
        controller = new SelectorController();

        initComponents();
        setName(Bundle.CTL_SelectorTopComponent());
        setToolTipText(Bundle.HINT_SelectorTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrMissions = new javax.swing.JScrollPane();
        lstMissions = new javax.swing.JList<>();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        lstMissions.setModel(controller.getListModel());
        lstMissions.setCellRenderer(new MissionListCellRenderer());
        lstMissions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstMissionsMouseClicked(evt);
            }
        });
        scrMissions.setViewportView(lstMissions);

        add(scrMissions);
    }// </editor-fold>//GEN-END:initComponents

    private void lstMissionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstMissionsMouseClicked
        if ((evt.getButton() != MouseEvent.BUTTON1) || (evt.getClickCount() != 2)) {
            return;
        }

        final Rectangle r = lstMissions.getCellBounds(0, lstMissions.getLastVisibleIndex());
        if (r != null && r.contains(evt.getPoint())) {
            final int selectedIndex = lstMissions.locationToIndex(evt.getPoint());
            controller.editMission(selectedIndex);
        }

    }//GEN-LAST:event_lstMissionsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<Mission> lstMissions;
    private javax.swing.JScrollPane scrMissions;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        controller.close();
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

}
