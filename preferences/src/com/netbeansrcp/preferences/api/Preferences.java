/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.preferences.api;

import java.beans.PropertyChangeListener;
import java.io.File;

/**
 *
 * @author gperon
 */
public interface Preferences {

    String PATH_CHANGED = "path_changed";

    void setPersistencePath(String path);

    File getPersistencePath();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
