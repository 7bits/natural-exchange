package it.sevenbits.services.authentication;

import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
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
}
