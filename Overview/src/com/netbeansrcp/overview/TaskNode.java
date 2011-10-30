/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskmodel.api.Task;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gperon
 */
public class TaskNode extends AbstractNode implements PropertyChangeListener {

    static List<? extends Action> registeredActions;

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

    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<Action>();
        actions.addAll(getRegisteredActions());
        actions.addAll(Arrays.asList(super.getActions(context)));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        return Utilities.actionsForPath("Tasks/Nodes/Task/PreferredAction").get(0);
    }

    protected static List<? extends Action> getRegisteredActions() {
        if (registeredActions == null) {
            registeredActions = Utilities.actionsForPath("Tasks/Nodes/Task/Actions");
        }
        return registeredActions;
    }
}
