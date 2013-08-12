package it.sevenbits.controller;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;





import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;

/**
 * The controller servicing the page of the registration form of the user.
 */
@Controller
@RequestMapping(value = "user")
public class UsersRegistrationController {

    /**
     *
     */
     private final Logger logger = LoggerFactory.getLogger(UsersRegistrationController.class);

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registrationForm() {

        ModelAndView modelAndView = new ModelAndView("user/registration");
        MailingNewsForm mailingNewsForm = new MailingNewsForm();
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        modelAndView.addObject("mailingNewsForm", mailingNewsForm);
        modelAndView.addObject("userRegistrationForm", userRegistrationForm);
        return modelAndView;
    }

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     *
     */
    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    /**
     *
     */
    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @Resource(name = "subscriberDao")
    private SubscriberDao subscriberDao;

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public ModelAndView registrationRequestForm(
            final UserRegistrationForm userRegistrationFormParam, final BindingResult result,
            final MailingNewsForm mailingNewsFormParam, final BindingResult mailRes
    ) {
        if (mailingNewsFormParam.getEmail() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam, mailRes);
            ModelAndView mdv = new ModelAndView("user/registration");
            if (!mailRes.hasErrors()) {
                this.subscriberDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                mdv.addObject("mailingNewsForm", mailingNewsForm);

            }
            UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
            mdv.addObject("userRegistrationForm", userRegistrationForm);
            return mdv;
        } else {
            if (userRegistrationFormParam.getEmail() != null) {
                userRegistrationValidator.validate(userRegistrationFormParam, result);
                if (result.hasErrors()) {
                    return new ModelAndView("/registration");
                }
                User user = new User();
                user.setFirstName(userRegistrationFormParam.getFirstName());
                user.setLastName(userRegistrationFormParam.getLastName());
                user.setEmail(userRegistrationFormParam.getEmail());
                user.setPassword(userRegistrationFormParam.getPassword());
                user.setVklink(userRegistrationFormParam.getVkLink());
                user.setRole("ROLE_USER");
                this.userDao.create(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                        user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        return new ModelAndView("/registration");
    }
}