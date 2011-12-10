/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskactions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.cookies.SaveCookie;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Task",
id = "com.netbeansrcp.taskactions.SaveTaskAction")
@ActionRegistration(iconBase = "com/netbeansrcp/taskactions/media-record.png",
displayName = "#CTL_SaveTaskAction" )
@ActionReferences({})
@Messages("CTL_SaveTaskAction=Save")
public final class SaveAction implements ActionListener {

    private final SaveCookie context;

    public SaveAction(SaveCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            context.save();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }
}
