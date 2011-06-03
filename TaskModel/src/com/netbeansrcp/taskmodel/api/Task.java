/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskmodel.api;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 *
 * @author gperon
 */
public interface Task extends Serializable {

    public java.lang.String getId();

    public java.lang.String getParentId();

    public java.lang.String getName();

    public void setName(java.lang.String name);

    public java.util.Date getDue();

    public void setDue(java.util.Date due);

    public enum Priority {

        LOW, MEDIUM, HIGH
    }

    public Priority getPrio();

    public void setPrio(Priority prio);

    public int getProgr();

    public void setProgr(int progr);

    public java.lang.String getDescr();

    public void setDescr(java.lang.String descr);

    public void addChild(Task subTask);

    public java.util.List<Task> getChildren();

    public boolean remove(Task subTask);

    public void addPropertyChangeListener(
            PropertyChangeListener listener);

    public void removePropertyChangeListener(
            PropertyChangeListener listener);
    public static final String PROP_NAME = "name";
    public static final String PROP_DUE = "due";
    public static final String PROP_PRIO = "prio";
    public static final String PROP_PROGR = "progr";
    public static final String PROP_DESCR = "descr";
    public static final String PROP_CHILDREN_ADD = "children_add";
    public static final String PROP_CHILDREN_REMOVE =
            "children_remove";
}
