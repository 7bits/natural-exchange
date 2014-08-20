package it.sevenbits.helpers.jadeHelpers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthService {

    public boolean isAuthenticated() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails);
    }
}
