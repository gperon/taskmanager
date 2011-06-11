/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskidgenerator;

import com.netbeansrcp.taskidgenerator.api.IdValidator;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gperon
 */
@ServiceProvider(service=IdValidator.class)
public class UniqueIdValidator implements IdValidator {

    @Override
    public boolean validate(String id) {
        System.out.println("UniqueIdValidator.validate(" + id + ")");
        return true;
    }
}
