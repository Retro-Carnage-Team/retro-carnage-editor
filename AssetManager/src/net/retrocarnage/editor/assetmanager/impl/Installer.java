package net.retrocarnage.editor.assetmanager.impl;

import net.retrocarnage.editor.assetmanager.AssetService;
import net.retrocarnage.editor.assetmanager.AssetServiceFactory;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        final AssetService assetService = AssetServiceFactory.buildAssetService();
        assetService.initializeFolderStructure();
        assetService.loadAssets();
    }

}
