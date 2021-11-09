package net.retrocarnage.editor.assetmanager.batchimport;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class BatchImportWizardPanel1 implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the component from this class, just use
     * getComponent().
     */
    private BatchImportVisualPanel1 component;

    @Override
    public BatchImportVisualPanel1 getComponent() {
        if (component == null) {
            component = new BatchImportVisualPanel1();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getAuthor().isBlank()) {
            throw new WizardValidationException(null, "Please specify the author if the tiles", null);
        }
        if (null == component.getImportFolder() || !component.getImportFolder().exists()) {
            throw new WizardValidationException(null, "Please select a folder to import", null);
        }
        if (component.getLicenseLink().isBlank() && component.getLicenseText().isBlank()) {
            throw new WizardValidationException(null, "Please specify a license for the tiles", null);
        }
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
