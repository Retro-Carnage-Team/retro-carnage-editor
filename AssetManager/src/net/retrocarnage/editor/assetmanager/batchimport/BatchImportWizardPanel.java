package net.retrocarnage.editor.assetmanager.batchimport;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class BatchImportWizardPanel implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel.
     */
    private BatchImportVisualPanel component;

    @Override
    public BatchImportVisualPanel getComponent() {
        if (component == null) {
            component = new BatchImportVisualPanel();
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
        // specified in interface but not required for our use case
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        // specified in interface but not required for our use case
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // specified in interface but not required for our use case
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // specified in interface but not required for our use case
    }

}
