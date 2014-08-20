package it.sevenbits.helpers;

import it.sevenbits.helpers.jadeHelpers.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private UrlService urlService = new UrlService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String relativePath = request.getServletPath();
        String fullPath = this.urlService.uri(relativePath);
        String prefix = urlService.getAfterDomenPath();
        String currentPage = prefix + relativePath;

        modelAndView.addObject("getCurrentFullURI", fullPath);
        modelAndView.addObject("getCurrentRelativeURI", currentPage);
        modelAndView.addObject("getPrefix", prefix);
        super.postHandle(request, response, handler, modelAndView);
    }
}
