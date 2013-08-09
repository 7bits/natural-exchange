package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLogoutController {

    @RequestMapping(value = "q/user/logout.html", method = RequestMethod.GET)
    public ModelAndView helloWorld() {

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        ModelAndView logout = new ModelAndView("advertisement/list");
        return logout;
    }

}
