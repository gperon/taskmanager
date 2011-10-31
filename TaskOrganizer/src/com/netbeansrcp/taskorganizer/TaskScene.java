/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskorganizer;

import com.netbeansrcp.taskmodel.api.Task;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.util.List;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;

/**
 *
 * @author gperon
 */
public class TaskScene extends VMDGraphScene {

    private LayerWidget mainLayer;

    public TaskScene() {
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {

            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                Node node = NodeTransfer.node(transferable, NodeTransfer.DND_COPY);
                if (node != null && node.getLookup().lookup(Task.class) != null) {
                    return ConnectorState.ACCEPT;
                } else {
                    return ConnectorState.REJECT_AND_STOP;

                }
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                Node node = NodeTransfer.node(transferable, NodeTransfer.DND_COPY);
                Task task = node.getLookup().lookup(Task.class);
                createNode(task.getName());
                List<Task> children = task.getChildren();
                for (Task subTask : children) {
                    createPin(task.getName(), subTask.getName());
                }
            }
        }));
    }

    private void createNode(String nodeId) {
        ((VMDNodeWidget) addNode(nodeId)).setNodeName(nodeId);
    }

    private void createPin(String nodeId, String pinId) {
        ((VMDPinWidget) addPin(nodeId, pinId)).setPinName(pinId);
    }
}
