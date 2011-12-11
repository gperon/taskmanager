/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.priorityfilter;

import com.netbeansrcp.overview.OverviewTopComponent;
import com.netbeansrcp.taskmodel.api.Task;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.windows.WindowManager;

/**
 *
 * @author gperon
 */
public class Filter {

    public static void filter(Task.Priority prio) {
        OverviewTopComponent tc = (OverviewTopComponent) WindowManager.getDefault().findTopComponent("OverviewTopComponent");
        Node root = tc.getExplorerManager().getRootContext();
        if (root instanceof PriorityFilterNode) {
            root = ((PriorityFilterNode) root).getOriginal();
        }
        Node newRoot = (prio != null) ? new PriorityFilterNode(root, prio) : root;
        tc.getExplorerManager().setRootContext(newRoot);
        String msg = "";
        if (prio != null) {
            String prioTxt = "";
            switch (prio) {
                case LOW:
                    prioTxt = "low";
                    break;
                case MEDIUM:
                    prioTxt = "medium";
                    break;
                case HIGH:
                    prioTxt = "high";
                    break;
                default:
                    throw new AssertionError();
            }
            msg = "Only Tasks with priority\n" + prioTxt + " or higher are shown.";
        } else {
            msg = "All Tasks are shown indipendent of priority.";
        }
        NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(d);
    }
}
