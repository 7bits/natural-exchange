package it.sevenbits.controller.newdesign;


import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.TimeManager;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.UserEditProfileValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "new/user")
public class UserController {

    public static final int REGISTRATION_PERIOD = 3;

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     *
     */

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private SearchVariantDao searchVariantDao;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private UserEditProfileValidator userEditProfileValidator;

//    @RequestMapping(value = "/main.html", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.OK)
//    public @ResponseBody Map subscribe(@ModelAttribute("email") MailingNewsForm form,
//                                       final BindingResult bindingResult) {
//        Map map = new HashMap();
//        mailingNewsValidator.validate(form, bindingResult);
//        if (!bindingResult.hasErrors()) {
//            Subscriber newSubscriber = new Subscriber(form.getEmailNews());
//            if (this.subscriberDao.isExists(newSubscriber)) {
//                map.put("success", false);
//                Map errors = new HashMap();
//                errors.put("exist", "Вы уже подписаны.");
//                map.put("errors", errors);
//            } else {
//                this.subscriberDao.create(newSubscriber);
//                map.put("success", true);
//            }
//        } else {
//            map.put("success", false);
//            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            Map errors = new HashMap();
//            errors.put("wrong", errorMessage);
//            map.put("errors", errors);
//        }
//        return map;
//    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map registrationRequest(@ModelAttribute("email") UserRegistrationForm form,
        final BindingResult bindingResult
       ) {
        Map map = new HashMap();
        if (!userDao.isExistUserWithEmail(form.getEmail())) {
            form.setIsReceiveNews(false);
            form.setVkLink("");
            userRegistrationValidator.validate(form, bindingResult);
            if (!bindingResult.hasErrors()) {
                map.put("success", true);
                Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
                User user = new User();
                user.setEmail(form.getEmail());
                String userPassword = md5encoder.encodePassword(form.getPassword(), "");
                user.setPassword(userPassword);
                user.setFirstName(form.getFirstName());
                user.setLastName(form.getLastName());
                user.setVk_link("");
                user.setIsBanned(false);
                user.setUpdateDate(TimeManager.getTime());
                user.setCreatedDate(TimeManager.getTime());
                user.setRole("ROLE_USER");
////            if (userRegistrationFormParam.getIsReceiveNews()) {
////                Subscriber subscriber = new Subscriber(userRegistrationFormParam.getEmail());
////                if (!this.subscriberDao.isExists(subscriber)) {
////                    this.subscriberDao.create(subscriber);
////                }
////            }
                user.setActivationDate(TimeManager.addDate(REGISTRATION_PERIOD));
                String code = md5encoder.encodePassword(user.getPassword(), user.getEmail());
                user.setActivationCode(code);
                this.userDao.create(user);
                mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
            } else {
                map.put("success", false);
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                Map errors = new HashMap();
                errors.put("wrong", errorMessage);
                map.put("errors", errors);
            }
        } else {
            map.put("success", false);
            Map errors = new HashMap();
            errors.put("exist", "Вы уже зарегистрированы.");
            map.put("errors", errors);
        }
        return map;
    }
}
