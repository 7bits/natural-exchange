package it.sevenbits.security;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/8/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
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
     /*
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal ) {

        String name = principal.getName();
        model.addAttribute("username", name);
        model.addAttribute("message", "Spring Security Custom Form example");
        return "hello";

    }
      */ /*
    @Resource(name = "subscriberDao")
     private SubscriberDao subscribertDao;
    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(ModelMap model, MailingNewsForm mailingNewsFormParam, BindingResult bindingResult ) {


        ModelAndView modelAndView = new ModelAndView("/login");
        if (mailingNewsFormParam.getEmail() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam,bindingResult);
            if (!bindingResult.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                modelAndView.addObject("mailingNewsForm",mailingNewsForm);
            }

        }
        return modelAndView;
    }
    */

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
