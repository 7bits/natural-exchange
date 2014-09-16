package it.sevenbits.security;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

public class BanFilter implements Filter {
    @Autowired
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            String emailOfCurrentUser = ((UserEntity) auth.getPrincipal()).getEmail();
            User userFromDB = userDao.findUserByEmail(emailOfCurrentUser);
            if (userFromDB.getIsBanned()) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
