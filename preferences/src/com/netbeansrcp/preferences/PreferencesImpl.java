/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.preferences;

import com.netbeansrcp.preferences.api.Preferences;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import org.openide.filesystems.LocalFileSystem;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gperon
 */
@ServiceProvider(service = Preferences.class)
public class PreferencesImpl implements Preferences {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public File getPersistencePath() {
        String taskDir = NbPreferences.forModule(PreferencesPanel.class).get("DIRECTORY", new LocalFileSystem().getRootDirectory().getAbsolutePath());
        File file = new File(taskDir);
        return file;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void setPersistencePath(String path) {
        pcs.firePropertyChange(PATH_CHANGED, getPersistencePath(), path);
        NbPreferences.forModule(PreferencesPanel.class).put("DIRECTORY", path);
    }
}
