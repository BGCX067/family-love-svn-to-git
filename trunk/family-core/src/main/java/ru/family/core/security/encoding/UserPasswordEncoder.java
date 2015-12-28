package ru.family.core.security.encoding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.family.core.model.family.UserProfile;

/**
 * Author: Dmitriy Rakov
 * Date: ${DATE}
 */
public class UserPasswordEncoder
{
    private static class UserDetailsEncodingAdapter extends User implements UserDetails
    {
        UserDetailsEncodingAdapter(UserProfile userProfile)
        {
            super(userProfile.getLogin(), userProfile.getPassword(), true, true, true, true, java.util.Collections.<GrantedAuthority>emptyList());
        }

        @Override
        public String getPassword()
        {
            throw new UnsupportedOperationException("Password cant be salt for encoding");
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;

    public String encodePasswordForUser(UserProfile userProfile, String password)
    {
        UserDetailsEncodingAdapter userDetails = new UserDetailsEncodingAdapter(userProfile);

        Object salt = saltSource.getSalt(userDetails);

        return passwordEncoder.encodePassword(password, salt);
    }
}
