/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author gperon
 */
public class TaskChildren extends Children.Keys<Task> implements PropertyChangeListener {

    private Task task;

    public TaskChildren(Task task) {
        this.task = task;
        task.addPropertyChangeListener(this);
    }

    @Override
    protected void addNotify() {
        super.addNotify();
        setKeys(task.getChildren());
    }

    @Override
    protected Node[] createNodes(Task key) {
        return new TaskNode[]{new TaskNode(key)};
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Task.PROP_CHILDREN_ADD.equals(evt.getPropertyName())
                || Task.PROP_CHILDREN_REMOVE.equals(evt.getPropertyName())) {
            setKeys(task.getChildren());
        }
//        if (TaskManager.PROP_TASKLIST_ADD.equals(evt.getPropertyName())
//                || TaskManager.PROP_TASKLIST_REMOVE.equals(evt.getPropertyName())) {
//            setKeys(task.getChildren());
//        }
    }
}
