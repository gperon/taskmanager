/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.priorityfilter;

import com.netbeansrcp.taskmodel.api.Task;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

/**
 *
 * @author gperon
 */
public class PriorityFilterChildren extends FilterNode.Children {

    private Task.Priority prio;

    public PriorityFilterChildren(Node or, Task.Priority prio) {
        super(or);
        this.prio = prio;
    }

    @Override
    protected Node copyNode(Node node) {
        return new PriorityFilterNode(node, prio);
    }

    @Override
    protected Node[] createNodes(Node key) {
        Task task = key.getLookup().lookup(Task.class);
        if (task != null && prio.compareTo(task.getPrio()) >= 0) {
            return new Node[]{copyNode(key)};
        }
        return new Node[]{};
    }
}
