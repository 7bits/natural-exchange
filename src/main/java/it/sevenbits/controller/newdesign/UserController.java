package it.sevenbits.controller.newdesign;


import it.sevenbits.dao.*;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.TimeManager;
import it.sevenbits.util.form.EditingUserInfoForm;
import it.sevenbits.util.form.SearchEditForm;
import it.sevenbits.util.form.UserEntryForm;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.SearchEditValidator;
import it.sevenbits.util.form.validator.UserEntryValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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
    private CategoryDao categoryDao;

    @Autowired
    private SearchVariantDao searchVariantDao;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

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
    public ModelAndView editProfile() {
        ModelAndView modelAndView = new ModelAndView("editProfile.jade");
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/edit.html", method = RequestMethod.POST)
    public String changeUserInformation(final EditingUserInfoForm editingUserInfoForm) {
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        User userNew = new User();

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

        userNew.setEmail(currentUser.getEmail());
        userNew.setPassword(currentUser.getPassword());
        userNew.setFirstName(newFirstName);
        userNew.setLastName(newLastName);
        userNew.setAvatar(newAvatar);
        userNew.setVk_link(currentUser.getVk_link());
        userNew.setUpdateDate(TimeManager.getTime());
        this.userDao.updateData(userNew);
        return "redirect:/new/user/userprofile/searches.html";
    }

    @RequestMapping(value = "/userprofile/editSearch.html", method = RequestMethod.GET)
    public ModelAndView searchEditing(@RequestParam(value = "id", required = true) final Long id) {
        ModelAndView modelAndView = new ModelAndView("editSearch");
        SearchEditForm searchEditForm = new SearchEditForm();
        if (id != null) {
            SearchVariant searchVariant = this.searchVariantDao.findById(id);
            searchEditForm.setCategory(searchVariant.getCategories());
            searchEditForm.setKeywords(searchVariant.getKeyWords());
        }
        List<Category> categories = this.categoryDao.findAll();
        String[] keywords = StringUtils.split(searchEditForm.getKeywords());
        Map<String, String> errors = new HashMap<>();
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("searchEditForm", searchEditForm);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("errors", errors);
        return modelAndView;
    }

    @Autowired
    SearchEditValidator searchEditValidator;

    @RequestMapping(value = "/userprofile/editSearch.html", method = RequestMethod.POST)
    public ModelAndView searchEditing(
            final SearchEditForm searchEditForm,
            final BindingResult result
    ) {
        searchEditValidator.validate(searchEditForm, result);
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            ModelAndView modelAndView = new ModelAndView("/userprofile/editSearch.html");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        Long id = this.getCurrentUser();
        User currentUser = this.userDao.findById(id);
        SearchVariant newSearchVariant = new SearchVariant();
        newSearchVariant.setKeyWords(searchEditForm.getKeywords());
        newSearchVariant.setCategories(searchEditForm.getCategory());
        newSearchVariant.setEmail(currentUser.getEmail());
        newSearchVariant.setCreatedDate(TimeManager.getTime());
        this.searchVariantDao.create(newSearchVariant);
        return new ModelAndView("redirect:/userprofile/searches.html");
    }
}
