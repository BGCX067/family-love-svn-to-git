package ru.family.core.service;

import ru.family.core.model.BaseModelParams;
import ru.family.core.model.family.UserProfile;

/**
 * Author: Dmitriy Rakov
 * Date: 22.03.12
 */
public interface RegistrationService
{
    public UserProfile registerNewUser(Long familyId, String login, String password, BaseModelParams params) throws ServiceException;
}
