package net.retrocarnage.editor.sectioneditor;

import java.util.Collection;
import net.retrocarnage.editor.model.gameplay.GamePlay;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * GUI Controller class for the SectionEditor.
 *
 * @author Thomas Werner
 */
public class SectionEditorController implements LookupListener {

    private Lookup.Result<GamePlay> lookupResult;

    SectionEditorController() {
        lookupResult = Utilities.actionsGlobalContext().lookupResult(GamePlay.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    public void resultChanged(final LookupEvent le) {
        Collection<? extends GamePlay> items = lookupResult.allInstances();
        if (items.isEmpty()) {
            System.out.println("No gameplay selected");
        } else {
            final GamePlay gp = items.iterator().next();
            System.out.println("GamePlay selected: " + gp.getMissionId());
        }
    }

    void close() {
        lookupResult.removeLookupListener(this);
    }

}
