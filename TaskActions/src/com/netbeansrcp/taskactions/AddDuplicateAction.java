/*9
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions;

import com.netbeansrcp.taskeditor.TaskEditorTopComponent;
import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;
import org.openide.windows.TopComponent;

/**
 *
 * @author gperon
 */
@ActionID(category = "Task",
id = "com.netbeansrcp.taskactions.AddDuplicateTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskactions/edit-copy.png",
displayName = "#CTL_AddDuplicateTaskAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 400),
    @ActionReference(path = "Toolbars/Edit", position = 400),
    @ActionReference(path = "Shortcuts", name="D-D")
})
@Messages("CTL_AddDuplicateTaskAction=Duplicate Task")
public class AddDuplicateAction extends AbstractAction implements LookupListener, Presenter.Toolbar, ContextAwareAction {

    private Lookup.Result<Task> result;
//    private JButton toolbarBnt;

    public AddDuplicateAction() {
        this(Utilities.actionsGlobalContext());
    }

    public AddDuplicateAction(Lookup lookup) {
        super("Copy Task...", new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/taskactions/edit-copy.png")));
        result = lookup.lookupResult(Task.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }
    
    public AddDuplicateAction(String label) {
        super(label, new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/taskactions/edit-copy.png")));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (result != null && !result.allInstances().isEmpty()) {
            Task original = result.allInstances().iterator().next();
            TaskManager taskMgr = Lookup.getDefault().lookup(TaskManager.class);
            Task newTask = null;
            String parentId = original.getParentId();
            if (parentId != null && !"".equals(parentId)) {
                newTask = taskMgr.createTask(original.getName(), original.getParentId());
            } else {
                newTask = taskMgr.createTask();
                newTask.setName(original.getName());
            }
            newTask.setDescr(original.getDescr());
            newTask.setDue(original.getDue());
            newTask.setPrio(original.getPrio());
            newTask.setProgr(original.getProgr());
            EditAction.openInTaskEditor(newTask);
        }
    }

    public static void openInTaskEditor(Task task) {
        TopComponent win = TaskEditorTopComponent.findInstance(task);
        win.open();
        win.requestActive();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (!result.allInstances().isEmpty()) {
            setEnabled(true);
//            if (toolbarBnt != null) {
//                toolbarBnt.setEnabled(true);
//            }
        } else {
//            if (toolbarBnt != null) {
//                toolbarBnt.setEnabled(false);
//            }
            setEnabled(false);
        }
    }

    @Override
    public Component getToolbarPresenter() {
//        if (toolbarBnt == null) {
//            toolbarBnt = new JButton(this);
//        }
//        return toolbarBnt;
            return  new JButton(this);
    }
    
    @Override
    public Action createContextAwareInstance(Lookup lkp) {
        return new AddDuplicateAction(lkp);
    }
    
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
