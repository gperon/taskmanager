/*
 * @(#)TaskImpl.java   2011-07-09
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
package com.netbeansrcp.taskmodel;

import com.netbeansrcp.taskidgenerator.api.TaskIdGenerator;
import com.netbeansrcp.taskmodel.api.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openide.util.Lookup;

/**
 *
 * @author gperon
 */
public class TaskImpl implements Task, PropertyChangeListener {
    
    private String id = "";
    private String name = "";
    private String parentId = "";
    private Date due = new Date();
    private Priority prio = Priority.MEDIUM;
    private int progr = 0;
    private String descr = "";
    private List<Task> children = new ArrayList<Task>();
    private PropertyChangeSupport pcs;

    /**
     * Constructs ...
     *
     */
    public TaskImpl() {
        this("", "");
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param parentId
     */
    public TaskImpl(String name, String parentId) {
        TaskIdGenerator idGen =
                Lookup.getDefault().lookup(com.netbeansrcp.taskidgenerator.api.TaskIdGenerator.class);
        this.id = idGen.generateId();
        this.name = name;
        this.parentId = parentId;
        this.due = new Date();
        this.prio = Priority.MEDIUM;
        this.progr = 0;
        this.descr = "";
        this.children = new ArrayList<Task>();
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setName(String name) {
        String old = this.name;
        this.name = name;
        this.pcs.firePropertyChange(PROP_NAME, old, name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Date getDue() {
        return due;
    }

    /**
     * Method description
     *
     *
     * @param due
     */
    public void setDue(Date due) {
        Date old = this.due;
        this.due = due;
        this.pcs.firePropertyChange(PROP_DUE, old, due);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Priority getPrio() {
        return prio;
    }

    /**
     * Method description
     *
     *
     * @param prio
     */
    public void setPrio(Priority prio) {
        Priority old = this.prio;
        this.prio = prio;
        this.pcs.firePropertyChange(PROP_PRIO, old, prio);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getProgr() {
        return progr;
    }

    /**
     * Method description
     *
     *
     * @param progr
     */
    public void setProgr(int progr) {
        int old = this.progr;
        this.progr = progr;
        this.pcs.firePropertyChange(PROP_PROGR, old, progr);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Method description
     *
     *
     * @param descr
     */
    public void setDescr(String descr) {
        String old = this.descr;
        this.descr = descr;
        this.pcs.firePropertyChange(PROP_DESCR, old, descr);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<Task> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    /**
     * Method description
     *
     *
     * @param subTask
     */
    public void addChild(Task subTask) {
        this.children.add(subTask);
        this.pcs.firePropertyChange(PROP_CHILDREN_ADD, null, this.children);
    }

    /**
     * Method description
     *
     *
     * @param subTask
     *
     * @return
     */
    public boolean remove(Task subTask) {
        boolean res = this.children.remove(subTask);
        subTask.removePropertyChangeListener(this);
        this.pcs.firePropertyChange(PROP_CHILDREN_REMOVE, null, this.children);
        
        return res;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * Method description
     *
     *
     * @param obj
     *
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskImpl other = (TaskImpl) obj;
        
        return this.id.equals(other.getId());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ((this.id != null) ? this.id.hashCode() : 0);
        
        return hash;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String toString() {
//        return "Task{id=" + getId() + ", parentId=" + this.parentId + ", name=" + this.getName() + ", due="
//                + DateFormat.getInstance().format(this.due) + ", prio=" + this.prio + ", prog=" + this.progr
//                + ", descr=" + this.descr + "}";
        return this.getId() + " - " + this.parentId + " - " + this.getName() + " - "
                + DateFormat.getInstance().format(this.due) + " - " + this.prio + " - " + this.progr
                + " - " + this.descr;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        pcs.firePropertyChange(PROP_CHILDREN_MODIFICATION, evt.getOldValue(), evt.getNewValue());
    }
}
