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
package ru.family.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.family.core.metamodel.GenericData;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.modifications.dao.ModificationDAO;
import ru.family.core.modifications.engine.ModificationEngine;
import ru.family.core.modifications.request.CommonModificationRequest;
import ru.family.core.modifications.request.ModificationContext;
import ru.family.core.modifications.request.ModificationRequest;
import ru.family.core.orm.Entity;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.service.ModificationService;
import ru.family.core.utils.IdGenerator;

/**
 * @author DMRA0509
 * @version 1.0 3/23/12
 */
public class GenericModificationService implements ModificationService
{
    //todo fix
    private static final int DEFAULT_VOTES_COUNT = 2;

    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private ModificationDAO modificationDAO;
    @Autowired
    private ModificationEngine modificationEngine;
    @Autowired
    private LifecycleManager lifecycleManager;

    public void publishWishModification(Long memberId, Long wishId, GenericData wishData)
    {
        Wish wish = lifecycleManager.loadWishEnsure(wishId);

        publishGenericModification(memberId, wish, wishData);
    }

    @Override
    public void publishWishCategoryModification(Long memberId, Long categoryId, GenericData wishCategoryData)
    {
        WishCategory wishCategory = lifecycleManager.loadWishCategory(categoryId);

        publishGenericModification(memberId, wishCategory, wishCategoryData);
    }

    @Override
    public void approveModification(Long modificationId, Long memberId)
    {
        ModificationRequest modificationRequest = modificationDAO.findModificationEnsure(modificationId);

        modificationRequest.vote(memberId);

        modificationDAO.saveModification(modificationRequest);

        modificationEngine.processModification(modificationRequest);
    }

    @Override
    public void rejectModification(Long modificationId)
    {
        ModificationRequest modificationRequest = modificationDAO.findModificationEnsure(modificationId);

        modificationDAO.removeModification(modificationRequest);
    }

    private void publishGenericModification(Long memberId, Entity entity, GenericData data)
    {
        Long modificationId = idGenerator.generateId();

        ModificationContext modificationContext = new ModificationContext(entity, data.getValues());

        ModificationRequest modificationRequest = new CommonModificationRequest(modificationId, memberId, modificationContext, DEFAULT_VOTES_COUNT);

        modificationDAO.saveModification(modificationRequest);

        modificationEngine.processModification(modificationRequest);
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