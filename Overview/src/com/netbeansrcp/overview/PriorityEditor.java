/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import com.netbeansrcp.taskmodel.api.Task;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 *
 * @author gperon
 */
public class PriorityEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    @Override
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        return new PriorityInplaceEditor();
    }

    static class PriorityInplaceEditor implements InplaceEditor {

        private PropertyEditor editor;
        private PropertyModel model;
        private JSlider slider;

        public PriorityInplaceEditor() {
            slider = new JSlider(0, 2);
            slider.setSnapToTicks(true);
        }

        @Override
        public void connect(PropertyEditor pe, PropertyEnv env) {
            editor = pe;
            reset();
        }

        @Override
        public JComponent getComponent() {
            return slider;
        }

        @Override
        public void clear() {
            editor = null;
            model = null;
        }

        @Override
        public Object getValue() {
            switch (slider.getValue()) {
                case 0:
                    return Task.Priority.LOW;
                case 1:
                    return Task.Priority.MEDIUM;
                case 2:
                    return Task.Priority.HIGH;
            }
            return Task.Priority.MEDIUM;
        }

        @Override
        public void setValue(Object o) {
            Task.Priority prio = (Task.Priority) o;
            switch (prio) {
                case LOW:
                    slider.setValue(0);
                    break;
                case MEDIUM:
                    slider.setValue(1);
                    break;
                case HIGH:
                    slider.setValue(2);
                    break;
            }
        }

        @Override
        public boolean supportsTextEntry() {
            return true;
        }

        @Override
        public void reset() {
            Task.Priority val = (Task.Priority) editor.getValue();
            setValue(val);
        }

        @Override
        public void addActionListener(ActionListener al) {
        }

        @Override
        public void removeActionListener(ActionListener al) {
        }

        @Override
        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[0];
        }

        @Override
        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        @Override
        public PropertyModel getPropertyModel() {
            return model;
        }

        @Override
        public void setPropertyModel(PropertyModel pm) {
            model = pm;
        }

        @Override
        public boolean isKnownComponent(Component c) {
            return c == slider || slider.isAncestorOf(c);
        }
    }
}
