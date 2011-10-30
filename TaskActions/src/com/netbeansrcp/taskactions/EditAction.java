/*9
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions;

import com.netbeansrcp.taskeditor.TaskEditorTopComponent;
import com.netbeansrcp.taskmodel.api.Task;
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
id = "com.netbeansrcp.taskactions.EditTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskactions/edit-select-all.png",
displayName = "#CTL_EditTaskAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 200),
    @ActionReference(path = "Toolbars/Edit", position = 200),
    @ActionReference(path = "Shortcuts", name="D-E")
})
@Messages("CTL_EditTaskAction=Edit Task")
public class EditAction extends AbstractAction implements LookupListener, Presenter.Toolbar, ContextAwareAction {

    private Lookup.Result<Task> result;
    private JButton toolbarBtn;

    public EditAction() {
        this(Utilities.actionsGlobalContext());
    }

    public EditAction(Lookup lookup) {
        super("Edit Task...", new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/taskactions/edit-select-all.png")));
        result = lookup.lookupResult(Task.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (result != null && !result.allInstances().isEmpty()) {
            Task task = result.allInstances().iterator().next();
            EditAction.openInTaskEditor(task);
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
        return new EditAction(lkp);
    }
    
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
