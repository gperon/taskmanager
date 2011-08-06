/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskmodel.api.Task;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gperon
 */
public class TaskNode extends AbstractNode implements PropertyChangeListener {

    public TaskNode(Task task) {
        this(task, Lookups.singleton(task));
//        super(new TaskChildren(task), Lookups.singleton(task));
//        setName(task.getId());
//        setDisplayName(task.getName());
//        setIconBaseWithExtension("com/netbeansrcp/overview/Task.png");
//        addPropertyChangeListener(this);
    }

    public TaskNode(Task task, Lookup lookup) {
        super(Children.create(new TaskChildFactory(task), true), lookup);
        setName(task.getId());
        setDisplayName(task.getName());
        setIconBaseWithExtension("com/netbeansrcp/overview/Task.png");
        addPropertyChangeListener(this);
        
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Task.PROP_NAME.equals(evt.getPropertyName()) || Task.PROP_PRIO.equals(evt.getPropertyName())) {
            setDisplayName(evt.getNewValue() + "");
        }
    }

    @Override
    public String getHtmlDisplayName() {
        String html = "<font color='";
        Task task = getLookup().lookup(Task.class);
        switch (task.getPrio()) {
            case LOW:
                html += "0000FF"; // blue
                break;
            case MEDIUM:
                html += "000000"; // black
                break;
            case HIGH:
                html += "FF0000"; // red
                break;
            default:
                throw new AssertionError();
        }
        html += "'>" + task.getName() + "<font/>";
        return html;
    }
}
