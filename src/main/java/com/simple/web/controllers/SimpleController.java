package com.simple.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "simple")
public class SimpleController {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleController.class);

    @RequestMapping(value = "simple.html", method = RequestMethod.GET)
    public ModelAndView helloWorld() {

        LOG.info("Simple controller");
        return new ModelAndView("simple");
    }

}
