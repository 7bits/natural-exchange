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

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map registrationRequest(@RequestParam(value = "email", required = false) final String email,
        @RequestParam(value = "firstName", required = false) final String firstName,
        @RequestParam(value = "lastName", required = false) final String lastName,
        @RequestParam(value = "password", required = false) final String password
       ) {
        Map<String, String> response = new HashMap<>();
        if (!userDao.isExistUserWithEmail(email)) {
            UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
            userRegistrationForm.setEmail(email);
            userRegistrationForm.setFirstName(firstName);
            userRegistrationForm.setLastName(lastName);
            userRegistrationForm.setPassword(password);
            userRegistrationForm.setIsReceiveNews(false);
            userRegistrationForm.setVkLink("");
//            userRegistrationValidator.validate(userRegistrationForm, );
            response.put("success", "auth");
            Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
            User user = new User();
            user.setEmail(email);
            String userPassword = md5encoder.encodePassword(password, "");
            user.setPassword(userPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
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
            response.put("error", "failed");
        }
        return response;
    }

//    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
//    public ModelAndView registrationRequestForm(final UserRegistrationForm userRegistrationFormParam,
//                                                final BindingResult result) {
//        userRegistrationValidator.validate(userRegistrationFormParam, result);
//        if (result.hasErrors()) {
//            return new ModelAndView("user/registration");
//        }
//        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
//        User user = new User();
//        user.setEmail(userRegistrationFormParam.getEmail());
//        String password = md5encoder.encodePassword(userRegistrationFormParam.getPassword(), "");
//        user.setPassword(password);
//        user.setFirstName(userRegistrationFormParam.getFirstName());
//        user.setLastName(userRegistrationFormParam.getLastName());
//        user.setVk_link(userRegistrationFormParam.getVkLink());
//        user.setIsBanned(false);
//        user.setUpdateDate(TimeManager.getTime());
//        user.setCreatedDate(TimeManager.getTime());
//        user.setRole("ROLE_USER");
//        if (userRegistrationFormParam.getIsReceiveNews()) {
//            Subscriber subscriber = new Subscriber(userRegistrationFormParam.getEmail());
//            if (!this.subscriberDao.isExists(subscriber)) {
//                this.subscriberDao.create(subscriber);
//            }
//        }
//        user.setActivationDate(TimeManager.addDate(REGISTRATION_PERIOD));
//        String code = md5encoder.encodePassword(user.getPassword(), user.getEmail() );
//        user.setActivationCode(code);
//        this.userDao.create(user);
//        mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
//        return new ModelAndView("user/regUserLink");
//    }
}
