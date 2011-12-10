/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskfilesupport;

import com.netbeansrcp.overview.TaskNode;
import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class TopLevelTaskDataObject extends MultiDataObject implements PropertyChangeListener {

    private InstanceContent ic;
    private Lookup lookup;
    private TaskManager taskMgr;

    public TopLevelTaskDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        taskMgr = Lookup.getDefault().lookup(TaskManager.class);
        if (taskMgr != null) {
            Task task = taskMgr.load(pf);
            task.addPropertyChangeListener(this);
            ic.add(task);
        }
    }

    @Override
    protected Node createNodeDelegate() {
        return new TaskNode(lookup.lookup(Task.class), lookup);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    private class TopLevelTaskSaveCapability implements SaveCookie {

        @Override
        public void save() throws IOException {
            FileObject fo = getPrimaryFile();
            Task task = getLookup().lookup(Task.class);
            taskMgr.save(task, fo);
            ic.remove(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SaveCookie saveCookie = getLookup().lookup(SaveCookie.class);
//        if (saveCookie != null) {
        ic.add(new TopLevelTaskSaveCapability());
//        }
    }
}
