package it.sevenbits.controller;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.*;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
//import it.sevenbits.util.UtilsManager;
import it.sevenbits.util.UtilsManager;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.UserEditProfileValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import it.sevenbits.util.TimeManager;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//import static it.sevenbits.util.UtilsManager.*;

/**
 * The controller servicing the page of the registration form of the user.
 */
@Controller
@RequestMapping(value = "user")
public class UsersController {
    public static final int REGISTRATION_PERIOD = 3;

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    /**
     *
     */

    @Resource(name = "auth")
    private MyUserDetailsService myUserDetailsService;

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "advertisementDao")
    private AdvertisementDao advertisementDao;

    @Resource(name = "subscriberDao")
    private SubscriberDao subscriberDao;

    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private AdvertisementSearchingValidator advertSearchingValidator;

    @Autowired
    private UserEditProfileValidator userEditProfileValidator;

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
        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
        User user = new User();
        user.setEmail(userRegistrationFormParam.getEmail());
        String password = md5encoder.encodePassword(userRegistrationFormParam.getPassword(), "");
        user.setPassword(password);
        user.setFirstName(userRegistrationFormParam.getFirstName());
        user.setLastName(userRegistrationFormParam.getLastName());
        user.setVk_link(userRegistrationFormParam.getVkLink());
        user.setIsDeleted(false);
        user.setUpdateDate(TimeManager.getTime());
        user.setCreatedDate(TimeManager.getTime());
        user.setRole("ROLE_USER");
        if (userRegistrationFormParam.getIsReceiveNews()) {
            Subscriber subscriber = new Subscriber(userRegistrationFormParam.getEmail());
            if (!this.subscriberDao.isExists(subscriber)) {
                this.subscriberDao.create(subscriber);
            }
        }
        user.setActivationDate(TimeManager.addDate(REGISTRATION_PERIOD));
        String code = md5encoder.encodePassword(user.getPassword(), user.getEmail() );
        user.setActivationCode(code);
        this.userDao.create(user);
        mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
        return new ModelAndView("user/regUserLink");
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
        if (user == null) {
            return new ModelAndView("user/conf_failed");
        }
        if (user.getActivationDate() == 0) {
            return new ModelAndView("user/loginRes");
        }
        if (checkRegistrationLink(user, codeParam)) {
            this.userDao.updateActivationCode(user);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());
            UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(usrDet);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            return new ModelAndView("user/loginRes");
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


    @RequestMapping(value = "/userProfile.html", method = RequestMethod.GET)
    public ModelAndView seeProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = this.userDao.findUserByEmail(email);
        ModelAndView modelAndView = new ModelAndView("user/userProfile");
        modelAndView.addObject("username", user.getFirstName()+ " " + user.getLastName());
        if (user.getAvatar() == null)
            modelAndView.addObject("userAvatar", "emptyface.png");
        else
            modelAndView.addObject("userAvatar", user.getAvatar());
        List<Advertisement> advertisements = this.advertisementDao.findAllByEmail(user);
        for(int i = 0; i < advertisements.size(); i++) {
            if (!advertisements.get(i).getIs_visible()) {
                advertisements.remove(i);
            }
        }
        modelAndView.addObject("adverts", advertisements);

        List<SearchVariant> variants = this.searchVariantDao.findByEmail(email);
        ArrayList<Searching> searchings = new ArrayList<>();
        for (SearchVariant variantsToAdd:variants) {
            searchings.add(new Searching(variantsToAdd));
        }
        modelAndView.addObject("searchVarsList", searchings);
        return modelAndView;
    }

    @RequestMapping(value = "/delKeyWords.html", method = RequestMethod.GET)
    public String delKeyWords(@RequestParam(value = "currentKeyWords", required = true) final String keyWordsParam,
                              @RequestParam(value = "currentCategory", required = true) final String categoryParam
    )
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SearchVariant tmp = new SearchVariant(email, keyWordsParam, categoryParam);
        this.searchVariantDao.delete(tmp);
        return "redirect:/user/userProfile.html";
    }

    @RequestMapping(value = "/editSearchVariant.html", method = RequestMethod.GET)
    public ModelAndView editSearchVars(@RequestParam(value = "oldKeys", required = false) final String keyWordsParam,
                                       @RequestParam(value = "oldCategories", required = true) final String categoriesParam
    )
    {
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        String[] categs ;
        categs = categoriesParam.split(" ");
        if (null == categoriesParam) {
            advertisementSearchingForm.setAll();
        }
        else
        {
            advertisementSearchingForm.setCategories(categs);
        }
        if (keyWordsParam != null)
        {
            advertisementSearchingForm.setKeyWords(keyWordsParam);
        }
        ModelAndView modelAndView = new ModelAndView("user/editSearchVariant");
        modelAndView.addObject("advertisementSearchingForm", advertisementSearchingForm);
        modelAndView.addObject("oldCategories", categoriesParam);
        modelAndView.addObject("oldKeys", keyWordsParam);

        return modelAndView;
    }

    @RequestMapping(value = "/editSearchVariant.html", method = RequestMethod.POST)
    public ModelAndView editSearchVarsPost(@RequestParam(value = "oldKeys", required = false) final String oldKeyWordsParam,
                                     @RequestParam(value = "oldCategories", required = true) final String oldCategoriesParam,
                                     final AdvertisementSearchingForm advertSearchingFormParam,
                                     final BindingResult result
    ){
        this.advertSearchingValidator.validate(advertSearchingFormParam, result);
        if (result.hasErrors()) {
            advertSearchingFormParam.setKeyWords(oldKeyWordsParam);
            advertSearchingFormParam.setCategories(oldCategoriesParam.split(" "));
            ModelAndView modelAndView = new ModelAndView("user/editSearchVariant");
            modelAndView.addObject("advertisementSearchingForm", advertSearchingFormParam);
            modelAndView.addObject("oldCategories", oldCategoriesParam);
            modelAndView.addObject("oldKeys", oldKeyWordsParam);
            return modelAndView;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String allCategories = UtilsManager.stringArrayToString(advertSearchingFormParam.getCategories());
        this.searchVariantDao.update(new SearchVariant(email, oldKeyWordsParam, oldCategoriesParam),
                advertSearchingFormParam.getKeyWords(),allCategories);

        return  new ModelAndView("redirect:/user/userProfile.html");
    }

    @RequestMapping(value = "/editProfile.html", method = RequestMethod.GET)
    public ModelAndView editProfile(){
        logger.debug("edit Profile");
        ModelAndView modelAndView = new ModelAndView("user/editProfile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserEntity userEntity = this.userDao.findEntityByEmail(email);

        logger.debug("email = " + email + "   " + userEntity.toString()
        );
        UserRegistrationForm userRegForm = new UserRegistrationForm();
        userRegForm.setEmail(email);
        userRegForm.setFirstName(userEntity.getFirstName());
        userRegForm.setLastName(userEntity.getLastName());
        userRegForm.setPassword(userEntity.getPassword());
        userRegForm.setVkLink(userEntity.getVk_link());
        this.subscriberDao.isExists(new Subscriber(email));
        userRegForm.setIsReceiveNews(this.subscriberDao.isExists(new Subscriber(email)));
        modelAndView.addObject("userRegistrationForm", userRegForm);
        return modelAndView;
    }

    @RequestMapping(value = "/editProfile.html", method = RequestMethod.POST)
    public String editProfileProcess(final UserRegistrationForm userRegistrationFormParam,
                                     final BindingResult result
    ){
        logger.debug("edit Profile process");

        this.userEditProfileValidator.validate(userRegistrationFormParam, result);
        if (result.hasErrors()) {
            return "user/editProfile";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oldEmail = auth.getName();
        Subscriber subscriberOld = new Subscriber(oldEmail);
        Boolean oldIsReceive = this.subscriberDao.isExists(subscriberOld);
        Boolean newIsReceive = userRegistrationFormParam.getIsReceiveNews();
        if (userRegistrationFormParam.getEmail().equals(oldEmail)) {
            if (oldIsReceive && !newIsReceive) {
                this.subscriberDao.delete(subscriberOld);
            }
            if (!oldIsReceive && newIsReceive) {
                this.subscriberDao.create(new Subscriber(userRegistrationFormParam.getEmail()));
            }
        } else {
            if (oldIsReceive) {
                this.subscriberDao.delete(subscriberOld);
            }
            if (newIsReceive) {
                this.subscriberDao.create(new Subscriber(userRegistrationFormParam.getEmail()));
            }
        }
        User userNew = new User();
        userNew.setEmail(userRegistrationFormParam.getEmail());
        userNew.setPassword(userRegistrationFormParam.getPassword());
        userNew.setFirstName(userRegistrationFormParam.getFirstName());
        userNew.setLastName(userRegistrationFormParam.getLastName());
        userNew.setVk_link(userRegistrationFormParam.getVkLink());
        userNew.setUpdateDate(TimeManager.getTime());
        this.userDao.updateData(userNew);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userNew.getEmail(),
                userNew.getPassword());
        UserDetails usrDet = myUserDetailsService.loadUserByUsername(userNew.getEmail());
        token.setDetails(usrDet);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        logger.debug("new user = " + userNew.toString());
        return "redirect:/user/userProfile.html";
    }
}