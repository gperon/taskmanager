/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.tasklog;

import com.netbeansrcp.taskmodel.api.Task;
import com.netbeansrcp.tasksource.api.TaskSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.netbeansrcp.tasklog//TaskLog//EN",
autostore = false)
@TopComponent.Description(preferredID = "TaskLogTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "properties", openAtStartup = true)
@ActionID(category = "Window", id = "com.netbeansrcp.tasklog.TaskLogTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_TaskLogAction",
preferredID = "TaskLogTopComponent")
public final class TaskLogTopComponent extends TopComponent implements PropertyChangeListener, LookupListener {

    private Lookup.Result<Task> result;
    private DefaultListModel listModel = new DefaultListModel();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        jList1.repaint();
    }

    public TaskLogTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(TaskLogTopComponent.class, "CTL_TaskLogTopComponent"));
        setToolTipText(NbBundle.getMessage(TaskLogTopComponent.class, "HINT_TaskLogTopComponent"));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new JList(listModel);

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        result = Lookup.getDefault().lookup(TaskSource.class).getLookup().lookupResult(Task.class);
        result.addLookupListener(this);
        for (Task task : result.allInstances()) {
            listModel.addElement(task);
            task.addPropertyChangeListener(this);
        }
    }

    @Override
    public void componentClosed() {
        for (Enumeration e = listModel.elements(); e.hasMoreElements();) {
            ((Task) e.nextElement()).removePropertyChangeListener(this);
        }
        result = null;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<Task> rslt = (Lookup.Result<Task>) ev.getSource();
        for (Task task : rslt.allInstances()) {
            listModel.addElement(task);
            task.addPropertyChangeListener(this);
        }
    }
}
