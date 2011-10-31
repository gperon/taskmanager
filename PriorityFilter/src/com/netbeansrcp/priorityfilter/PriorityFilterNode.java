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
public class PriorityFilterNode extends FilterNode {

    public PriorityFilterNode(Node original, Task.Priority prio) {
        super(original, new PriorityFilterChildren(original, prio));
    }

    public Node getOriginal() {
        return super.getOriginal();
    }
}
