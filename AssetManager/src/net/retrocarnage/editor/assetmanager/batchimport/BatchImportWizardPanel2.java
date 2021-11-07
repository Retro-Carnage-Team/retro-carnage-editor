package net.retrocarnage.editor.assetmanager.batchimport;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class BatchImportWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {

    private BatchImportVisualPanel2 component;

    @Override
    public BatchImportVisualPanel2 getComponent() {
        if (component == null) {
            component = new BatchImportVisualPanel2();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
    }

}
