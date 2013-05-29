package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class UsersRegistrationController {

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registrationForm() {

        ModelAndView modelAndView = new ModelAndView("user/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registrationRequest.html", method = RequestMethod.POST)
    public ModelAndView registrationRequestForm(@RequestParam("userName") String userName,@RequestParam("test1") String test1) {

        ModelAndView modelAndView = new ModelAndView("user/registrationRequest");
        modelAndView.addObject("userName",test1);
        return modelAndView;
    }
}
