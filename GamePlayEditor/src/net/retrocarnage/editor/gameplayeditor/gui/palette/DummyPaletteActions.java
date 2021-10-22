package net.retrocarnage.editor.gameplayeditor.gui.palette;

import javax.swing.Action;
import org.netbeans.spi.palette.PaletteActions;
import org.openide.util.Lookup;

/**
 * A dummy implementation of the abstract PaletteActions class.
 *
 * @author Thomas Werner
 */
public class DummyPaletteActions extends PaletteActions {

    @Override
    public Action[] getImportActions() {
        return new Action[]{};
    }

    @Override
    public Action[] getCustomPaletteActions() {
        return new Action[]{};
    }

    @Override
    public Action[] getCustomCategoryActions(Lookup lkp) {
        return new Action[]{};
    }

    @Override
    public Action[] getCustomItemActions(Lookup lkp) {
        return new Action[]{};
    }

    @Override
    public Action getPreferredAction(Lookup lkp) {
        return null;
    }

}
