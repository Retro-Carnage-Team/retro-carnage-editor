package net.retrocarnage.editor.core.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.retrocarnage.editor.core.ApplicationFolderService;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        try {
            ((ApplicationFolderServiceImpl) ApplicationFolderService.getDefault()).initializeApplicationFolder();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to initialize application folder", ex);
        }

        WindowManager.getDefault().invokeWhenUIReady(() -> 
            ((JFrame) WindowManager.getDefault().getMainWindow()).setTitle("RETRO CARNAGE EDITOR")
        );
    }

}
