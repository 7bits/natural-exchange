package it.sevenbits.services.authentication;

import it.sevenbits.repository.entity.User;
import it.sevenbits.repository.entity.hibernate.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    public static UserEntity getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return ((UserEntity) principal);
        } else return null;
    }

    public static UserDetails getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return (UserDetails) principal;
        } else return null;
    }

    public static Long getUserId() {
        UserEntity currentUser = getUser();
        if (currentUser != null) {
            return currentUser.getId();
        }
        return (long) 0;
    }

    public static void changeUserContext(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user, user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static String getUserName(User user) {
        if (!user.getFirstName().equals("")) {
            return user.getFirstName();
        }
        if (!user.getLastName().equals("")) {
            return user.getLastName();
        }
        return "Безымянный";
    }

    public static String findUserNameFromPrincipal() {
        User user = AuthService.getUser();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (user != null) {
            userName = user.getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
