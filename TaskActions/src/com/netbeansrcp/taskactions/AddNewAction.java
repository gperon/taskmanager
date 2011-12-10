/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.ContextAwareAction;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

@ActionID(category = "Task",
id = "com.netbeansrcp.taskactions.AddNewTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskactions/document-new.png",
displayName = "#CTL_AddNewTaskAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 300),
    @ActionReference(path = "Toolbars/Edit", position = 300),
    @ActionReference(path = "Shortcuts", name = "D-N")
})
@Messages("CTL_AddNewTaskAction=Add New Task")
public final class AddNewAction extends AbstractAction implements LookupListener, Presenter.Popup , ContextAwareAction{

    private Lookup.Result<Task> result;

    public AddNewAction() {
        this("Create new Task...");
    }

    private AddNewAction(String name) {
        super(name, new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/taskactions/document-new.png")));
    }

    private AddNewAction(Lookup lookup) {
        this("Create New Task...");
        result = lookup.lookupResult(Task.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    public void actionPerformed(ActionEvent e) {
        TaskManager taskMgr = Lookup.getDefault().lookup(TaskManager.class);
        Task task = null;
        if (result != null && result.allInstances().size() > 0) {
            task = result.allInstances().iterator().next();
            task = taskMgr.createTask("", task.getId());
        } else {
            task = taskMgr.createTask().getLookup().lookup(Task.class);
        }
        EditAction.openInTaskEditor(task);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (!result.allInstances().isEmpty()) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }

    public Component getToolbarPresenter() {
        return new JButton(this);
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new AddNewAction(actionContext);
    }
}
