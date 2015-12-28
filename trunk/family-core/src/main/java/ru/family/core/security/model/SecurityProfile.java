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
package ru.family.core.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.family.core.model.family.UserProfile;
import ru.family.core.orm.Entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/21/12
 */
@Document(collection = "security")
public class SecurityProfile implements Entity
{
    @Id
    private Long userId;
    private Set<UserRole> roles = new HashSet<UserRole>();

    public SecurityProfile()
    {
    }

    public SecurityProfile(UserProfile userProfile, Set<UserRole> roles)
    {
        this.userId = userProfile.getId();
        this.roles = roles;
    }

    public Long getId()
    {
        return userId;
    }

    public void addRole(UserRole role)
    {
        roles.add(role);
    }

    public void removeRole(UserRole role)
    {
        for (Iterator<UserRole> it = roles.iterator(); it.hasNext(); )
            if (it.next().equals(role))
                it.remove();
    }

    public Set<UserRole> getRoles()
    {
        return roles;
    }

    public boolean hasRole(UserRole role)
    {
        for (UserRole userRole : roles)
            if (role.equals(userRole))
                return true;
        return false;
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