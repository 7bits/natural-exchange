package it.sevenbits.security;

import it.sevenbits.dao.hibernate.UserDaoHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/6/13
 * Time: 1:26 PM
 *
 */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDaoHibernate userDaoManager;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userDaoManager.findUserByEmail(email);
    }

}
