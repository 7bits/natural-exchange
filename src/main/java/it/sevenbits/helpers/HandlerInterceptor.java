package it.sevenbits.helpers;

import it.sevenbits.helpers.jadeHelpers.UrlService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private final AuthService authService = new AuthService();
    private UrlService urlService = new UrlService();

    private CallFunctionHelper callFunctionHelper = new CallFunctionHelper();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("auth", authService);
        modelAndView.addObject("uri", urlService);
        super.postHandle(request, response, handler, modelAndView);
    }
}
