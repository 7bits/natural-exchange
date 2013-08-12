package it.sevenbits.security;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/8/13
 * Time: 8:12 PM
 *
 */


import java.security.Principal;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login() {


        ModelAndView modelAndView = new ModelAndView("/login");

        return modelAndView;
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        return "login";

    }

}
