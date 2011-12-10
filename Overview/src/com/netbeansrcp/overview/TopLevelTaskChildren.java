/*
 * @(#)TopLevelTaskChildren.java   2011-07-09
 * 
 * Copyright (c) 2011-2012 GioPerLab
 * All Rights Reserved. 
 *
 *
 */



/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gperon
 */
public class TopLevelTaskChildren extends Children.Keys<DataObject> implements PropertyChangeListener {
    private TaskManager taskMgr;

    /**
     * Constructs ...
     *
     */
    public TopLevelTaskChildren() {
        taskMgr = Lookup.getDefault().lookup(TaskManager.class);
        if (taskMgr != null) {
            taskMgr.addPropertyChangeListener(this);
        }
    }

    @Override
    protected void addNotify() {
        super.addNotify();
        if (taskMgr != null) {
            setKeys(taskMgr.getTopLevelTasks());
        }
    }

    @Override
    protected Node[] createNodes(DataObject key) {
        return new Node[] { key.getNodeDelegate() };
    }

    /**
     * Method description
     *
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (taskMgr != null) {
            if (TaskManager.PROP_TASKLIST_ADD.equals(evt.getPropertyName())
                    || TaskManager.PROP_TASKLIST_REMOVE.equals(evt.getPropertyName())) {
                setKeys(taskMgr.getTopLevelTasks());
            }
        }
    }
}
