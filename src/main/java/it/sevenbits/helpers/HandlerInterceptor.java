package it.sevenbits.helpers;

import it.sevenbits.helpers.jadeHelpers.AuthService;
import it.sevenbits.helpers.jadeHelpers.UrlService;
import it.sevenbits.helpers.jadeHelpers.VkAuthService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private final VkAuthService vkAuthService = new VkAuthService();
    private final AuthService authService = new AuthService();
    private UrlService urlService = new UrlService();



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("vkAuth", vkAuthService);
        modelAndView.addObject("auth", authService);
        modelAndView.addObject("uri", urlService);
        super.postHandle(request, response, handler, modelAndView);
    }
}
