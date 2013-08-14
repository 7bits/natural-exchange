package it.sevenbits.controller;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.UserRegistrationForm;
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
import it.sevenbits.util.TimeManager;
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

    /**
     *
     */
    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     *
     */
    @Resource(name = "subscriberDao")
    private SubscriberDao subscriberDao;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registrationForm() {

        ModelAndView modelAndView = new ModelAndView("user/registration");
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        modelAndView.addObject("userRegistrationForm", userRegistrationForm);
        return modelAndView;
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public ModelAndView registrationRequestForm(
            final UserRegistrationForm userRegistrationFormParam, final BindingResult result
    ) {
        userRegistrationValidator.validate(userRegistrationFormParam, result);
        if (result.hasErrors()) {
            return new ModelAndView("user/registration");
        }
        User user = new User();
        user.setEmail(userRegistrationFormParam.getEmail());
        user.setPassword(userRegistrationFormParam.getPassword());
        user.setFirstName(userRegistrationFormParam.getFirstName());
        user.setLastName(userRegistrationFormParam.getLastName());
        user.setVklink(userRegistrationFormParam.getVkLink());
        user.setIsDeleted(false);
        user.setUpdateDate(TimeManager.getTime());
        user.setCreatedDate(TimeManager.getTime());
        user.setRole("ROLE_USER");
        if (userRegistrationFormParam.getIsReceiveNews().equals("true")) {
            Subscriber subscriber = new Subscriber(userRegistrationFormParam.getEmail());
            if (!this.subscriberDao.isExists(subscriber)) {
                this.subscriberDao.create(subscriber);
            }
        }
        this.userDao.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ModelAndView modelAndView = new ModelAndView("user/registrationRequest");
        modelAndView.addObject("advertisementSearchingForm", new AdvertisementSearchingForm());
        MailingNewsForm mailingNewsForm = new MailingNewsForm();
        modelAndView.addObject("mailingNewsForm", mailingNewsForm);
        return  modelAndView;
    }
}