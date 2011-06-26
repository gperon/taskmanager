/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskmodel;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gperon
 */
@ServiceProvider(service=TaskManager.class)
public class TaskManagerImpl implements TaskManager {

    private List<Task> topLevelTasks;
    private PropertyChangeSupport pcs;

    public TaskManagerImpl() {
        topLevelTasks = new ArrayList<Task>();
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public synchronized Task createTask() {
        Task task = new TaskImpl();
        topLevelTasks.add(task);
        pcs.firePropertyChange(PROP_TASKLIST_ADD, null, task);
        return task;
    }

    @Override
    public synchronized Task createTask(String name, String parentId) {
        Task task = new TaskImpl(name, parentId);
        Task parent = getTask(parentId);
        if (parent != null) {
            parent.addChild(task);
        }
        pcs.firePropertyChange(PROP_TASKLIST_ADD, parent, task);
        return task;

    }

    @Override
    public Task getTask(String id) {
        for (Task task : topLevelTasks) {
            Task found = findTask(task, id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public List<Task> getTopLevelTasks() {
        return Collections.unmodifiableList(topLevelTasks);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public synchronized void removeTask(String id) {
        Task task = getTask(id);
        if (task != null) {
            Task parent = getTask(task.getParentId());
            if (parent != null) {
                parent.remove(task);
            }
            topLevelTasks.remove(task);
            pcs.firePropertyChange(PROP_TASKLIST_REMOVE, parent, task);

        }
    }

    private Task findTask(Task task, String id) {
        if (id.equals(task.getId())) {
            return task;
        }
        for (Task child : task.getChildren()) {
            Task found = findTask(child, id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
