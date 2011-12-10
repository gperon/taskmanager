/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskactions.SaveAction;
import com.netbeansrcp.taskmodel.api.Task;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.Action;
import org.openide.cookies.SaveCookie;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gperon
 */
public class TaskNode extends AbstractNode implements PropertyChangeListener, LookupListener {

    static List<? extends Action> registeredActions;
    private Lookup.Result result;
    private boolean saveable = false;

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
        setIconBaseWithExtension("com/netbeansrcp/overview/contact-new.png");
        task.addPropertyChangeListener(this);
//        result = getLookup().lookup(SaveCookie.class);
        result = getLookup().lookupResult(SaveCookie.class);
        result.addLookupListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Task.PROP_NAME.equals(evt.getPropertyName()) || Task.PROP_PRIO.equals(evt.getPropertyName())) {
            setDisplayName(evt.getNewValue() + "");
        }
    }

    @Override
    public String getHtmlDisplayName() {
        String html = "<font color=\"";
        Task task = getLookup().lookup(Task.class);
        switch (task.getPrio()) {
            case LOW:
                html += "#0000FF"; // blue
                break;
            case MEDIUM:
                html += "#000000"; // black
                break;
            case HIGH:
                html += "#FF0000"; // red
                break;
            default:
                throw new AssertionError();
        }
        html += "\">" + task.getName() + " <font/>";
        return html;
    }

    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<Action>();
        actions.addAll(getRegisteredActions());
        actions.addAll(Arrays.asList(super.getActions(context)));
//        actions.add(new SaveAction());
        actions.add(null);
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

    @Override
    protected Sheet createSheet() {
        final Task task = getLookup().lookup(Task.class);
        Sheet sheet = Sheet.createDefault();
        Sheet.Set setProps = sheet.createPropertiesSet();
        setProps.setDisplayName("identification");
        sheet.put(setProps);

        Property<String> idProp = new PropertySupport.ReadOnly<String>("id", String.class, "ID", "identification number") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return (task != null) ? task.getId() : "";
            }
        };
        Property<String> parentIdProp = new PropertySupport.ReadOnly<String>("parent-id", String.class, "parent-ID", "identification number of parent task") {

            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return (task != null) ? task.getParentId() : "";
            }
        };
        setProps.put(idProp);
        setProps.put(parentIdProp);

        Sheet.Set setExp = sheet.createExpertSet();
        setExp.setDisplayName("properties");
        sheet.put(setExp);
        try {
            Property nameProp = new PropertySupport.Reflection<String>(task, String.class, "name");
            nameProp.setName("name");
            nameProp.setDisplayName("name");
            nameProp.setShortDescription("name");

            Property dueProp = new PropertySupport.Reflection<Date>(task, Date.class, "due");
            dueProp.setName("due");
            dueProp.setDisplayName("due date");
            dueProp.setShortDescription("due date");

            PropertySupport.Reflection<Task.Priority> prioProp = new PropertySupport.Reflection<Task.Priority>(task, Task.Priority.class, "prio");
            prioProp.setName("prio");
            prioProp.setDisplayName("priority");
            prioProp.setShortDescription("priority");
            prioProp.setPropertyEditorClass(PriorityEditor.class);

            Property progrProp = new PropertySupport.ReadWrite("prog", Integer.class, "Progress", "Progress") {

                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return task != null ? task.getProgr() : 0;
                }

                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (task != null && val instanceof Integer) {
                        task.setProgr(((Integer) val).intValue());
                    }
                }
            };
            setExp.put(nameProp);
            setExp.put(dueProp);
            setExp.put(prioProp);
            setExp.put(progrProp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet.Set setDescr = new Sheet.Set();
        setDescr.setName("description");
        setDescr.setValue("tabName", " description ");
        sheet.put(setDescr);
        try {
            Property descrProp = new PropertySupport.Reflection<String>(task, String.class, "descr");
            descrProp.setName("descr");
            descrProp.setDisplayName("description");
            descrProp.setShortDescription("description");
            setDescr.put(descrProp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sheet;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        SaveCookie save = getLookup().lookup(SaveCookie.class);
        if (!saveable && save != null) {
            saveable = true;
            fireIconChange();
        }
        if (saveable && save == null) {
            saveable = false;
            fireIconChange();
        }
    }

    @Override
    public Image getIcon(int type) {
        Image std = super.getIcon(type);
        if (saveable) {
            Image badge = ImageUtilities.loadImage("com/netbeansrcp/taskactions/media-record.png");
            return ImageUtilities.mergeImages(std, badge, 5, 5);
        }
        return std;
    }
}
