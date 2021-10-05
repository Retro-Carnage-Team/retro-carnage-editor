package net.retrocarnage.editor.gameplayeditor;

import net.retrocarnage.editor.gameplayeditor.impl.GamePlayEditorRepository;
import net.retrocarnage.editor.model.Mission;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.gameplayeditor//GamePlayEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "GamePlayEditorTopComponent",
        iconBase = "net/retrocarnage/editor/gameplayeditor/accessories-text-editor.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_GamePlayEditorAction=GamePlayEditor",
    "CTL_GamePlayEditorTopComponent=GamePlayEditor Window",
    "HINT_GamePlayEditorTopComponent=This is a GamePlayEditor window"
})
public final class GamePlayEditorTopComponent extends TopComponent {

    private final Mission mission;

    public GamePlayEditorTopComponent() {
        this(null);
    }

    public GamePlayEditorTopComponent(final Mission mission) {
        this.mission = mission;

        initComponents();
        setName(Bundle.CTL_GamePlayEditorTopComponent());
        setToolTipText(Bundle.HINT_GamePlayEditorTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        if (null != mission) {
            GamePlayEditorRepository.INSTANCE.register(mission.getId(), this);
        }
    }

    @Override
    public void componentClosed() {
        if (null != mission) {
            GamePlayEditorRepository.INSTANCE.unregister(mission.getId(), this);
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }
}