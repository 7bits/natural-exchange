package com.simple.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.mvc.Controller;


/**
 * Base class for all controllers. Every controller contains mainView - the JSP that will be shown by default
 * and could be changed to another one during request handling. Needs to be set in config of the dispatcher servlet
 */
public abstract class BaseController implements Controller, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}