package net.retrocarnage.editor.layerselector.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeSelectionModel;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Selectable;
import net.retrocarnage.editor.nodes.nodes.LayerChildrenCurrentEditor;
import net.retrocarnage.editor.nodes.nodes.SelectableNode;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the Layers of the GamePlayEditor.
 */
@ConvertAsProperties(
        dtd = "-//net.retrocarnage.editor.layerselector.gui//LayerSelector//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LayerSelectorTopComponent",
        iconBase = "/net/retrocarnage/editor/layerselector/icon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "rightSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "net.retrocarnage.editor.layerselector.gui.LayerSelectorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LayerSelectorAction",
        preferredID = "LayerSelectorTopComponent"
)
@Messages({
    "CTL_LayerSelectorAction=Layers",
    "CTL_LayerSelectorTopComponent=Layers",
    "HINT_LayerSelectorTopComponent=List of Layers"
})
public final class LayerSelectorTopComponent extends TopComponent implements ExplorerManager.Provider {

    private static final Logger logger = Logger.getLogger(LayerSelectorTopComponent.class.getName());
    private static final String NEW_LAYER_TEXT = "Name";
    private static final String NEW_LAYER_TITLE = "Please specify a name for the new Layer";

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final VetoableChangeListener selectionChangeListener = (PropertyChangeEvent e) -> handleSelectionChanged(e);
    private SelectionController selectionCtrl;

    public LayerSelectorTopComponent() {
        final ActionMap map = getActionMap();
        associateLookup(ExplorerUtils.createLookup(explorerManager, map));

        initComponents();

        final LookupListener lookupListener = (final LookupEvent le) -> handleSelectionControllerChanged();
        final Lookup.Result<SelectionController> selectionCtrlLookupResult = GamePlayEditorProxyFactory
                .INSTANCE
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookupResult(SelectionController.class);
        selectionCtrlLookupResult.addLookupListener(lookupListener);

        final BeanTreeView view = new BeanTreeView();
        view.setRootVisible(false);
        view.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        explorerManager.setRootContext(new AbstractNode(new LayerChildrenCurrentEditor()));
        explorerManager.addPropertyChangeListener((pce) -> {
            if ("selectedNodes".equals(pce.getPropertyName())) {
                handleExplorerNodeChange(pce);
            }
        });
        add(view, BorderLayout.CENTER);

        setName(Bundle.CTL_LayerSelectorTopComponent());
        setToolTipText(Bundle.HINT_LayerSelectorTopComponent());

        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlActions = new javax.swing.JPanel();
        btnAddLayer = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlActions.setLayout(new java.awt.GridLayout(1, 0));

        org.openide.awt.Mnemonics.setLocalizedText(btnAddLayer, org.openide.util.NbBundle.getMessage(LayerSelectorTopComponent.class, "LayerSelectorTopComponent.btnAddLayer.text")); // NOI18N
        btnAddLayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLayerActionPerformed(evt);
            }
        });
        pnlActions.add(btnAddLayer);

        add(pnlActions, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddLayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLayerActionPerformed
        final LayerController controller = GamePlayEditorProxyFactory
                .INSTANCE
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookup(LayerController.class);
        if (null == controller) {
            return;
        }

        final NotifyDescriptor.InputLine inputLine = new NotifyDescriptor.InputLine(NEW_LAYER_TEXT, NEW_LAYER_TITLE);
        final Object result = DialogDisplayer.getDefault().notify(inputLine);
        if (NotifyDescriptor.OK_OPTION.equals(result)) {
            final Layer newLayer = new Layer();
            newLayer.setLocked(false);
            newLayer.setName(inputLine.getInputText());
            newLayer.setVisible(true);
            controller.addLayer(newLayer);
        }
    }//GEN-LAST:event_btnAddLayerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddLayer;
    private javax.swing.JPanel pnlActions;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void componentActivated() {
        ExplorerUtils.activateActions(explorerManager, true);
    }

    @Override
    protected void componentDeactivated() {
        ExplorerUtils.activateActions(explorerManager, false);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        // String version = p.getProperty("version");
    }

    /**
     * Invoked when the user selected a visual asset.
     *
     * Used to synchronize the selection in the SelectionController of the current editor window.
     *
     * @param pce the event that caused this
     */
    void handleExplorerNodeChange(final PropertyChangeEvent pce) {
        if (null == selectionCtrl) {
            logger.log(Level.SEVERE, "Inconsistent component state");
            return;
        }

        final Node[] selectedNodes = (Node[]) pce.getNewValue();
        if ((null != selectedNodes) && (1 == selectedNodes.length)) {
            Selectable selectable = null;
            if (selectedNodes[0] instanceof SelectableNode) {
                selectable = ((SelectableNode) selectedNodes[0]).getSelectable();
                if (null == selectionCtrl.getSelection() || !selectionCtrl.getSelection().equals(selectable)) {
                    selectionCtrl.setSelection(selectable);
                }
            } else {
                selectionCtrl.invalidateSelection();
            }
        }            
    }

    /**
     * Called by a prop listener when the editor window changed - and thus the selection controller changes as well.
     */
    private void handleSelectionControllerChanged() {
        if (null != selectionCtrl) {
            selectionCtrl.removeVetoableChangeListener(selectionChangeListener);
        }

        selectionCtrl = GamePlayEditorProxyFactory
                .INSTANCE
                .buildGamePlayEditorProxy()
                .getLookup()
                .lookup(SelectionController.class);                
        if (null != selectionCtrl) {
            selectionCtrl.addVetoableChangeListener(selectionChangeListener);
            SwingUtilities.invokeLater(() -> changeSelection(selectionCtrl.getSelection()));
        }
    }

    /**
     * Called by a prop listener when the selection within a editor window changed. This means we have to synchronize
     * the selection in the bean tree view.
     *
     * @param pce the event that caused this
     */
    private void handleSelectionChanged(final PropertyChangeEvent pce) {
        if (SelectionController.PROPERTY_SELECTION.equals(pce.getPropertyName())) {
            changeSelection((Selectable) pce.getNewValue());
        }
    }

    /**
     * Changes the node selection to match the given Selectable
     *
     * @param currentSelection the thing to be selected
     */
    private void changeSelection(final Selectable currentSelection) {
        try {
            boolean clearSelection = true;
            if (null != currentSelection) {
                final Node nodeForSelection = findNodeForSelection(
                        explorerManager.getRootContext(),
                        currentSelection
                );
                if (null != nodeForSelection) {
                    explorerManager.setSelectedNodes(new Node[]{nodeForSelection});
                    clearSelection = false;
                }
            }
            if (clearSelection) {
                explorerManager.setSelectedNodes(new Node[]{});
            }
        } catch (PropertyVetoException ex) {
            logger.log(Level.WARNING, "Failed to change selection", ex);            
        }
    }

    /**
     * Recursively searches a tree of Nodes for something that matches the current selection.
     *
     * @param parent the node parent to be searched
     * @param selection the current selection (to be matched by the node)
     * @return the matching Node or null
     */
    private static Node findNodeForSelection(final Node parent, final Selectable selection) {
        if ((parent instanceof SelectableNode) && ((SelectableNode) parent).getSelectable() == selection) {
            return parent;
        }
        for (Node child : parent.getChildren().getNodes()) {
            final Node childMatch = findNodeForSelection(child, selection);
            if (null != childMatch) {
                return childMatch;
            }
        }
        return null;
    }

}
