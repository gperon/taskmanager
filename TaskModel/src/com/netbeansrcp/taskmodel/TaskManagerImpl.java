/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskmodel;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.taskmodel.api.TaskManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.LocalFileSystem;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gperon
 */
@ServiceProvider(service = TaskManager.class)
public class TaskManagerImpl implements TaskManager {

    private List<Task> topLevelTasks;
    private PropertyChangeSupport pcs;
    private FileObject root;
    private Map<Task, DataObject> doByTask;
    private boolean taskLoaded;

    public TaskManagerImpl() {
        topLevelTasks = new ArrayList<Task>();
        doByTask = new HashMap<Task, DataObject>();
        pcs = new PropertyChangeSupport(this);

        try {
            File file = new File("/tmp/tasks/");
            LocalFileSystem fs = new LocalFileSystem();
            fs.setRootDirectory(file);
            root = fs.getRoot();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }

//        deleteTasks();

//        /* dummy costruction */
//        Task t1 = createTask().getLookup().lookup(Task.class);
//        t1.setName("Todo 1");
//
//        Task t2 = createTask("Todo 1.1", t1.getId());
//        t2 = createTask("Todo 1.2", t1.getId());
//        t2 = createTask("Todo 1.3", t1.getId());
//        createTask("Todo 1.3.1", t2.getId());
//
//        t1 = createTask().getLookup().lookup(Task.class);
//        t1.setName("Todo 2");
//
//        t2 = createTask("Todo 2.1", t1.getId());
//        t2 = createTask("Todo 2.2", t1.getId());
//        t2 = createTask("Todo 2.3", t1.getId());
//        t1 = createTask("Todo 2.3.1", t2.getId());
//
//        t2 = createTask("Todo 2.3.1.1", t1.getId());
//        t2 = createTask("Todo 2.3.1.2", t1.getId());
////        try {
////            File file = new File("/tmp/tasks/");
////            LocalFileSystem fs = new LocalFileSystem();
////            fs.setRootDirectory(file);
////            root = fs.getRoot();
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        save(t1);
//        save(t2);
//        System.out.println("Count of know TopLevelTasks before deleting: " + topLevelTasks.size());
//        removeTask(t1.getId());
//        removeTask(t2.getId());
//        System.out.println("Count of know TopLevelTasks after deleting: " + topLevelTasks.size());
//        loadTasks();
        
//        System.out.println("Count of know TopLevelTasks after loading: " + topLevelTasks.size());

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public synchronized DataObject createTask() {
        DataObject dao = null;
        try {
            Task task = new TaskImpl();
            FileObject fo = save(task);
            dao = DataObject.find(fo);
            if (dao != null) {
                doByTask.put(task, dao);
            }
            topLevelTasks.add(task);
            pcs.firePropertyChange(PROP_TASKLIST_ADD, null, task);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
        return dao;
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
    public List<DataObject> getTopLevelTasks() {
        return Collections.unmodifiableList(new ArrayList<DataObject>(doByTask.values()));
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
            } else {
                DataObject dao = doByTask.get(task);
                if (dao != null) {
                    try {
                        dao.getPrimaryFile().delete();
                        doByTask.remove(task);
                    } catch (Exception e) {
                        Exceptions.printStackTrace(e);
                    }
                }
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

    @Override
    public FileObject save(Task task) {
        FileObject fo = null;
        try {
            fo = root.createData(task.getId() + ".tsk");
            save(task, fo);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
        return fo;
    }

    @Override
    public void save(Task task, FileObject fo) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(fo.getOutputStream()));
            out.writeObject(task);
            fo.setAttribute("saved", SimpleDateFormat.getInstance().format(new Date()));
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    @Override
    public Task load(FileObject fo) {
        Task task = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(fo.getInputStream()));
            task = (Task) in.readObject();
            System.out.println("Loaded: " + task + " [" + fo.getAttribute("saved") + "]");
            if (!topLevelTasks.contains(task)) {
                topLevelTasks.add(task);
                pcs.firePropertyChange(PROP_TASKLIST_ADD, null, task);
            }
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
        return task;
    }

    private void loadTasks() {
        if (taskLoaded) {
            return;
        }
        taskLoaded = true;
        FileObject[] entries = root.getChildren();
        for (FileObject fo : entries) {
            try {
                DataObject dao = DataObject.find(fo);
                if (dao != null) {
                    doByTask.put(dao.getLookup().lookup(Task.class), dao);
                }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private void deleteTasks() {
        for (FileObject fo : root.getChildren()) {
            if ("tsk".equalsIgnoreCase(fo.getExt())) {
                try {
                    fo.delete();
                } catch (Exception e) {
                    Exceptions.printStackTrace(e);
                }
            }
        }
    }
}
