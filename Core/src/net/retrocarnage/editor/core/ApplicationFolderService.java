package net.retrocarnage.editor.core;

import java.nio.file.Path;
import net.retrocarnage.editor.core.impl.ApplicationFolderServiceImpl;
import org.openide.util.Lookup;

/**
 * A service that manages the application folder.
 *
 * The application folder is located in the user's home directory and contains all assets and levels that the user is
 * working on.
 *
 * @author Thomas Werner
 */
public abstract class ApplicationFolderService {

    public abstract Path buildDatabaseFilePath(final String fileName);

    public abstract Path getApplicationFolder();

    /**
     * @return an instance of this service
     */
    public static ApplicationFolderService getDefault() {
        ApplicationFolderService service = Lookup.getDefault().lookup(ApplicationFolderService.class);
        if (null == service) {
            service = new ApplicationFolderServiceImpl();
        }
        return service;
    }

}
