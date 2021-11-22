package net.retrocarnage.editor.gameplayeditor.interfaces;

import net.retrocarnage.editor.gameplayeditor.interfaces.impl.GamePlayEditorProxyImpl;
import org.openide.util.Lookup;

/**
 * Provides access to the Lookup of the active GamePlayEditor window.
 *
 * @author Thomas Werner
 */
public abstract class GamePlayEditorProxy implements Lookup.Provider {

    private static final GamePlayEditorProxy gamePlayEditorRegistryImpl = new GamePlayEditorProxyImpl();

    /**
     * @return an instance of this service
     */
    public static GamePlayEditorProxy getDefault() {
        return gamePlayEditorRegistryImpl;
    }

}
