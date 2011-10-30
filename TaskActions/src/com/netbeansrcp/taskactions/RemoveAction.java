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
id = "com.netbeansrcp.taskactions.RemoveTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskactions/edit-clear.png",
displayName = "#CTL_RemoveTaskAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 500),
    @ActionReference(path = "Toolbars/Edit", position = 500),
    @ActionReference(path = "Shortcuts", name = "D-X")
})
@Messages("CTL_RemoveTaskAction=Delete Task")
public class RemoveAction extends AbstractAction implements LookupListener, Presenter.Toolbar, ContextAwareAction {

    private Lookup.Result<Task> result;
    private JButton toolbarBtn;

    public RemoveAction() {
        this(Utilities.actionsGlobalContext());
    }

    public RemoveAction(Lookup lookup) {
        super("Delete Task...", new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/taskactions/edit-clear.png")));
        result = lookup.lookupResult(Task.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (result != null && !result.allInstances().isEmpty()) {
            Task task = result.allInstances().iterator().next();
            TaskManager taskMgr = Lookup.getDefault().lookup(TaskManager.class);
            if (taskMgr != null && task != null) {
                taskMgr.removeTask(task.getId());
            }
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
            if (toolbarBtn != null) {
                toolbarBtn.setEnabled(true);
            }
        } else {
            if (toolbarBtn != null) {
                toolbarBtn.setEnabled(false);
            }
            setEnabled(false);
        }
    }

    @Override
    public Component getToolbarPresenter() {
        if (toolbarBtn == null) {
            toolbarBtn = new JButton(this);
        }
        return toolbarBtn;
    }

    @Override
    public Action createContextAwareInstance(Lookup lkp) {
        return new RemoveAction(lkp);
    }

    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
