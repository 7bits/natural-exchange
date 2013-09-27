package it.sevenbits.controller;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import it.sevenbits.util.TimeManager;
import javax.annotation.Resource;

/**
 * The controller servicing the page of the registration form of the user.
 */
@Controller
@RequestMapping(value = "user")
public class UsersRegistrationController {
    public static final int REGISTRATION_PERIOD = 3;

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

    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

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
    public ModelAndView registrationRequestForm(final UserRegistrationForm userRegistrationFormParam,
                                                final BindingResult result) {
        userRegistrationValidator.validate(userRegistrationFormParam, result);
        if (result.hasErrors()) {
            return new ModelAndView("user/registration");
        }
        User user = new User();
        user.setEmail(userRegistrationFormParam.getEmail());
        user.setPassword(userRegistrationFormParam.getPassword());
        user.setFirstName(userRegistrationFormParam.getFirstName());
        user.setLastName(userRegistrationFormParam.getLastName());
        user.setVk_link(userRegistrationFormParam.getVkLink());
        user.setIsDeleted(false);
        user.setUpdateDate(TimeManager.getTime());
        user.setCreatedDate(TimeManager.getTime());
        user.setRole("ROLE_USER");
        if (userRegistrationFormParam.getIsReceiveNews() != null) {
            Subscriber subscriber = new Subscriber(userRegistrationFormParam.getEmail());
            if (!this.subscriberDao.isExists(subscriber)) {
                this.subscriberDao.create(subscriber);
            }
        }
        user.setActivationDate(TimeManager.addDate(REGISTRATION_PERIOD));
        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
        String code = md5encoder.encodePassword(user.getPassword(), user.getEmail() );
        user.setActivationCode(code);
        this.userDao.create(user);
        mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
        ModelAndView modelAndView = new ModelAndView("user/regUserLink");
        return  modelAndView;
    }

    @RequestMapping(value = "/loginRes.html", method = RequestMethod.GET)
    public ModelAndView loginRes() {
        return new ModelAndView("user/loginRes");
    }

    @RequestMapping(value = "/regUserLink.html", method = RequestMethod.GET)
    public ModelAndView regUserLink() {
        return new ModelAndView("user/regUserLink");
    }

    boolean checkRegistrationLink(final User user, final  String code) {
        if (user == null) {
              return false;
        }
        if (TimeManager.getTime() >  user.getActivationDate()) {
            return false;
        }
        if (!code.equals(user.getActivationCode())) {
            logger.info("check not passed: code not equals");
            return  false;
        }
        return true;
    }
    @RequestMapping(value = "/magic.html", method = RequestMethod.GET)
    public ModelAndView magicPage(@RequestParam(value = "code", required = true) final String codeParam,
                                  @RequestParam(value = "mail", required = true) final String mailParam) {
        User user = this.userDao.findUserByEmail(mailParam);
        if ( user != null && user.getActivationDate() == 0) {
            return new ModelAndView("user/loginRes");
        }
        if (checkRegistrationLink(user, codeParam)) {
            this.userDao.updateActivationCode(user);
            return  new ModelAndView("user/loginRes");
        } else {
            return new ModelAndView("user/conf_failed");
        }
    }

    @RequestMapping(value = "/conf_failed.html", method = RequestMethod.GET)
    public ModelAndView confirmProfileFailed() {
        return new ModelAndView("user/conf_failed");
    }

    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public ModelAndView logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return new ModelAndView("advertisement/list");
    }

}