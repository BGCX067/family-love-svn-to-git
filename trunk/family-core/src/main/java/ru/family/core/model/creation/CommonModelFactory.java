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
package ru.family.core.model.creation;

import ru.family.core.model.BaseModelParams;
import ru.family.core.model.Picture;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.model.wish.WishRank;
import ru.family.core.utils.IdGenerator;

/**
 * @author DMRA0509
 * @version 1.0 3/12/12
 */
public abstract class CommonModelFactory implements ModelFactory
{
    protected IdGenerator idGenerator;

    public void setIdGenerator(IdGenerator idGenerator)
    {
        this.idGenerator = idGenerator;
    }

    public Family createFamily(String name, String description)
    {
        return createFamily(name, description, null);
    }

    public FamilyMember createFamilyMember(String name, String description, Picture picture, UserProfile profile, Family family)
    {
        return new FamilyMember(id(), name, description, picture, profile, family);
    }

    @Override
    public FamilyMember createFamilyMember(BaseModelParams params, UserProfile profile, Family family)
    {
        return createFamilyMember(params.getName(), params.getDesc(), params.getPicture(), profile, family);
    }

    public WishCategory crateWishCategory(String name, String description, Picture picture)
    {
        return new WishCategory(id(), name, description, picture);
    }

    public Wish createWish(String name, String description, Picture picture, WishRank wishRank, WishCategory category)
    {
        return new Wish(id(), name, description, picture, wishRank, category);
    }

    public UserProfile createUserProfile(String login, String password)
    {
        return new UserProfile(id(), login, password);
    }

    protected Long id()
    {
        return idGenerator.generateId();
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