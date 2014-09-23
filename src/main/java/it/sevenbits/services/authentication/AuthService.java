package it.sevenbits.services.authentication;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Autowired
    static UserDao userDao;

    public static User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UserEntity) {
            return userDao.findUserByEmail(auth.getName());
        } else return null;
    }

    public static UserDetails getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return (UserDetails) principal;
        } else return null;
    }
}
