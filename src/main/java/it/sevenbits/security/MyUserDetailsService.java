package it.sevenbits.security;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service(value = "auth")
public class MyUserDetailsService implements UserDetailsService {

    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = this.userDao.findUserByEmail(email);
        if (user.getActivationDate() != 0L) {
            throw new UsernameNotFoundException(email + " not found or is not confirmed");
        }
        return user;
    }

}

