package com.formdev.flatlaf;

import javax.swing.SwingUtilities;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        FlatLightLaf.setup();
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            SwingUtilities.updateComponentTreeUI(WindowManager.getDefault().getMainWindow());
        });
    }

}
