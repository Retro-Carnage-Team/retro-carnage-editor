package net.retrocarnage.editor.sectioneditor;

import net.retrocarnage.editor.core.gui.ButtonCellEditor;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.model.SectionDirection;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the sections of the selected mission.
 *
 * The form shows a preview of the mission in a mini-map and has various controls to modify the sections.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.sectioneditor//SectionEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SectionEditorTopComponent",
        iconBase = "net/retrocarnage/editor/sectioneditor/icon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "net.retrocarnage.editor.sectioneditor.SectionEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SectionEditorAction",
        preferredID = "SectionEditorTopComponent"
)
@Messages({
    "CTL_SectionEditorAction=Sections",
    "CTL_SectionEditorTopComponent=Sections",
    "HINT_SectionEditorTopComponent=Sections of the selected mission"
})
public final class SectionEditorTopComponent extends TopComponent {

    private final SectionEditorController controller;

    public SectionEditorTopComponent() {
        controller = new SectionEditorController();
        controller.addPropertyChangeListener((PropertyChangeEvent pce) -> handleControllerPropertyChanged(pce));

        initComponents();
        setName(Bundle.CTL_SectionEditorTopComponent());
        setToolTipText(Bundle.HINT_SectionEditorTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPreview = new javax.swing.JPanel();
        lblMap = new SectionMapLabel();
        scrSections = new javax.swing.JScrollPane();
        tblSections = new javax.swing.JTable();
        pnlActions = new javax.swing.JPanel();
        btnAddSection = new javax.swing.JButton();
        btnRemoveSection = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlPreview.setPreferredSize(new java.awt.Dimension(411, 411));

        lblMap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlPreviewLayout = new javax.swing.GroupLayout(pnlPreview);
        pnlPreview.setLayout(pnlPreviewLayout);
        pnlPreviewLayout.setHorizontalGroup(
            pnlPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMap, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPreviewLayout.setVerticalGroup(
            pnlPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMap, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pnlPreview, java.awt.BorderLayout.PAGE_START);

        tblSections.setModel(controller.getTableModel());
        tblSections.setRowHeight(24);
        new ButtonCellEditor(tblSections, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int modelRow = Integer.valueOf(e.getActionCommand());
                final Section section = controller.getSections().get(modelRow);
                controller.increaseLengthOfSection(section);
            }
        }, 2);

        new ButtonCellEditor(tblSections, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int modelRow = Integer.valueOf(e.getActionCommand());
                final Section section = controller.getSections().get(modelRow);
                controller.decreaseLengthOfSection(section);
            }
        }, 3);

        tblSections
        .getSelectionModel()
        .addListSelectionListener(controller.getTableSelectionListener(tblSections));

        final JComboBox cmbDirections = new JComboBox();
        for(SectionDirection sd: SectionDirection.values()) {
            cmbDirections.addItem(sd);
        }
        tblSections.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(cmbDirections));
        scrSections.setViewportView(tblSections);

        add(scrSections, java.awt.BorderLayout.CENTER);

        pnlActions.setLayout(new java.awt.GridLayout(1, 0));

        org.openide.awt.Mnemonics.setLocalizedText(btnAddSection, org.openide.util.NbBundle.getMessage(SectionEditorTopComponent.class, "SectionEditorTopComponent.btnAddSection.text")); // NOI18N
        btnAddSection.setEnabled(false);
        btnAddSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSectionActionPerformed(evt);
            }
        });
        pnlActions.add(btnAddSection);

        org.openide.awt.Mnemonics.setLocalizedText(btnRemoveSection, org.openide.util.NbBundle.getMessage(SectionEditorTopComponent.class, "SectionEditorTopComponent.btnRemoveSection.text")); // NOI18N
        btnRemoveSection.setEnabled(false);
        btnRemoveSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSectionActionPerformed(evt);
            }
        });
        pnlActions.add(btnRemoveSection);

        add(pnlActions, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSectionActionPerformed
        controller.addSection();
    }//GEN-LAST:event_btnAddSectionActionPerformed

    private void btnRemoveSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSectionActionPerformed
        controller.deleteSection();
    }//GEN-LAST:event_btnRemoveSectionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSection;
    private javax.swing.JButton btnRemoveSection;
    private javax.swing.JLabel lblMap;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlPreview;
    private javax.swing.JScrollPane scrSections;
    private javax.swing.JTable tblSections;
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

    private void handleControllerPropertyChanged(final PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case SectionEditorController.PROPERTY_ENABLED:
                btnAddSection.setEnabled(Objects.equals(Boolean.TRUE, pce.getNewValue()));
                btnRemoveSection.setEnabled(
                        Objects.equals(Boolean.TRUE, pce.getNewValue())
                        && (null != controller.getSelectedSection())
                );
                break;
            case SectionEditorController.PROPERTY_SELECTION:
                btnRemoveSection.setEnabled(null != pce.getNewValue());
                break;
            case SectionEditorController.PROPERTY_SECTIONS:
                ((SectionMapLabel) lblMap).setSections((List<Section>) pce.getNewValue());
                break;
            default:
            // ignore this
        }
    }
}
