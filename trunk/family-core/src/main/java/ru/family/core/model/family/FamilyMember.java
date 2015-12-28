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
package ru.family.core.model.family;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.family.core.model.*;
import ru.family.core.model.wish.Wish;
import ru.family.core.orm.Entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/12/12
 */
@Document(collection = "familyMembers")
public class FamilyMember extends BaseModel implements Entity
{
    @DBRef
    private Family family;
    @DBRef
    private Set<Wish> wishes = new HashSet<Wish>();
    @DBRef
    @Indexed
    private UserProfile userProfile;

    public FamilyMember()
    {
    }

    public FamilyMember(Long id, String name, String description, Picture picture, UserProfile userProfile, Family family)
    {
        super(id, name, description, picture);

        this.userProfile = userProfile;

        this.family = family;
    }

    public Family getFamily()
    {
        return family;
    }

    public UserProfile getUserProfile()
    {
        return userProfile;
    }

    public void addWish(Wish wish)
    {
        wishes.add(wish);
    }

    public Wish getWish(Long wishId)
    {
        for (Wish wish : wishes)
            if (wish.getId().equals(wishId))
                return wish;

        return null;
    }

    public Collection<Wish> getWishes()
    {
        return java.util.Collections.unmodifiableCollection(wishes);
    }

    public void removeWish(Long wishId)
    {
        for (Iterator<Wish> it = wishes.iterator(); it.hasNext(); )
            if (it.next().getId().equals(wishId))
            {
                it.remove();
                return;
            }

        throw new ModelException("WishId: " + wishId + " was not found for FM: " + this.id);
    }

    public void clearWishes()
    {
        wishes.clear();
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