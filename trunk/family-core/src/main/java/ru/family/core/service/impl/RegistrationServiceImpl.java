package ru.family.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.family.core.model.BaseModelParams;
import ru.family.core.model.creation.ModelFactory;
import ru.family.core.model.family.UserProfile;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.security.encoding.UserPasswordEncoder;
import ru.family.core.security.model.SecurityProfile;
import ru.family.core.security.model.UserRole;
import ru.family.core.service.FamilyService;
import ru.family.core.service.RegistrationService;
import ru.family.core.service.ServiceException;

import java.util.Collections;

/**
 * Author: Dmitriy Rakov
 * Date: 22.03.12
 */
public class RegistrationServiceImpl implements RegistrationService
{
    @Autowired
    private ModelFactory modelFactory;
    @Autowired
    private LifecycleManager lifecycleManager;
    @Autowired
    private UserPasswordEncoder userPasswordEncoder;
    @Autowired
    private FamilyService familyService;

    @Override
    public UserProfile registerNewUser(Long familyId, String login, String password, BaseModelParams params) throws ServiceException
    {
        validateUserNotExists(login);

        UserProfile userProfile = createEncodedPasswordProfile(login, password);

        lifecycleManager.storeUser(userProfile);

        secureDefaultUser(userProfile);

        familyService.createNewMember(familyId, params, userProfile);

        return userProfile;
    }

    private void secureDefaultUser(UserProfile userProfile)
    {
        SecurityProfile securityProfile = new SecurityProfile(userProfile, Collections.singleton(UserRole.ROLE_USER));

        lifecycleManager.storeSecurityProfile(securityProfile);
    }

    private void validateUserNotExists(String login) throws ServiceException
    {
        UserProfile userProfile = lifecycleManager.findUserByLogin(login);

        if (userProfile != null)
            throw new ServiceException("Can't register user with login: " + login + ". User already exists");
    }

    private UserProfile createEncodedPasswordProfile(String login, String password)
    {
        UserProfile userProfile = modelFactory.createUserProfile(login, password);

        String encodedPassword = userPasswordEncoder.encodePasswordForUser(userProfile, password);

        userProfile.setPassword(encodedPassword);

        return userProfile;
    }
}
