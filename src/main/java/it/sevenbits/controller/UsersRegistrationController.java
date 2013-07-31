package it.sevenbits.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "user")
public class UsersRegistrationController {

    final Logger logger = LoggerFactory
            .getLogger(AdvertisementController.class);

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registrationForm() {

        ModelAndView modelAndView = new ModelAndView("user/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registrationRequest.html", method = RequestMethod.POST)
    public ModelAndView registrationRequestForm(@RequestParam("userName") String userName) {

        ModelAndView modelAndView = new ModelAndView("user/registrationRequest");
        modelAndView.addObject("userName",userName);
        return modelAndView;
    }
}
