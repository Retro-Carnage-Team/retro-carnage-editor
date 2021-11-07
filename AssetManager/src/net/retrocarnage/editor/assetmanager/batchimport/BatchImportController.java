package net.retrocarnage.editor.assetmanager.batchimport;

import java.util.ArrayList;
import java.util.List;
import org.openide.WizardDescriptor;

/**
 * A controller for the BatchImportWizard.
 *
 * @author Thomas Werner
 */
public class BatchImportController {

    private final BatchImportWizardPanel1 panel1;
    private final BatchImportWizardPanel2 panel2;
    private final BatchImportWizardPanel3 panel3;

    BatchImportController() {
        panel1 = new BatchImportWizardPanel1();
        panel2 = new BatchImportWizardPanel2();
        panel3 = new BatchImportWizardPanel3();
    }

    List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        final List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(panel1);
        panels.add(panel2);
        panels.add(panel3);
        return panels;
    }

}
