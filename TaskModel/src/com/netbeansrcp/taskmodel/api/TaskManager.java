/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskmodel.api;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author gperon
 */
public interface TaskManager {

    static final String PROP_TASKLIST_ADD = "TASK_LIST_ADD";
    static final String PROP_TASKLIST_REMOVE = "TASK_LIST_REMOVE";

    Task createTask();

    Task createTask(String name, String parentId);

    void removeTask(String id);

    List<Task> getTopLevelTasks();

    Task getTask(String id);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
