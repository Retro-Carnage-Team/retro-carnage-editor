package net.retrocarnage.editor.gameplayeditor.interfaces;

import net.retrocarnage.editor.gameplayeditor.interfaces.impl.GamePlayEditorProxy;
import org.openide.util.Lookup;

/**
 * Provides access to the Lookup of the active GamePlayEditor window.
 *
 * @author Thomas Werner
 */
public class GamePlayEditorProxyFactory {
    
    private static class SingletonHelper {
        private static final Lookup.Provider gamePlayEditorProxy = new GamePlayEditorProxy();
    }
    
    private GamePlayEditorProxyFactory() { }

    public static Lookup.Provider buildGamePlayEditorProxy() {
        return SingletonHelper.gamePlayEditorProxy;
    }
    
}