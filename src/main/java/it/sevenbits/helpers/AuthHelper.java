package it.sevenbits.helpers;

import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.services.authentication.AuthService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthHelper {

    public boolean isAuthenticated() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails);
    }

    public static Long getCurrentUserID() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserEntity userEntity = (UserEntity) auth.getPrincipal();
            return userEntity.getId();
        }
        return (long) -1;
    }

    public static String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserEntity userEntity = (UserEntity) auth.getPrincipal();
            return userEntity.getEmail();
        }
        return "";
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }
        return new User();
    }

    public String getCurrentUserFirstAndLastNames() {
        User user = getCurrentUser();
        if (user.getFirstName().length() > 0 && user.getLastName().length() > 0) {
            return user.getFirstName() + " " + user.getLastName();
        } else {
            return "Личный кабинет";
        }
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
            return (user.getRole().equals("ROLE_MODERATOR") || user.getRole().equals("ROLE_ADMIN"));
        }
        return false;
    }
}
