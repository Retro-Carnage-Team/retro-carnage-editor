package net.retrocarnage.editor.assetmanager.impl;

import net.retrocarnage.editor.assetmanager.AssetService;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        final AssetServiceImpl assetService = ((AssetServiceImpl) AssetService.getDefault());
        assetService.initializeFolderStructure();
        assetService.loadAssets();
    }

}
