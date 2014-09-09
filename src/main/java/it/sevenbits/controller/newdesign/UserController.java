package it.sevenbits.controller.newdesign;


import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.helpers.jadeHelpers.EncodeDecodeService;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.TimeManager;
import it.sevenbits.util.form.EditingUserInfoForm;
import it.sevenbits.util.form.UserEntryForm;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.EditingUserInfoFormValidator;
import it.sevenbits.util.form.validator.UserEntryValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
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
    private EditingUserInfoFormValidator editingUserInfoFormValidator;

    @Autowired
    private UserEntryValidator userEntryValidator;

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    Map registrationRequest(@ModelAttribute("email") UserRegistrationForm form,
                            final BindingResult bindingResult
    ) {
        Map map = new HashMap();
        userRegistrationValidator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            if (!userDao.isExistUserWithEmail(form.getEmail())) {
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
                Map errors = new HashMap();
                errors.put("exist", "Вы уже зарегистрированы.");
                map.put("errors", errors);
            }
        } else {
            map.put("success", false);
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            Map errors = new HashMap();
            errors.put("wrong", errorMessage);
            map.put("errors", errors);
        }
        return map;
    }

    @RequestMapping(value = "/entry.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    Map entryRequest(@ModelAttribute("email") UserEntryForm form,
                     final BindingResult bindingResult
    ) {
        Map map = new HashMap();
        userEntryValidator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            if (!userDao.isExistUserWithEmail(form.getEmail())) {
                map.put("success", false);
                Map errors = new HashMap();
                errors.put("notExist", "Вы еще не зарегистрированы.");
                map.put("errors", errors);
            } else {
                Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
                User user = userDao.findUserByEmail(form.getEmail());
                if (user.getPassword().equals(md5PasswordEncoder.encodePassword(form.getPassword(), ""))) {
                    if (user.getActivationDate() != 0L) {
                        map.put("success", false);
                        Map errors = new HashMap();
                        errors.put("notRegistrationComplete", "Вы не активировали свой аккаунт.");
                        map.put("errors", errors);
                    } else {
                        map.put("success", true);
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
                        token.setDetails(usrDet);
                        SecurityContext context = SecurityContextHolder.getContext();
                        context.setAuthentication(token);
                    }
                } else {
                    map.put("success", false);
                    Map errors = new HashMap();
                    errors.put("wrongPassword", "Вы ввели неверный пароль.");
                    map.put("errors", errors);
                }
            }
        } else {
            map.put("success", false);
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            Map errors = new HashMap();
            errors.put("wrong", errorMessage);
            map.put("errors", errors);
        }
        return map;
    }

    @RequestMapping(value = "/userprofile/searches.html", method = RequestMethod.GET)
    public ModelAndView showUserProfile() {
        ModelAndView modelAndView = new ModelAndView("userSearch.jade");
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        List<SearchVariant> searchVariantList = this.searchVariantDao.findByEmail(currentUser.getEmail());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("userPage", "searches.html");
        modelAndView.addObject("searchVariants", searchVariantList);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/advertisements.html", method = RequestMethod.GET)
    public ModelAndView showUserAdvertisements() {
        ModelAndView modelAndView = new ModelAndView("userAdvertisements.jade");
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        modelAndView.addObject("userPage", "advertisements.html");
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    private Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserEntity userEntity = (UserEntity) auth.getPrincipal();
            return userEntity.getId();
        }
        return (long) 0;
    }

    @RequestMapping(value = "/userprofile/edit.html", method = RequestMethod.GET)
    public ModelAndView editProfile(
        @RequestParam(value = "firstNameError", required = false) final String firstNameError,
        @RequestParam(value = "lastNameError", required = false) final String lastNameError
    ) {
        ModelAndView modelAndView = new ModelAndView("editProfile.jade");
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("errorFromFirstName", EncodeDecodeService.decode(firstNameError));
        modelAndView.addObject("errorFromLastName", EncodeDecodeService.decode(lastNameError));
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/edit.html", method = RequestMethod.POST)
    public String changeUserInformation(final EditingUserInfoForm editingUserInfoForm, BindingResult bindingResult) {
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        this.editingUserInfoFormValidator.validate(editingUserInfoForm, bindingResult);
        if (bindingResult.hasErrors()) {
            String redirectAddress = "redirect:/new/user/userprofile/edit.html?firstNameError=";
            FieldError fieldErrorFromFirstName = bindingResult.getFieldError("FirstName");
            if (fieldErrorFromFirstName != null) {
                redirectAddress += EncodeDecodeService.encode(fieldErrorFromFirstName.getDefaultMessage());
            }
            redirectAddress += "&lastNameError=";
            FieldError fieldErrorFromLastName = bindingResult.getFieldError("LastName");
            if (fieldErrorFromLastName != null) {
                redirectAddress += EncodeDecodeService.encode(fieldErrorFromLastName.getDefaultMessage());
            }
            return redirectAddress;
        }

        String newFirstName = editingUserInfoForm.getFirstName();
        String newLastName = editingUserInfoForm.getLastName();
        String newAvatar = currentUser.getAvatar();
        MultipartFile avatarFile = editingUserInfoForm.getImage();
        FileManager fileManager = new FileManager();

        if (editingUserInfoForm.getIsDelete() == null) {
            if (!avatarFile.getOriginalFilename().equals("")) {
                fileManager.deleteFile(newAvatar, false);
                newAvatar = fileManager.savingFile(avatarFile, false);
            }
        } else {
            fileManager.deleteFile(newAvatar, false);
            newAvatar = null;
        }

        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setAvatar(newAvatar);
        this.userDao.updateData(currentUser);
        return "redirect:/new/user/userprofile/searches.html";
    }
}
