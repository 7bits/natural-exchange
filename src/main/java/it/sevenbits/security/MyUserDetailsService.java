package it.sevenbits.security;

import it.sevenbits.dao.hibernate.UserDaoHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/6/13
 * Time: 1:26 PM
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDaoHibernate userDaoManager;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return userDaoManager.findUserByEmail(email);
    }

}
