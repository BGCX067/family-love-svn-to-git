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
package ru.family.core.orm.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.family.impl.MongoSpecificFamily;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.model.wish.WishRank;
import ru.family.core.orm.DataAccessException;
import ru.family.core.orm.Entity;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.security.model.SecurityProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author DMRA0509
 * @version 1.0 3/19/12
 */
public class MongoLifecycleManager implements LifecycleManager
{
    @Autowired
    protected MongoTemplate dao;

    @Override
    public void store(Entity entity)
    {
        dao.save(entity);
    }

    public void removeWish(Wish wish)
    {
        dao.remove(wish);
    }

    public void removeWish(Long wishId)
    {
        Wish wish = loadEnsure(wishId, Wish.class);

        removeWish(wish);
    }

    public void storeWish(Wish wish)
    {
        if (wish.getCategory() == null)
            throw new DataAccessException("Cant store wish without category: " + wish.getId());

        validateObjectExists(wish.getCategory());

        dao.save(wish);
    }

    public Wish loadWish(Long wishId)
    {
        return dao.findById(wishId, Wish.class);
    }

    @Override
    public Wish loadWishEnsure(Long wishId)
    {
        return loadEnsure(wishId, Wish.class);
    }

    public void storeWishCategory(WishCategory wishCategory)
    {
        dao.save(wishCategory);
    }

    public WishCategory loadWishCategory(Long categoryId)
    {
        return dao.findById(categoryId, WishCategory.class);
    }

    public void removeWishCategory(Long categoryId)
    {
        WishCategory category = loadEnsure(categoryId, WishCategory.class);

        for (Wish wish : findWishByCategory(category.getId()))
            removeWish(wish);

        dao.remove(category);
    }

    public Collection<WishCategory> findAllCategories()
    {
        return dao.findAll(WishCategory.class);
    }

    public void storeFamily(Family family)
    {
        if (!(family instanceof MongoSpecificFamily))
            throw new DataAccessException("Cant process family class: " + family.getClass() + " by MongoSpecific LM");

        dao.save(family);
    }

    public Family loadFamily(Long id)
    {
        Family family = dao.findById(id, Family.class);

        if (family == null)
            return null;

        MongoSpecificFamily mongoSpecificFamily = (MongoSpecificFamily) family;

        List<FamilyMember> familyMembers = findFamilyMembersByFamily(family.getId());

        mongoSpecificFamily.setMembers(familyMembers);

        return mongoSpecificFamily;
    }

    @Override
    public Family loadFamilyEnsure(Long id)
    {
        Family family = loadFamily(id);
        if (family == null)
            throw new DataAccessException("Family was not found: " + id);
        return family;
    }

    public void removeFamily(Long id)
    {
        Family family = loadEnsure(id, Family.class);

        removeFamily(family);
    }

    public void removeFamily(Family family)
    {
        for (FamilyMember familyMember : family.getMembers())
            removeFamilyMember(familyMember);

        dao.remove(family);
    }

    public Collection<Family> findAllFamilies()
    {
        return dao.findAll(Family.class);
    }

    public void removeFamilyMember(FamilyMember familyMember)
    {
        dao.remove(dao.findById(familyMember.getUserProfile().getId(), SecurityProfile.class));

        dao.remove(familyMember.getUserProfile());

        dao.remove(familyMember);
    }

    public void removeFamilyMember(Long familyMemberId)
    {
        FamilyMember familyMember = loadEnsure(familyMemberId, FamilyMember.class);

        removeFamilyMember(familyMember);
    }

    public void storeFamilyMember(FamilyMember familyMember)
    {
        if (familyMember.getFamily() == null)
            throw new DataAccessException("Cant store member without family: " + familyMember.getId());

        if (familyMember.getUserProfile() == null)
            throw new DataAccessException("Cant store member without user profile: " + familyMember.getId());

        validateObjectExists(familyMember.getFamily());

        validateObjectExists(familyMember.getUserProfile());

        dao.save(familyMember);
    }

    public FamilyMember loadFamilyMember(Long id)
    {
        return dao.findById(id, FamilyMember.class);
    }

    @Override
    public FamilyMember loadFamilyMemberEnsure(Long id)
    {
        return loadEnsure(id, FamilyMember.class);
    }

    public void storeUser(UserProfile userProfile)
    {
        dao.save(userProfile);
    }

    @Override
    public UserProfile loadUserEnsure(Long userId)
    {
        return loadEnsure(userId, UserProfile.class);
    }

    public UserProfile findUserByLogin(String login)
    {
        Query query = new Query(Criteria.where("$login").is(login));

        return findOne(query, UserProfile.class);
    }

    public SecurityProfile findSecurityProfileByUser(UserProfile userProfile)
    {
        return dao.findById(userProfile.getId(), SecurityProfile.class);
    }

    @Override
    public void storeSecurityProfile(SecurityProfile securityProfile)
    {
        dao.save(securityProfile);
    }

    public List<Wish> findWishesByRank(WishRank wishRank)
    {
        Query query = new Query(Criteria.where("wishRank").is(wishRank.toString()));

        return findObjectsByQuery(query, Wish.class);

    }

    public List<Wish> findWishesByRankAndCategory(WishRank wishRank, Long categoryId)
    {
        Query query = new Query(Criteria.where("category.$id").is(categoryId).and("wishRank").is(wishRank.toString()));

        return findObjectsByQuery(query, Wish.class);
    }

    public List<Wish> findWishByCategory(Long categoryId)
    {
        Query query = new Query(Criteria.where("category.$id").is(categoryId));

        return findObjectsByQuery(query, Wish.class);
    }

    public List<FamilyMember> findFamilyMembersByFamily(Long familyId)
    {
        Query query = new Query(Criteria.where("family.$id").is(familyId));

        return findObjectsByQuery(query, FamilyMember.class);
    }

    private <V extends Entity> List<V> findObjectsByQuery(Query query, Class<V> clazz)
    {
        List<V> results = dao.find(query, clazz);

        return results != null ? results : Collections.<V>emptyList();
    }

    private <V extends Entity> V findOne(Query query, Class<V> clazz)
    {
        List<V> results = findObjectsByQuery(query, clazz);

        if (results.size() == 1)
            throw new DataAccessException("Incorrect collection size for query: " + query.toString());

        return results.iterator().next();
    }

    protected <V extends Entity> V loadEnsure(Long id, Class<V> clazz)
    {
        V object = dao.findById(id, clazz);

        if (object == null)
            throw new DataAccessException("Object wish id: " + id + " and class = " + clazz + " was not found");

        return object;
    }

    private void validateObjectExists(Entity entity)
    {
        validateObjectExists(entity.getId(), entity.getClass());
    }

    private <V extends Entity> void validateObjectExists(Long objectId, Class<V> clazz)
    {
        V object = dao.findById(objectId, clazz);

        if (object == null)
            throw new DataAccessException("Object: " + objectId + " for class: " + clazz + " does not exist");
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