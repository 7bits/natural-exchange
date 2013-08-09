package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller

public class AuthFailedController {

    @RequestMapping(value = "/auth_failed.html", method = RequestMethod.GET)
    public ModelAndView auth() {
        ModelAndView modelAndView = new ModelAndView("/auth_failed");

        return modelAndView;
    }


}
