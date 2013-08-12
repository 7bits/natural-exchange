package it.sevenbits.security;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/8/13
 * Time: 8:12 PM
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for login page
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/login");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(final ModelMap model) {
         return "login";
     }

}
