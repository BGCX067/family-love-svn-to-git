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
package ru.family.core.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.family.core.AbstractTest;
import ru.family.core.TestUtil;
import ru.family.core.model.FilePicture;
import ru.family.core.model.creation.ModelFactory;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.model.wish.WishRank;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author DMRA0509
 * @version 1.0 3/15/12
 */
public class LifecycleManagerTest extends AbstractTest
{
    private ModelFactory modelFactory;
    private LifecycleManager lifecycleManager;

    public LifecycleManagerTest()
    {
        lifecycleManager = applicationContext.getBean("lifecycleManager", LifecycleManager.class);
        modelFactory = applicationContext.getBean("modelFactory", ModelFactory.class);
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
        applicationContext.getBean("mongoTemplate", MongoTemplate.class).getDb().dropDatabase();
    }

    @Test
    public void testStoreEmptyWishCategory() throws Exception
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Eating", "Category of eating products", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeWishCategory(wishCategory);

        WishCategory reloadedCategory = lifecycleManager.loadWishCategory(wishCategory.getId());

        TestUtil.assertWishCategories(wishCategory, reloadedCategory);
    }

    @Test
    public void testStoreWishWithCategory() throws Exception
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Eating", "Category of eating products", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("Milk", "Milk is very good", null, WishRank.HARDCORE, wishCategory);

        lifecycleManager.storeWish(wish1);

        wishCategory.setDescription("New Desc");

        lifecycleManager.storeWishCategory(wishCategory);

        Wish reloadedWish = lifecycleManager.loadWish(wish1.getId());

        TestUtil.assertWishes(wish1, reloadedWish);

        TestUtil.assertWishCategories(wish1.getCategory(), reloadedWish.getCategory());
    }

    @Test(expected = DataAccessException.class)
    public void testStoreWishWithoutCategory() throws Exception
    {
        Wish wish1 = modelFactory.createWish("Milk", "Milk is very good", null, WishRank.HARDCORE, null);

        lifecycleManager.storeWish(wish1);

        fail();
    }

    @Test
    public void testRemoveCategory() throws Exception
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Eating", "Category of eating products", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("Milk", "Milk is very good", null, WishRank.HARDCORE, wishCategory);
        Wish wish2 = modelFactory.createWish("Bread", "Bread is very good", null, WishRank.NORMAL, wishCategory);

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        lifecycleManager.removeWishCategory(wishCategory.getId());

        assertNull(lifecycleManager.loadWishCategory(wishCategory.getId()));

        assertNull(lifecycleManager.loadWish(wish1.getId()));
        assertNull(lifecycleManager.loadWish(wish2.getId()));
    }

    @Test
    public void testFindWishesByCategory()
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Eating", "Category of eating products", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("Milk", "Milk is very good", null, WishRank.HARDCORE, wishCategory);
        Wish wish2 = modelFactory.createWish("Bread", "Bread is very good", null, WishRank.NORMAL, wishCategory);

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        List<Wish> wishes = lifecycleManager.findWishByCategory(wishCategory.getId());

        assertEquals(2, wishes.size());

        assertTrue(wishes.contains(wish1));
        assertTrue(wishes.contains(wish2));
    }

    @Test
    public void testFindWishesByRank()
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Eating", "Category of eating products", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("Milk", "Milk is very good", null, WishRank.HARDCORE, wishCategory);
        Wish wish2 = modelFactory.createWish("Bread", "Bread is very good", null, WishRank.NORMAL, wishCategory);

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        List<Wish> hardcoreWishes = lifecycleManager.findWishesByRank(WishRank.HARDCORE);
        List<Wish> normalWishes = lifecycleManager.findWishesByRank(WishRank.NORMAL);

        assertEquals(1, hardcoreWishes.size());
        assertEquals(1, normalWishes.size());

        assertEquals(wish1, hardcoreWishes.iterator().next());
        assertEquals(wish2, normalWishes.iterator().next());
    }

    @Test
    public void testStoreEmptyFamily()
    {
        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeFamily(family);

        Family reloadedFamily = lifecycleManager.loadFamily(family.getId());

        assertNotNull(reloadedFamily);

        TestUtil.assertFamilies(family, reloadedFamily);
    }

    @Test
    public void testStoreFamilyWithMembers()
    {
        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeFamily(family);

        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");
        UserProfile userProfile2 = modelFactory.createUserProfile("usr2", "usr2");

        lifecycleManager.storeUser(userProfile1);
        lifecycleManager.storeUser(userProfile2);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, family);
        FamilyMember familyMember2 = modelFactory.createFamilyMember("User2", "Test desc", null, userProfile2, family);

        lifecycleManager.storeFamilyMember(familyMember1);
        lifecycleManager.storeFamilyMember(familyMember2);

        Family reloadedFamily = lifecycleManager.loadFamily(family.getId());

        TestUtil.assertFamilies(family, reloadedFamily);

        FamilyMember reloadedMember1 = reloadedFamily.getFamilyMember(familyMember1.getId());
        FamilyMember reloadedMember2 = reloadedFamily.getFamilyMember(familyMember2.getId());

        assertNotNull(reloadedMember1);
        assertNotNull(reloadedMember2);

        TestUtil.assertFamilyMembers(reloadedMember1, familyMember1);
        TestUtil.assertFamilyMembers(reloadedMember2, familyMember2);
    }

    @Test
    public void testRemoveFamily()
    {
        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeFamily(family);

        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");
        UserProfile userProfile2 = modelFactory.createUserProfile("usr2", "usr2");

        lifecycleManager.storeUser(userProfile1);
        lifecycleManager.storeUser(userProfile2);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, family);
        FamilyMember familyMember2 = modelFactory.createFamilyMember("User2", "Test desc", null, userProfile2, family);

        lifecycleManager.storeFamilyMember(familyMember1);
        lifecycleManager.storeFamilyMember(familyMember2);

        Family reloadedFamily = lifecycleManager.loadFamily(family.getId());

        lifecycleManager.removeFamily(reloadedFamily);

        assertNull(lifecycleManager.loadFamily(reloadedFamily.getId()));
        assertNull(lifecycleManager.loadFamilyMember(familyMember1.getId()));
        assertNull(lifecycleManager.loadFamilyMember(familyMember2.getId()));
    }

    @Test(expected = DataAccessException.class)
    public void testStoreFamilyMemberWithoutFamily()
    {
        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");

        lifecycleManager.storeUser(userProfile1);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, null);

        lifecycleManager.storeFamilyMember(familyMember1);

        fail();
    }

    @Test(expected = DataAccessException.class)
    public void testStoreFamilyMemberWithoutUserProfile()
    {
        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeFamily(family);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, null, family);

        lifecycleManager.storeFamilyMember(familyMember1);

        fail();
    }

    @Test
    public void testStoreFamilyMemberWithoutWishes()
    {
        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));

        lifecycleManager.storeFamily(family);

        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");

        lifecycleManager.storeUser(userProfile1);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, family);

        family.setDescription("Test Desc");

        lifecycleManager.storeFamilyMember(familyMember1);

        lifecycleManager.storeFamily(family);

        FamilyMember reloadedMember = lifecycleManager.loadFamilyMember(familyMember1.getId());

        TestUtil.assertFamilyMembers(familyMember1, reloadedMember);

        TestUtil.assertFamilies(family, reloadedMember.getFamily());
    }

    @Test
    public void testStoreFamilyMemberWithWishes()
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Test", "Test", null);
        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("My Wish 1", null, null, WishRank.HARDCORE, wishCategory);
        Wish wish2 = modelFactory.createWish("My Wish 2", null, null, WishRank.NORMAL, wishCategory);

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));
        lifecycleManager.storeFamily(family);

        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");
        lifecycleManager.storeUser(userProfile1);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, family);

        familyMember1.addWish(wish1);
        familyMember1.addWish(wish2);

        lifecycleManager.storeFamilyMember(familyMember1);

        wish1.setDescription("Test Desc 1");
        wish2.setDescription("Test Desc 2");

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        FamilyMember reloadedMember = lifecycleManager.loadFamilyMember(familyMember1.getId());

        TestUtil.assertFamilyMembers(familyMember1, reloadedMember);
    }

    @Test
    public void testRemoveWishFromFamilyMember()
    {
        WishCategory wishCategory = modelFactory.crateWishCategory("Test", "Test", null);
        lifecycleManager.storeWishCategory(wishCategory);

        Wish wish1 = modelFactory.createWish("My Wish 1", null, null, WishRank.HARDCORE, wishCategory);
        Wish wish2 = modelFactory.createWish("My Wish 2", null, null, WishRank.NORMAL, wishCategory);

        lifecycleManager.storeWish(wish1);
        lifecycleManager.storeWish(wish2);

        Family family = modelFactory.createFamily("My Family", "Very good family", new FilePicture("C:\\0.bmp"));
        lifecycleManager.storeFamily(family);

        UserProfile userProfile1 = modelFactory.createUserProfile("usr1", "usr1");
        lifecycleManager.storeUser(userProfile1);

        FamilyMember familyMember1 = modelFactory.createFamilyMember("User1", "Test desc", null, userProfile1, family);

        familyMember1.addWish(wish1);
        familyMember1.addWish(wish2);

        lifecycleManager.storeFamilyMember(familyMember1);

        familyMember1.removeWish(wish2.getId());

        lifecycleManager.storeFamilyMember(familyMember1);

        FamilyMember reloadedMember = lifecycleManager.loadFamilyMember(familyMember1.getId());

        TestUtil.assertFamilyMembers(familyMember1, reloadedMember);
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