package net.retrocarnage.editor.assetmanager;

import net.retrocarnage.editor.assetmanager.impl.AssetServiceImpl;
import net.retrocarnage.editor.core.ApplicationFolderService;

/**
 * A factory for AssetServices.
 * 
 * @author Thomas Werner
 */
public class AssetServiceFactory {
    
    private static class SingletonHelper {
        private static final AssetService serviceInstance = new AssetServiceImpl(ApplicationFolderService.getDefault());
    }
    
    private AssetServiceFactory() { }

    public static AssetService buildAssetService() {
        return SingletonHelper.serviceInstance;
    }
    
}
