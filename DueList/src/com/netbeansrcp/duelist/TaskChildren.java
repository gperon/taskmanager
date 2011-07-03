/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.duelist;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gperon
 */
public class TaskChildren extends Children.Array implements PropertyChangeListener, ChangeListener {

    private long startTime;
    private long endTime;
    private JSpinner spinner;
    private TaskManager taskMgr;

    public TaskChildren(JSpinner spinner) {
        this.spinner = spinner;
        this.spinner.addChangeListener(this);
        taskMgr = Lookup.getDefault().lookup(TaskManager.class);
        if (taskMgr != null) {
            taskMgr.addPropertyChangeListener(this);
        }
    }

    @Override
    protected Collection<Node> initCollection() {
        Calendar cal = Calendar.getInstance();
        System.out.println("" + spinner.getValue());
        cal.set(Calendar.WEEK_OF_YEAR, (Integer) spinner.getValue());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        startTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        endTime = cal.getTimeInMillis();
        List<Task> dueTasks = new ArrayList<Task>();
        if (taskMgr != null) {
            List<Task> topLevelTasks = taskMgr.getTopLevelTasks();
            for (Task task : topLevelTasks) {
                findDueTasks(task, dueTasks);
            }
        }
        Collection<Node> dueNodes = new ArrayList<Node>(dueTasks.size());
        for (Task task : dueTasks) {
            dueNodes.add(new TaskNode(task));
            task.addPropertyChangeListener(this);
        }
        return dueNodes;
    }

    private void updateNodes() {
        remove(getNodes());
        add(initCollection().toArray(new Node[]{}));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof Task && (TaskManager.PROP_TASKLIST_ADD.equals(evt.getPropertyName()) || TaskManager.PROP_TASKLIST_REMOVE.equals(evt.getPropertyName()))) {
            updateNodes();
        }
        if (evt.getSource() instanceof TaskManager && (TaskManager.PROP_TASKLIST_ADD.equals(evt.getPropertyName()) || TaskManager.PROP_TASKLIST_REMOVE.equals(evt.getPropertyName()))) {
            updateNodes();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateNodes();
    }

    private void findDueTasks(Task task, List<Task> dueTasks) {
        long dueTime = task.getDue().getTime();
        if (dueTime >= startTime && dueTime <= endTime) {
            dueTasks.add(task);
        }
        for (Task subTask : task.getChildren()) {
            findDueTasks(subTask, dueTasks);
        }
    }
}
