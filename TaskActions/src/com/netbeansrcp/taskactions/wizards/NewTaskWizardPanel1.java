/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions.wizards;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class NewTaskWizardPanel1 implements WizardDescriptor.Panel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private Component component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new NewTaskVisualPanel1();
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
//        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
         return new HelpCtx(NewTaskWizardPanel1.class);
    }

    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }

    public final void addChangeListener(ChangeListener l) {
    }

    public final void removeChangeListener(ChangeListener l) {
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
        WizardDescriptor wd = (WizardDescriptor) settings;
        String parentId = (String) wd.getProperty(NewTaskConstants.PARENT_ID);
        String txt = "";
        if (parentId == null) {
            txt = "You want a Task without parent\nCreate Task...";
        } else {
            txt = "You want to create a Subtask for the task with ID " + parentId;
        }
        ((NewTaskVisualPanel1) getComponent()).getInfoLabel().setText(txt);
    }

    public void storeSettings(Object settings) {
    }
}
