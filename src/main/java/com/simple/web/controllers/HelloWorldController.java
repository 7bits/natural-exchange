package com.simple.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value = "helloworld.html", method = RequestMethod.GET)
    public ModelAndView helloWorld() {

        LOG.info("Hello world controller");
        return new ModelAndView("helloWorld");
    }

}
