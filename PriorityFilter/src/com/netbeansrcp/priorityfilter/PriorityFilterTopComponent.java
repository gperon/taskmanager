/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.priorityfilter;

import com.netbeansrcp.overview.OverviewTopComponent;
import com.netbeansrcp.taskmodel.api.Task;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.nodes.Node;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.netbeansrcp.priorityfilter//PriorityFilter//EN",
autostore = false)
@TopComponent.Description(preferredID = "PriorityFilterTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "com.netbeansrcp.priorityfilter.PriorityFilterTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_PriorityFilterAction",
preferredID = "PriorityFilterTopComponent")
public final class PriorityFilterTopComponent extends TopComponent {

    public PriorityFilterTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(PriorityFilterTopComponent.class, "CTL_PriorityFilterTopComponent"));
        setToolTipText(NbBundle.getMessage(PriorityFilterTopComponent.class, "HINT_PriorityFilterTopComponent"));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonLow = new javax.swing.JButton();
        jButtonMedium = new javax.swing.JButton();
        jButtonHigh = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jButtonLow, org.openide.util.NbBundle.getMessage(PriorityFilterTopComponent.class, "PriorityFilterTopComponent.jButtonLow.text")); // NOI18N
        jButtonLow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLowActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButtonMedium, org.openide.util.NbBundle.getMessage(PriorityFilterTopComponent.class, "PriorityFilterTopComponent.jButtonMedium.text")); // NOI18N
        jButtonMedium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMediumActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButtonHigh, org.openide.util.NbBundle.getMessage(PriorityFilterTopComponent.class, "PriorityFilterTopComponent.jButtonHigh.text")); // NOI18N
        jButtonHigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHighActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButtonReset, org.openide.util.NbBundle.getMessage(PriorityFilterTopComponent.class, "PriorityFilterTopComponent.jButtonReset.text")); // NOI18N
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButtonLow)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonMedium)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonHigh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonHigh, jButtonLow, jButtonMedium});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLow)
                    .addComponent(jButtonMedium)
                    .addComponent(jButtonHigh))
                .addGap(18, 18, 18)
                .addComponent(jButtonReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLowActionPerformed
        filter(Task.Priority.LOW);
    }//GEN-LAST:event_jButtonLowActionPerformed

    private void jButtonHighActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHighActionPerformed
        filter(Task.Priority.HIGH);
    }//GEN-LAST:event_jButtonHighActionPerformed

    private void jButtonMediumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMediumActionPerformed
        filter(Task.Priority.MEDIUM);
    }//GEN-LAST:event_jButtonMediumActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        filter(null);
    }//GEN-LAST:event_jButtonResetActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHigh;
    private javax.swing.JButton jButtonLow;
    private javax.swing.JButton jButtonMedium;
    private javax.swing.JButton jButtonReset;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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

    private void filter(Task.Priority prio) {
        Filter.filter(prio);
    }
}
