/*
 This software is the confidential information and copyrighted work of
 NetCracker Technology Corp. ("NetCracker") and/or its suppliers and
 is only distributed under the terms of a separate license agreement
 with NetCracker.
 Use of the software is governed by the terms of the license agreement.
 Any use of this software not in accordance with the license agreement
 is expressly prohibited by law, and may result in severe civil
 and criminal penalties. 
 
 Copyright (c) 1995-2010 NetCracker Technology Corp.
 
 All Rights Reserved.
 
*/
package ru.family.core.modifications.engine;

import org.springframework.beans.factory.annotation.Autowired;
import ru.family.core.metamodel.Generic;
import ru.family.core.metamodel.MetamodelManager;
import ru.family.core.modifications.dao.ModificationDAO;
import ru.family.core.modifications.request.ModificationContext;
import ru.family.core.modifications.request.ModificationRequest;
import ru.family.core.orm.Entity;
import ru.family.core.orm.LifecycleManager;

/**
 * @author DMRA0509
 * @version 1.0 4/4/12
 */
public abstract class CommonModificationEngine implements ModificationEngine
{
    @Autowired
    protected ModificationDAO modificationDAO;
    @Autowired
    protected LifecycleManager lifecycleManager;
    @Autowired
    private MetamodelManager<Entity> metamodelManager;

    //todo add modification lock
    protected void processModificationSynchronous(ModificationRequest modificationRequest)
    {
        int requiredVotesCount = modificationRequest.getRequiredVotesCount();
        int currentVotesCount = modificationRequest.getVotes().size();

        if (currentVotesCount >= requiredVotesCount)
            processRequest(modificationRequest);
    }

    private void processRequest(ModificationRequest modificationRequest)
    {
        executeRequestActionSafe(modificationRequest);

        modificationDAO.removeModification(modificationRequest);
    }

    private void executeRequestActionSafe(ModificationRequest modificationRequest)
    {
        try
        {
            executeRequestAction(modificationRequest);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void executeRequestAction(ModificationRequest modificationRequest) throws Exception
    {
        ModificationContext modificationContext = modificationRequest.getModificationContext();

        if (modificationContext != null)
        {
            execute(modificationContext);

            lifecycleManager.store(modificationContext.getModifiable());
        }
    }

    private void execute(ModificationContext modificationContext) throws Exception
    {
        Entity modifiable = modificationContext.getModifiable();

        if (modifiable == null)
            throw new RuntimeException("Cant execute action, modifiable is null");

        Generic<Entity> genericEntity = metamodelManager.create(modifiable);

        genericEntity.setValues(modificationContext.getValues());
    }
}
/*
 WITHOUT LIMITING THE FOREGOING, COPYING, REPRODUCTION, REDISTRIBUTION,
 REVERSE ENGINEERING, DISASSEMBLY, DECOMPILATION OR MODIFICATION
 OF THE SOFTWARE IS EXPRESSLY PROHIBITED, UNLESS SUCH COPYING,
 REPRODUCTION, REDISTRIBUTION, REVERSE ENGINEERING, DISASSEMBLY,
 DECOMPILATION OR MODIFICATION IS EXPRESSLY PERMITTED BY THE LICENSE
 AGREEMENT WITH NETCRACKER. 
 
 THIS SOFTWARE IS WARRANTED, IF AT ALL, ONLY AS EXPRESSLY PROVIDED IN
 THE TERMS OF THE LICENSE AGREEMENT, EXCEPT AS WARRANTED IN THE
 LICENSE AGREEMENT, NETCRACKER HEREBY DISCLAIMS ALL WARRANTIES AND
 CONDITIONS WITH REGARD TO THE SOFTWARE, WHETHER EXPRESS, IMPLIED
 OR STATUTORY, INCLUDING WITHOUT LIMITATION ALL WARRANTIES AND
 CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 TITLE AND NON-INFRINGEMENT.
 
 Copyright (c) 1995-2010 NetCracker Technology Corp.
 
 All Rights Reserved.
*/