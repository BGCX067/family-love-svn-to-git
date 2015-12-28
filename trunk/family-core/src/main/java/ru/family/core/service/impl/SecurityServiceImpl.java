package ru.family.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.family.core.model.family.UserProfile;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.security.model.SecurityProfile;
import ru.family.core.security.model.UserRole;
import ru.family.core.service.SecurityService;
import ru.family.core.service.ServiceException;

/**
 * Author: Dmitriy Rakov
 * Date: 22.03.12
 */
public class SecurityServiceImpl implements SecurityService
{
    @Autowired
    private LifecycleManager lifecycleManager;

    @Override
    public void grantUserRole(Long userId, UserRole role) throws ServiceException
    {
        UserProfile userProfile = lifecycleManager.loadUserEnsure(userId);

        SecurityProfile securityProfile = lifecycleManager.findSecurityProfileByUser(userProfile);

        if (securityProfile.hasRole(role))
            throw new ServiceException("User: " + userId + " already has role: " + role);

        securityProfile.addRole(role);

        lifecycleManager.storeSecurityProfile(securityProfile);
    }

    public void revokeUserRole(Long userId, UserRole role) throws ServiceException
    {
        UserProfile userProfile = lifecycleManager.loadUserEnsure(userId);

        SecurityProfile securityProfile = lifecycleManager.findSecurityProfileByUser(userProfile);
        
        if (!securityProfile.hasRole(role))
            throw new ServiceException("User: "+userId+" has not role: "+role);
        
        securityProfile.removeRole(role);
        
        lifecycleManager.storeSecurityProfile(securityProfile);
    }
}
