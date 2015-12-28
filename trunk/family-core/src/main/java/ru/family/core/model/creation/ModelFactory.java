package ru.family.core.model.creation;

import ru.family.core.model.BaseModelParams;
import ru.family.core.model.Picture;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;
import ru.family.core.model.wish.WishRank;

/**
 * Author: Dmitriy Rakov
 * Date: 20.03.12
 */
public interface ModelFactory
{
    public Family createFamily(String name, String description, Picture picture);

    public Family createFamily(String name, String description);

    public FamilyMember createFamilyMember(String name, String description, Picture picture, UserProfile profile, Family family);

    public FamilyMember createFamilyMember(BaseModelParams params, UserProfile profile, Family family);

    public WishCategory crateWishCategory(String name, String description, Picture picture);

    public Wish createWish(String name, String description, Picture picture, WishRank wishRank, WishCategory category);

    public UserProfile createUserProfile(String login, String password);
}
