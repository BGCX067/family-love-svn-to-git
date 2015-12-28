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

import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.model.wish.WishRank;
import ru.family.core.security.model.SecurityProfile;

import java.util.Collection;
import java.util.List;

/**
 * @author DMRA0509
 * @version 1.0 3/14/12
 */
public interface LifecycleManager
{
    public void store (Entity entity);

    public void removeWish(Wish wish);

    public void removeWish(Long wishId);

    public void storeWishCategory(WishCategory wishCategory);

    public WishCategory loadWishCategory(Long categoryId);

    public void removeWishCategory(Long categoryId);

    public Collection<WishCategory> findAllCategories();

    public void storeFamily(Family family);

    public Family loadFamily(Long id);

    public Family loadFamilyEnsure(Long id);

    public void removeFamily(Long id);

    public void removeFamily(Family family);

    public Collection<Family> findAllFamilies();

    public void removeFamilyMember(FamilyMember familyMember);

    public void removeFamilyMember(Long familyMemberId);

    public void storeWish(Wish wish);

    public Wish loadWish(Long wishId);

    public Wish loadWishEnsure(Long wishId);

    public void storeFamilyMember(FamilyMember familyMember);

    public FamilyMember loadFamilyMember(Long id);

    public FamilyMember loadFamilyMemberEnsure(Long id);

    public void storeUser (UserProfile userProfile);

    public UserProfile loadUserEnsure (Long userId);
    
    public UserProfile findUserByLogin(String login);

    public SecurityProfile findSecurityProfileByUser(UserProfile userProfile);
    
    public void storeSecurityProfile (SecurityProfile securityProfile);

    public List<Wish> findWishesByRank(WishRank wishRank);

    public List<Wish> findWishesByRankAndCategory(WishRank wishRank, Long categoryId);

    public List<Wish> findWishByCategory(Long categoryId);

    public List<FamilyMember> findFamilyMembersByFamily(Long familyId);
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