package it.sevenbits.web.controller.User;


import it.sevenbits.repository.dao.*;
import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.User;
import it.sevenbits.repository.entity.hibernate.CategoryEntity;
import it.sevenbits.repository.entity.hibernate.SearchVariantEntity;
import it.sevenbits.services.CategoryService;
import it.sevenbits.web.helpers.EncodeDecodeHelper;
import it.sevenbits.web.security.MyUserDetailsService;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.web.util.ErrorMessages;
import it.sevenbits.web.util.FileManager;
import it.sevenbits.web.util.TimeManager;
import it.sevenbits.web.util.form.advertisement.CurrentSearchVariantForm;
import it.sevenbits.web.util.form.advertisement.SearchEditForm;
import it.sevenbits.web.util.form.user.EditingUserInfoForm;
import it.sevenbits.web.util.form.user.UserEntryForm;
import it.sevenbits.web.util.form.user.UserRegistrationForm;
import it.sevenbits.web.util.form.validator.advertisement.SearchEditValidator;
import it.sevenbits.web.util.form.validator.user.EditingUserInfoFormValidator;
import it.sevenbits.web.util.form.validator.user.UserEntryValidator;
import it.sevenbits.web.util.form.validator.user.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    public static final int REGISTRATION_PERIOD = 3;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SearchVariantDao searchVariantDao;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private EditingUserInfoFormValidator editingUserInfoFormValidator;

    @Autowired
    private UserEntryValidator userEntryValidator;

    @Autowired
    SearchEditValidator searchEditValidator;

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    Map registrationRequest(@ModelAttribute("email") UserRegistrationForm form, final BindingResult bindingResult) {
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
                user.setAvatar("noavatar.png");
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
            Map errors = new HashMap();
            errors.put("wrong", ErrorMessages.getFieldsErrorMessages(bindingResult));
            map.put("errors", errors);
        }
        return map;
    }

    @RequestMapping(value = "/entry.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    Map entryRequest(@ModelAttribute("email") UserEntryForm form, final BindingResult bindingResult) {
        Map map = new HashMap();
        Map errors = new HashMap();
        userEntryValidator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            if (!userDao.isExistUserWithEmail(form.getEmail())) {
                map.put("success", false);
                errors.put("notExist", "Вы еще не зарегистрированы.");
                map.put("errors", errors);
            } else {
                Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
                User user = userDao.findUserByEmail(form.getEmail());
                if (user.getIsBanned()) {
                    map.put("success", false);
                    errors.put("userIsBanned", "Данный пользователь забанен администрацией");
                    map.put("errors", errors);
                    return map;
                }
                if (user.getPassword().equals(md5PasswordEncoder.encodePassword(form.getPassword(), ""))) {
                    if (user.getActivationDate() != 0L) {
                        map.put("success", false);
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
                    errors.put("wrongPassword", "Вы ввели неверный пароль.");
                    map.put("errors", errors);
                }
            }
        } else {
            map.put("success", false);
            errors.put("wrong", ErrorMessages.getFieldsErrorMessages(bindingResult));
            map.put("errors", errors);
        }
        return map;
    }

    @RequestMapping(value = "/showUser.html", method = RequestMethod.GET)
    public ModelAndView showUser(@RequestParam(value = "id", required = true) final Long userId) {
        ModelAndView modelAndView = new ModelAndView("user.jade");
        User user = this.userDao.findById(userId);
        if (user == null) {
            return new ModelAndView("redirect:/main.html");
        }
        List<Advertisement> userAdvertisements = new LinkedList<>();
        User currentUser = AuthService.getUser();
        if (currentUser != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(currentUser);
        }

        modelAndView.addObject("user", user);
        modelAndView.addObject("advertisements", this.advertisementDao.findUserAdvertisements(user));
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        return modelAndView;
    }

    boolean checkRegistrationLink(final User user, final String code) {
        if (user == null) {
            return false;
        }
        if (TimeManager.getTime() > user.getActivationDate()) {
            return false;
        }
        if (!code.equals(user.getActivationCode())) {
            logger.info("check not passed: code not equals");
            return false;
        }
        return true;
    }


    @RequestMapping(value = "/magic.html", method = RequestMethod.GET)
    public ModelAndView magicPage(@RequestParam(value = "code", required = true) final String codeParam, @RequestParam(value = "mail", required = true) final String mailParam) {
        User user = this.userDao.findUserByEmail(mailParam);
        if (user == null) {
            return new ModelAndView("conf_failed");
        }
        if (user.getActivationDate() == 0) {
            return new ModelAndView("registrationResult");
        }
        if (checkRegistrationLink(user, codeParam)) {
            this.userDao.updateActivationCode(user);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(usrDet);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            return new ModelAndView("registrationResult");
        } else {
            return new ModelAndView("conf_failed");
        }
    }

    @RequestMapping(value = "/conf_failed.html", method = RequestMethod.GET)
    public ModelAndView confirmProfileFailed() {
        return new ModelAndView("user/conf_failed");
    }

    @RequestMapping(value = "/userprofile/searches.html", method = RequestMethod.GET)
    public ModelAndView showUserProfile() {
        Long id = AuthService.getUserId();
        if (id == 0) {
            return new ModelAndView("redirect:/main.html");
        }
        User currentUser = this.userDao.findById(id);
        List<SearchVariantEntity> searchVariantList = this.searchVariantDao.findByEmail(currentUser.getEmail());
        ModelAndView modelAndView = new ModelAndView("userSearch.jade");
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("userPage", "searches.html");
        modelAndView.addObject("searchVariants", searchVariantList);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/edit.html", method = RequestMethod.GET)
    public ModelAndView editProfile(@RequestParam(value = "firstNameError", required = false) final String firstNameError, @RequestParam(value = "lastNameError", required = false) final String lastNameError, @RequestParam(value = "photoFileError", required = false) final String photoFileError) {
        Long id = AuthService.getUserId();
        if (id == 0) {
            return new ModelAndView("redirect:/main.html");
        }
        User currentUser = this.userDao.findById(id);
        ModelAndView modelAndView = new ModelAndView("editProfile.jade");
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("errorFromFirstName", EncodeDecodeHelper.decode(firstNameError));
        modelAndView.addObject("errorFromLastName", EncodeDecodeHelper.decode(lastNameError));
        modelAndView.addObject("errorFromPhotoFile", EncodeDecodeHelper.decode(photoFileError));
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/edit.html", method = RequestMethod.POST)
    public String changeUserInformation(final EditingUserInfoForm editingUserInfoForm, BindingResult bindingResult) {
        Long id = AuthService.getUserId();
        User currentUser = this.userDao.findById(id);
        this.editingUserInfoFormValidator.validate(editingUserInfoForm, bindingResult);
        if (bindingResult.hasErrors()) {
            String redirectAddress = "redirect:/user/userprofile/edit.html?firstNameError=";
            FieldError fieldErrorFromFirstName = bindingResult.getFieldError("FirstName");
            if (fieldErrorFromFirstName != null) {
                redirectAddress += EncodeDecodeHelper.encode(fieldErrorFromFirstName.getDefaultMessage());
            }
            redirectAddress += "&lastNameError=";
            FieldError fieldErrorFromLastName = bindingResult.getFieldError("LastName");
            if (fieldErrorFromLastName != null) {
                redirectAddress += EncodeDecodeHelper.encode(fieldErrorFromLastName.getDefaultMessage());
            }
            redirectAddress += "&photoFileError=";
            FieldError fieldPhotoFile = bindingResult.getFieldError("image");
            if (fieldPhotoFile != null) {
                redirectAddress += EncodeDecodeHelper.encode(fieldPhotoFile.getDefaultMessage());
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
                newAvatar = fileManager.savePhotoFile(avatarFile, false);
            }
        } else {
            if (avatarFile.getOriginalFilename().equals("")) {
            } else {
                fileManager.deleteFile(newAvatar, false);
                newAvatar = "noavatar.png";
            }
        }

        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setAvatar(newAvatar);
        this.userDao.updateData(currentUser);
        AuthService.changeUserContext(currentUser);
        return "redirect:/user/userprofile/searches.html";
    }

    @RequestMapping(value = "/userprofile/advertisements.html", method = RequestMethod.GET)
    public ModelAndView showAdvertisementsOfCurrentUser() {
        Long id = AuthService.getUserId();
        if (id == 0) {
            return new ModelAndView("redirect:/main.html");
        }
        User currentUser = this.userDao.findById(id);
        List<Advertisement> advertisementList = advertisementDao.findUserAdvertisements(currentUser);
        ModelAndView modelAndView = new ModelAndView("userAdvertisements.jade");
        modelAndView.addObject("advertisements", advertisementList);
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/deleteSearch.html", method = RequestMethod.GET)
    public ModelAndView searchDeleting(@RequestParam(value = "id", required = true) final Long searchVariantId) {
        Long id = AuthService.getUserId();
        if (id == 0) {
            return new ModelAndView("redirect:/main.html");
        }
        User currentUser = this.userDao.findById(id);
        List<SearchVariantEntity> searchVariantList = this.searchVariantDao.findByEmail(currentUser.getEmail());
        ModelAndView modelAndView = new ModelAndView("userSearch.jade");
        this.searchVariantDao.delete(this.searchVariantDao.findById(searchVariantId));
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("userPage", "searches.html");
        modelAndView.addObject("searchVariants", searchVariantList);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/editSearch.html", method = RequestMethod.GET)
    public ModelAndView searchEditing(@RequestParam(value = "id", required = true) final Long id) {
        if (AuthService.getUserId() == 0) {
            return new ModelAndView("redirect:/main.html");
        }
        ModelAndView modelAndView = new ModelAndView("editSearch");
        CurrentSearchVariantForm currentSearchForm = new CurrentSearchVariantForm();
        Map<String, String> errors = new HashMap<>();
        if (id != null) {
            SearchVariantEntity searchVariant = this.searchVariantDao.findById(id);
            currentSearchForm.setCategory(searchVariant.getCategories());
            currentSearchForm.setKeywords(searchVariant.getKeyWords());
            currentSearchForm.setSearchVariantId(id);
        }
        List<Category> categories = this.categoryDao.findAll();
        String allCategories = categoryService.findAllCategoriesAsString();
        String[] keywords = StringUtils.split(currentSearchForm.getKeywords());
        boolean isAllCategories = false;
        if (currentSearchForm.getCategory().size() == this.categoryDao.categoryCount()) {
            isAllCategories = true;
            CategoryEntity allCategoriesEntity = new CategoryEntity();
            allCategoriesEntity.setSlug(this.allCategoriesSlug());
            modelAndView.addObject("selectedCategory", allCategoriesEntity);
        } else {
            modelAndView.addObject("selectedCategory", currentSearchForm.getCategory().toArray()[0]);
        }
        modelAndView.addObject("allCategoriesSelected", isAllCategories);
        modelAndView.addObject("allCategories", allCategories);
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("currentSearchForm", currentSearchForm);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("errors", errors);
        return modelAndView;
    }

    @RequestMapping(value = "/userprofile/editSearch.html", method = RequestMethod.POST)
    public ModelAndView searchEditing(final SearchEditForm searchEditForm, final BindingResult result) {
        searchEditValidator.validate(searchEditForm, result);
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            ModelAndView modelAndView = new ModelAndView("/userprofile/editSearch.html");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        String[] separateCategories = StringUtils.split(searchEditForm.getCategory());
        Set<CategoryEntity> categories = this.categoryDao.findBySlugs(separateCategories);
        this.searchVariantDao.update(this.searchVariantDao.findById(searchEditForm.getSearchVariantId()), searchEditForm.getKeywords(), categories);
        return new ModelAndView("redirect:/user/userprofile/searches.html");
    }

    private String allCategoriesSlug() {
        List<Category> allCategories = this.categoryDao.findAll();
        String result = "";
        for (Category currentCategory : allCategories) {
            result += currentCategory.getSlug() + " ";
        }
        return StringUtils.trim(result);
    }

    private String arrayToString(String[] strings) {
        if (strings == null) {
            return null;
        }
        return StringUtils.join(strings, ' ');
    }
}
