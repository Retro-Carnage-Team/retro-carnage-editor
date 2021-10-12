package net.retrocarnage.editor.gameplayeditor.impl;

import java.beans.PropertyChangeEvent;
import net.retrocarnage.editor.gameplayeditor.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.gui.GamePlayEditorTopComponent;
import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import static org.openide.windows.TopComponent.Registry.PROP_ACTIVATED;
import static org.openide.windows.TopComponent.Registry.PROP_TC_CLOSED;

/**
 * Implementation of the abstract GamePlayEditorProxy class.
 *
 * @author Thomas Werner
 */
public class GamePlayEditorProxyImpl extends GamePlayEditorProxy {

    private final ProxyLookup.Controller controller = new ProxyLookup.Controller();
    private final ProxyLookup proxy = new ProxyLookup(controller);

    public GamePlayEditorProxyImpl() {
        TopComponent
                .getRegistry()
                .addPropertyChangeListener((final PropertyChangeEvent pce) -> handleTopComponentRegistryChange(pce));
    }

    @Override
    public Lookup getLookup() {
        return proxy;
    }

    private void handleTopComponentRegistryChange(final PropertyChangeEvent pce) {
        if (PROP_ACTIVATED.equals(pce.getPropertyName()) && (pce.getNewValue() instanceof GamePlayEditorTopComponent)) {
            final TopComponent tc = (TopComponent) pce.getNewValue();
            controller.setLookups(tc.getLookup());
        } else if (PROP_TC_CLOSED.equals(pce.getPropertyName())) {
            final long numberOfGamePlayEditors = TopComponent
                    .getRegistry()
                    .getOpened()
                    .stream()
                    .filter((tc) -> tc instanceof GamePlayEditorTopComponent)
                    .count();
            if (0 == numberOfGamePlayEditors) {
                controller.setLookups();
            }
        }
    }

}
