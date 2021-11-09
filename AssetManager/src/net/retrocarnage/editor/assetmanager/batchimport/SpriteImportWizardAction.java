package net.retrocarnage.editor.assetmanager.batchimport;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

@ActionID(
        category = "Tools",
        id = "net.retrocarnage.editor.assetmanager.batchimport.SpriteImportWizardAction"
)
@ActionRegistration(
        iconBase = "net/retrocarnage/editor/assetmanager/batchimport/outline_assistant_black_24dp.png",
        displayName = "#CTL_SpriteImportWizardAction"
)
@ActionReference(path = "Menu/Tools", position = 0)
public final class SpriteImportWizardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        final BatchImportController controller = new BatchImportController();
        final List<WizardDescriptor.Panel<WizardDescriptor>> panels = controller.getPanels();
        final String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            steps[i] = c.getName();
            if (c instanceof JComponent) {
                final JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        final WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Sprite Import Wizard");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            controller.runImport();
        }
    }
}
