/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.priorityfilter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

@ActionID(category = "Tools",
id = "com.netbeansrcp.priorityfilter.FilterTasksAction")
@ActionRegistration(iconBase = "com/netbeansrcp/priorityfilter/edit-find.png",
displayName = "#CTL_FilterTasksAction")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 600),
    @ActionReference(path = "Toolbars/Edit", position = 600),
    @ActionReference(path = "Shortcuts", name = "D-F")
})
@Messages("CTL_FilterTasksAction=Filter Tasks")
public final class FilterTasksAction extends AbstractAction implements ActionListener, Presenter.Toolbar {

    private JButton toolbarBtn;

    public FilterTasksAction() {
        super("Filter Tasks...", new ImageIcon(ImageUtilities.loadImage("com/netbeansrcp/priorityfilter/edit-find.png")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FilterPanel filterPanel = new FilterPanel();
        DialogDescriptor d = new DialogDescriptor(filterPanel, "Priority Filter", true, DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION, null);
        Object res = DialogDisplayer.getDefault().notify(d);
        if (res != null && DialogDescriptor.OK_OPTION == res) {
            Filter.filter(filterPanel.getPriority());
        }
    }

    @Override
    public Component getToolbarPresenter() {
        if (toolbarBtn == null) {
            toolbarBtn = new JButton(this);
        }
        return toolbarBtn;
    }
}
