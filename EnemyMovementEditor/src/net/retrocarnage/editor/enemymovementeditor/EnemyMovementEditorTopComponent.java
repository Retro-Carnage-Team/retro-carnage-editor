package net.retrocarnage.editor.enemymovementeditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.retrocarnage.editor.core.gui.SpinnerCellEditor;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.enemymovementeditor//EnemyMovementEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EnemyMovementEditorTopComponent",
        iconBase = "net/retrocarnage/editor/enemymovementeditor/icon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "rightSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "net.retrocarnage.editor.enemymovementeditor.EnemyMovementEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EnemyMovementEditorAction",
        preferredID = "EnemyMovementEditorTopComponent"
)
@Messages({
    "CTL_EnemyMovementEditorAction=Enemy movements",
    "CTL_EnemyMovementEditorTopComponent=Enemy movements",
    "HINT_EnemyMovementEditorTopComponent=Movements of the selected enemy"
})
public final class EnemyMovementEditorTopComponent extends TopComponent {

    private final EnemyMovementEditorController controller;
    private final PropertyChangeListener controllerChangeListener;

    public EnemyMovementEditorTopComponent() {
        controller = new EnemyMovementEditorController();
        controllerChangeListener = this::handleControllerPropertyChanged;
        controller.addPropertyChangeListener(controllerChangeListener);

        initComponents();
        setName(Bundle.CTL_EnemyMovementEditorTopComponent());
        setToolTipText(Bundle.HINT_EnemyMovementEditorTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDetail = new javax.swing.JPanel();
        scrMovements = new javax.swing.JScrollPane();
        tblMovements = new javax.swing.JTable();
        pnlMovementActions = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        pnlRecordingStatus = new javax.swing.JPanel();
        btnStartRecording = new javax.swing.JButton();
        btnStopRecording = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.pnlDetail.border.title"))); // NOI18N
        pnlDetail.setLayout(new java.awt.BorderLayout());

        tblMovements.setModel(controller.getTableModel());
        tblMovements.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMovements.getSelectionModel().addListSelectionListener(controller.getTableSelectionListener(tblMovements));
        tblMovements.getColumnModel().getColumn(0).setCellEditor(new SpinnerCellEditor());
        tblMovements.getColumnModel().getColumn(1).setCellEditor(new SpinnerCellEditor());
        scrMovements.setViewportView(tblMovements);

        pnlDetail.add(scrMovements, java.awt.BorderLayout.CENTER);

        pnlMovementActions.setPreferredSize(new java.awt.Dimension(180, 40));
        pnlMovementActions.setRequestFocusEnabled(false);

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/retrocarnage/editor/enemymovementeditor/remove.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnReset, org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.btnReset.text")); // NOI18N
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        pnlMovementActions.add(btnReset);

        pnlDetail.add(pnlMovementActions, java.awt.BorderLayout.SOUTH);

        add(pnlDetail, java.awt.BorderLayout.CENTER);

        btnStartRecording.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/retrocarnage/editor/enemymovementeditor/media-playback-start.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnStartRecording, org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.btnStartRecording.text")); // NOI18N
        btnStartRecording.setEnabled(false);
        btnStartRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartRecordingActionPerformed(evt);
            }
        });
        pnlRecordingStatus.add(btnStartRecording);

        btnStopRecording.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/retrocarnage/editor/enemymovementeditor/media-playback-stop.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnStopRecording, org.openide.util.NbBundle.getMessage(EnemyMovementEditorTopComponent.class, "EnemyMovementEditorTopComponent.btnStopRecording.text")); // NOI18N
        btnStopRecording.setEnabled(false);
        btnStopRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopRecordingActionPerformed(evt);
            }
        });
        pnlRecordingStatus.add(btnStopRecording);

        add(pnlRecordingStatus, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        controller.deleteMovements();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnStartRecordingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecordingActionPerformed
        controller.startRecording();
    }//GEN-LAST:event_btnStartRecordingActionPerformed

    private void btnStopRecordingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopRecordingActionPerformed
        controller.stopRecording();
    }//GEN-LAST:event_btnStopRecordingActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStartRecording;
    private javax.swing.JButton btnStopRecording;
    private javax.swing.JPanel pnlDetail;
    private javax.swing.JPanel pnlMovementActions;
    private javax.swing.JPanel pnlRecordingStatus;
    private javax.swing.JScrollPane scrMovements;
    private javax.swing.JTable tblMovements;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        controller.removePropertyChangeListener(controllerChangeListener);
        controller.close();
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

    private void handleControllerPropertyChanged(final PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case EnemyMovementEditorController.PROPERTY_SELECTION:
                btnReset.setEnabled(null != pce.getNewValue());
                break;
            case EnemyMovementEditorController.PROPERTY_ENABLED:
                btnStartRecording.setEnabled(Boolean.TRUE.equals(pce.getNewValue()) && !controller.isRecording());
                btnStopRecording.setEnabled(Boolean.TRUE.equals(pce.getNewValue()) && controller.isRecording());
                break;
            case EnemyMovementEditorController.PROPERTY_RECORDING:
                btnStartRecording.setEnabled(controller.isEnabled() && Boolean.FALSE.equals(pce.getNewValue()));
                btnStopRecording.setEnabled(controller.isEnabled() &&Boolean.TRUE.equals(pce.getNewValue()));                
        }
    }
}
