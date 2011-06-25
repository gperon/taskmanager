/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.tasksource;

import com.netbeansrcp.tasksource.api.TaskSource;
import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;

/**
 *
 * @author gperon
 */
@ServiceProvider(service = TaskSource.class)
public class TaskSourceImpl implements TaskSource {

    @Override
    public Lookup getLookup() {
        Lookup l1 = WindowManager.getDefault().findTopComponent("TaskEditorTopComponent").getLookup();
        Lookup l2 = WindowManager.getDefault().findTopComponent("TaskDuplicatorTopComponent").getLookup();
        return new ProxyLookup(new Lookup[]{l1, l2});
    }
}
