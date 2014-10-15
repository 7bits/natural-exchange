package it.sevenbits.web.security;

import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.repository.entity.User;
import it.sevenbits.repository.entity.hibernate.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;

public class BanFilter implements Filter {

    @Autowired
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
        ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain
    ) throws IOException, ServletException {
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
