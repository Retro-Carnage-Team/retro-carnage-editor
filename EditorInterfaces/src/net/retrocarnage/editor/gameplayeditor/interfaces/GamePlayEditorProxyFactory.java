package net.retrocarnage.editor.gameplayeditor.interfaces;

import net.retrocarnage.editor.gameplayeditor.interfaces.impl.GamePlayEditorProxy;
import org.openide.util.Lookup;

/**
 * Provides access to the Lookup of the active GamePlayEditor window.
 *
 * @author Thomas Werner
 */
public enum GamePlayEditorProxyFactory {

    INSTANCE;
    
    private final Lookup.Provider gamePlayEditorProxy;
 
    private GamePlayEditorProxyFactory() {
        gamePlayEditorProxy = new GamePlayEditorProxy();
    }
 
    public Lookup.Provider buildGamePlayEditorProxy() {
        return gamePlayEditorProxy;
    }
    
}
