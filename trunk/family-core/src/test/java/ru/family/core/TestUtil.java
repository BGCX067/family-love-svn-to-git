package ru.family.core;

import ru.family.core.model.IModel;
import ru.family.core.model.family.Family;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.family.UserProfile;
import ru.family.core.model.wish.Wish;
import ru.family.core.model.wish.WishCategory;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Dmitriy Rakov
 * Date: 20.03.12
 */
public class TestUtil
{
    public static void assertWishes(Wish expected, Wish actual)
    {
        assertBaseModels(expected, actual);
        assertEquals(expected.getCategory().getId(), actual.getCategory().getId());
    }

    public static void assertWishCategories(WishCategory expected, WishCategory actual)
    {
        assertBaseModels(expected, actual);
    }

    public static void assertFamilies(Family expected, Family actual)
    {
        assertBaseModels(expected, actual);
    }

    public static void assertFamilyMembers(FamilyMember expected, FamilyMember actual)
    {
        assertBaseModels(expected, actual);

        assertUserProfiles(expected.getUserProfile(), actual.getUserProfile());

        assertEquals(expected.getFamily().getId(), actual.getFamily().getId());

        assertFamilyMemberWishes(expected, actual);
    }

    private static void assertFamilyMemberWishes(FamilyMember expected, FamilyMember actual)
    {
        assertEquals(expected.getWishes().size(), actual.getWishes().size());

        for (Wish wish : expected.getWishes())
            assertEquals(wish, actual.getWish(wish.getId()));
    }

    private static void assertUserProfiles(UserProfile expected, UserProfile actual)
    {
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    private static void assertBaseModels(IModel expected, IModel actual)
    {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getDescription(), actual.getDescription());
    }
}
