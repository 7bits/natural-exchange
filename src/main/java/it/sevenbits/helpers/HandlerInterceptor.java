package it.sevenbits.helpers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private final StringHelper stringHelper = new StringHelper();
    private final EncodeDecodeHelper encodeDecodeHelper = new EncodeDecodeHelper();
    private final VkAuthHelper vkAuthHelper = new VkAuthHelper();
    private final AuthHelper authHelper = new AuthHelper();
    private final UrlHelper urlHelper = new UrlHelper();
    private final FilePathHelper filePathHelper = new FilePathHelper();



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("stringActions", stringHelper);
        modelAndView.addObject("coder", encodeDecodeHelper);
        modelAndView.addObject("vkAuth", vkAuthHelper);
        modelAndView.addObject("auth", authHelper);
        modelAndView.addObject("uri", urlHelper);
        modelAndView.addObject("filePath", filePathHelper);
        super.postHandle(request, response, handler, modelAndView);
    }
}
