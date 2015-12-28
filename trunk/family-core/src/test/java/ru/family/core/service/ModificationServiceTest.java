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
package ru.family.core.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.family.core.AbstractTest;
import ru.family.core.metamodel.impl.MapBasedGenericData;
import ru.family.core.model.creation.ModelFactory;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.orm.LifecycleManager;

/**
 * @author DMRA0509
 * @version 1.0 3/28/12
 */
public class ModificationServiceTest extends AbstractTest
{
    private ModificationService modificationService;
    private LifecycleManager lifecycleManager;
    private ModelFactory modelFactory;

    @Before
    public void setUp()
    {
        modificationService = applicationContext.getBean("genericModificationService", ModificationService.class);
        lifecycleManager = applicationContext.getBean("lifecycleManager", LifecycleManager.class);
        modelFactory = applicationContext.getBean("modelFactory", ModelFactory.class);

        applicationContext.getBean("mongoTemplate", MongoTemplate.class).getDb().dropDatabase();
    }

    @Test
    public void testPublishWishCategoryModification()
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Test", "Some desc", null);

        lifecycleManager.storeWishCategory(wishCategory);

        Family family = modelFactory.createFamily("My Family", null);

        lifecycleManager.storeFamily(family);

        UserProfile userProfile = modelFactory.createUserProfile("dima", "dima");

        lifecycleManager.storeUser(userProfile);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("Dima 1", null, null, userProfile, family);
        FamilyMember familyMember2 = modelFactory.createFamilyMember("Dima 2", null, null, userProfile, family);


        modificationService.publishWishCategoryModification(familyMember1.getId(), wishCategory.getId(), new MapBasedGenericData()
        {
            {
                getValues().put("description", "Hello World");
            }
        });

        System.out.println(lifecycleManager.loadWishCategory(wishCategory.getId()).getDescription());
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