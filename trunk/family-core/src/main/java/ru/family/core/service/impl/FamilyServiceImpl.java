package ru.family.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.family.core.model.BaseModelParams;
import ru.family.core.model.creation.ModelFactory;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.orm.LifecycleManager;
import ru.family.core.service.FamilyService;

/**
 * Author: Dmitriy Rakov
 * Date: 22.03.12
 */
public class FamilyServiceImpl implements FamilyService
{
    @Autowired
    private ModelFactory modelFactory;
    @Autowired
    private LifecycleManager lifecycleManager;

    public void createNewMember(Long familyId, BaseModelParams params, UserProfile userProfile)
    {
        Family family = lifecycleManager.loadFamilyEnsure(familyId);

        FamilyMember familyMember = modelFactory.createFamilyMember(params, userProfile, family);

        lifecycleManager.storeFamilyMember(familyMember);
    }

    @Override
    public FamilyMember updateFamilyMember(Long memberId, BaseModelParams params)
    {
        FamilyMember familyMember = lifecycleManager.loadFamilyMemberEnsure(memberId);

        familyMember.setParams(params);

        lifecycleManager.storeFamilyMember(familyMember);

        return familyMember;
    }

    @Override
    public FamilyMember getFamilyMember(Long memberId)
    {
        return lifecycleManager.loadFamilyMemberEnsure(memberId);
    }

    @Override
    public void removeFamilyMember(Long memberId)
    {
        lifecycleManager.removeFamilyMember(memberId);
    }

}
