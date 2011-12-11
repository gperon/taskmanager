/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions.wizards;

import com.netbeansrcp.taskmodel.api.Task;
import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;

public class NewTaskWizardPanel2 implements WizardDescriptor.Panel, DocumentListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NewTaskVisualPanel2 component;
    private WizardDescriptor wd;
    private boolean valid = true;
    private ChangeSupport cs = new ChangeSupport(this);
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.

    public Component getComponent() {
        if (component == null) {
            component = new NewTaskVisualPanel2();
            component.getDue().setText(DateFormat.getDateInstance().format(new Date()));
            component.getDue().getDocument().addDocumentListener(this);
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
//        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
         return new HelpCtx(NewTaskWizardPanel2.class);
    }

    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return valid;
        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }

    public final void addChangeListener(ChangeListener l) {
        cs.addChangeListener(l);
    }

    public final void removeChangeListener(ChangeListener l) {
        cs.removeChangeListener(l);
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
        wd = (WizardDescriptor) settings;
    }

    public void storeSettings(Object settings) {
        wd.putProperty(NewTaskConstants.NAME, component.getNameField().getText());
        try {
            wd.putProperty(NewTaskConstants.DUE, DateFormat.getDateInstance().parse(component.getDue().getText()));
        } catch (Exception e) {
        }
        Task.Priority prio = null;
        switch (component.getPriority().getValue()) {
            case 0:
                prio = Task.Priority.LOW;
                break;
            case 1:
                prio = Task.Priority.MEDIUM;
                break;
            case 2:
                prio = Task.Priority.HIGH;
                break;
            default:
                throw new AssertionError();
        }
        wd.putProperty(NewTaskConstants.PRIORITY, prio);
        wd.putProperty(NewTaskConstants.PROGRESS, component.getProgress().getValue());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changeDue();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changeDue();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changeDue();
    }

    private void changeDue() {
        boolean old = valid;
        try {
            DateFormat.getDateInstance().parse(component.getDue().getText());
            wd.putProperty("WizardPanel1_errorMessage", null);
            valid = true;
        } catch (Exception e) {
            wd.putProperty("WizardPanel1_errorMessage", "Due date not formatted correctly!");
            valid = false;
        }
        if (old != valid) {
            cs.fireChange();
        }
    }
}
