/**
 * Controller for authentication is failed page
 */
package it.sevenbits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

 /**
 * Make auth_failed page
 */
@Controller
public class AuthFailedController {

    @RequestMapping(value = "/auth_failed.html", method = RequestMethod.GET)
    public ModelAndView auth() {
        return new ModelAndView("/auth_failed");
    }


}
