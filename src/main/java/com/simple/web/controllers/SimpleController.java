package com.simple.web.controllers;

import com.simple.web.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: seven
 * Date: 23.04.13
 * Time: 3:30
 * To change this template use File | Settings | File Templates.
 */
public class SimpleController extends BaseController {

    @Override
    public void afterPropertiesSet() throws Exception {

        super.afterPropertiesSet();
    }

    @Override
    public ModelAndView handleRequest(
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {

        ModelAndView modelAndView = new ModelAndView("simple");

        return modelAndView;
    }

}
