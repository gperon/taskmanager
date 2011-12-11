/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions.wizards;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Date;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
//@ActionID(category = "Task", id = "com.netbeansrcp.taskactions.wizards.NewTaskWizardAction")
//@ActionRegistration(displayName = "Open NewTask Wizard")
//@ActionReference(path = "Menu/Tools", position = 700)
public final class NewTaskWizardAction implements ActionListener {

    private WizardDescriptor.Panel[] panels;

    public @Override
    void actionPerformed(ActionEvent e) {
        actionPerformed("");
    }

    void actionPerformed(String parentId) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Creating a Task");
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("com/netbeansrcp/taskactions/TaskBackground.png"));
        wizardDescriptor.putProperty(NewTaskConstants.PARENT_ID, parentId);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            TaskManager taskMgr = Lookup.getDefault().lookup(TaskManager.class);
            if (taskMgr != null) {
                Task task = null;
                if (parentId != null) {
                    task = taskMgr.createTask((String) wizardDescriptor.getProperty(NewTaskConstants.NAME), parentId);
                } else {
                    task = taskMgr.createTask().getLookup().lookup(Task.class);
                    task.setName((String) wizardDescriptor.getProperty(NewTaskConstants.NAME));
                }
                task.setDue((Date) wizardDescriptor.getProperty(NewTaskConstants.DUE));
                task.setPrio((Task.Priority) wizardDescriptor.getProperty(NewTaskConstants.PRIORITY));
                task.setProgr((Integer) wizardDescriptor.getProperty(NewTaskConstants.PROGRESS));
                task.setDescr((String) wizardDescriptor.getProperty(NewTaskConstants.DESCRIPTION));
            }
        }
    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                new NewTaskWizardPanel1(),
                new NewTaskWizardPanel2(),
                new NewTaskWizardPanel3()
            };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
                }
            }
        }
        return panels;
    }
}
