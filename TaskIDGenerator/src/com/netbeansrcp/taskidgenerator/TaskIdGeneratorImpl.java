/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.taskidgenerator;

import com.netbeansrcp.taskidgenerator.api.IdValidator;
import com.netbeansrcp.taskidgenerator.api.TaskIdGenerator;
import java.util.Random;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gperon
 */
@ServiceProvider(service = TaskIdGenerator.class)
public class TaskIdGeneratorImpl implements TaskIdGenerator {

    private Random random = new Random();

    @Override
    public String generateId() {
//        return getId();        
        Lookup.Result<IdValidator> r = Lookup.getDefault().lookupResult(IdValidator.class);
        String id = null;
        boolean valid = false;
        while (!valid) {
            id = getId();
            valid = true;
            for (IdValidator validator : r.allInstances()) {
                valid &= validator.validate(id);
            }
        }
        return id;
    }

    private String getId() {
        String id = "000000" + random.nextInt();
        return id.substring(id.length() - 6);
    }
}
