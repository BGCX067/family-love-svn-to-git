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
package ru.family.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.family.core.model.family.UserProfile;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.security.model.SecurityProfile;
import ru.family.core.security.model.UserRole;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/21/12
 */
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private LifecycleManager lifecycleManager;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException
    {
        UserProfile userProfile = lifecycleManager.findUserByLogin(login);

        if (userProfile == null)
            throw new SecurityException("User is null for login: " + login);

        SecurityProfile securityProfile = lifecycleManager.findSecurityProfileByUser(userProfile);

        if (securityProfile == null)
            throw new SecurityException("No security credentials for user: " + userProfile.getId());

        return new User(login, userProfile.getPassword(), true, true, true, true, loadAuthoritiesForUser(securityProfile));
    }

    private Collection<GrantedAuthority> loadAuthoritiesForUser(SecurityProfile securityProfile)
    {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Set<UserRole> roles = securityProfile.getRoles();

        if (roles == null || roles.isEmpty())
            return Collections.singleton(createAuthority(UserRole.ROLE_NO_ROLES));

        for (UserRole role : roles)
        {
            GrantedAuthority authority = createAuthority(role);
            authorities.add(authority);
        }
        return authorities;
    }

    private static GrantedAuthority createAuthority(UserRole role)
    {
        return new GrantedAuthorityImpl(role.toString());
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