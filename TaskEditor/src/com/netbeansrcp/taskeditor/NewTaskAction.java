/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskeditor;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Edit",
id = "com.netbeansrcp.taskeditor.NewTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskeditor/list-add.png",
displayName = "#CTL_NewTaskAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 100),
    @ActionReference(path = "Toolbars/Edit", position = 100),
    @ActionReference(path = "Shortcuts", name="D-A")
})
@Messages("CTL_NewTaskAction=New Task")
public final class NewTaskAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        TaskManager taskMgr = Lookup.getDefault().lookup(TaskManager.class);
        if (taskMgr != null) {
            Task task = taskMgr.createTask().getLookup().lookup(Task.class);
            TaskEditorTopComponent win = TaskEditorTopComponent.findInstance(task);
            win.open();
            win.requestActive();
        }
    }
}
