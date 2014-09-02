package it.sevenbits.helpers.jadeHelpers;

import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthService {

    public boolean isAuthenticated() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails);
    }

    public Long getCurrentUserID() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserEntity userEntity = (UserEntity) auth.getPrincipal();
            return userEntity.getId();
        }
        return (long) 0;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }
        return new User();
    }

    public boolean isUserModerator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            return (user.getRole().equals("ROLE_MODERATOR"));
        }
        return false;
    }

    public boolean isUserModeratorOrAdministrator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            return (user.getRole().equals("ROLE_MODERATOR") || user.getRole().equals("ROLE_ADMINISTRATOR"));
        }
        return false;
    }
}
