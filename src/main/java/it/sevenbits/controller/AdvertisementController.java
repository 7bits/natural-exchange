package it.sevenbits.controller;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.security.Role;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.captcha.Captcha;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.annotation.Resource;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.NewsPostingValidator;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Annotated spring controller class
 */
@Controller
@SessionAttributes({ "captchaString" })
@RequestMapping(value = "advertisement")
public class AdvertisementController {
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Resource(name = "advertisementDao")
    private AdvertisementDao advertisementDao;

    @Resource(name = "subscriberDao")
    private SubscriberDao subscribertDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

    @Resource(name = "auth")
    private MyUserDetailsService myUserDetailsService;

// --Commented out by Inspection START (18.03.14 11:42):
//    @Autowired
//    private AdvertisementSearchingValidator advertisementSearchingValidator;
// --Commented out by Inspection STOP (18.03.14 11:42)

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    /**
     * Gives information about all advertisements for display
     *
     * @return jsp-page with advertisements information
     * @throws FileNotFoundException
     *             sortDateOrder = true if ascending, false otherwise
     *             sortTitleOrder = so on
     */
    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(
            @RequestParam(value = "sortedBy", required = false)final String sortByNameParam,
            @RequestParam(value = "sortOrder", required = false)final String sortOrderParam,
            @RequestParam(value = "currentPage", required = false)final Integer currentPageParam,
            @RequestParam(value = "pageSize", required = false)final Integer pageSizeParam,
            @RequestParam(value = "currentCategory", required = false)final String currentCategoryParam,
            @RequestParam(value = "currentKeyWords", required = false)final String keyWordsParam,
            final AdvertisementSearchingForm advertisementSearchingFormParam,
            final MailingNewsForm mailingNewsFormParam, final BindingResult bindingResult)
            throws FileNotFoundException {
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setAll();
        String[] selectedCategories = advertisementSearchingFormParam.getCategories();
        String[] currentCategories;
        if (currentCategoryParam == null) { //запуск пустой страницы
            advertisementSearchingForm.setAll();
            currentCategories = advertisementSearchingForm.getCategories();
        } else {
            //Пришли параметры от формы выбора поиска
            if (selectedCategories != null) {
                currentCategories = selectedCategories;
            } else { //От какогото другого элемента
                currentCategories = stringToTokensArray(currentCategoryParam);
            }
            advertisementSearchingForm.setCategories(currentCategories);
            advertisementSearchingForm.setKeyWords(advertisementSearchingFormParam.getKeyWords());
        }
        modelAndView.addObject("currentCategory", stringArrayToString(currentCategories));
        modelAndView.addObject("advertisementSearchingForm", advertisementSearchingForm);
        String currentColumn;
        SortOrder currentSortOrder;
        if (sortByNameParam == null) {
            currentColumn = Advertisement.CREATED_DATE_COLUMN_CODE;
            currentSortOrder = SortOrder.DESCENDING;
        } else  {
            //TODO: check matching with columns
            currentColumn = sortByNameParam;
            if (sortOrderParam == null) {
                currentSortOrder = SortOrder.ASCENDING;
            } else {
                try {
                    currentSortOrder = SortOrder.valueOf(sortOrderParam);
                } catch (IllegalArgumentException e) {
                    currentSortOrder = SortOrder.NONE;
                }
            }
        }
        SortOrder newSortOrder = SortOrder.getViceVersa(currentSortOrder);
        if (currentColumn.equals(Advertisement.TITLE_COLUMN_CODE)) {
            modelAndView.addObject("sortedByTitle", Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle", newSortOrder.toString());
            modelAndView.addObject("sortedByDate", Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate", SortOrder.ASCENDING.toString());
        } else {
            modelAndView.addObject("sortedByTitle", Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle", SortOrder.ASCENDING.toString());
            modelAndView.addObject("sortedByDate", Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate", newSortOrder.toString());
        }
        List<Advertisement> advertisements;

        if (keyWordsParam != null) {
            advertisements = findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(
                    currentCategories, keyWordsParam, currentSortOrder, currentColumn
            );
            advertisementSearchingForm.setKeyWords(keyWordsParam);
            modelAndView.addObject("currentKeyWords", keyWordsParam);
        } else {
            if (advertisementSearchingFormParam.getKeyWords() == null || advertisementSearchingFormParam.getKeyWords().equals(""))
            {
                if (advertisementSearchingForm.getCategories() == null) {
                    advertisements = this.advertisementDao.findAll(currentSortOrder, currentColumn);
                } else {
                    advertisements = findAllAdvertisementsWithCategoryOrderBy(currentCategories, currentSortOrder, currentColumn);
                }
            } else {
                modelAndView.addObject("currentKeyWords", advertisementSearchingFormParam.getKeyWords());
                advertisements = findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(
                        currentCategories,
                        advertisementSearchingFormParam.getKeyWords(),
                        currentSortOrder,
                        currentColumn)
                ;
            }
        }
        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        int pageSize;
        if (pageSizeParam == null) {
            pageSize = defaultPageSize();
        } else {
            pageSize = pageSizeParam;
        }
        pageList.setPageSize(pageSize);
        int noOfPage = pageList.getPageCount();
        int currentPage;
        if (currentPageParam == null || currentPageParam >= noOfPage) {
            currentPage = 0;
        } else {
            currentPage = currentPageParam;
        }
        pageList.setPage(currentPage);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("defaultPageSize", defaultPageSize());
        modelAndView.addObject("noOfPage", noOfPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentColumn", currentColumn);
        modelAndView.addObject("currentSortOrder", currentSortOrder);

        // checking user for subscriber
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // if user != anonym
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Subscriber subscriber = new Subscriber(user.getUsername());
            if (!this.subscribertDao.isExists(subscriber)) {
                // check aside.jsp for flags isNotUser, userEmail and isNotSubscriber
                modelAndView.addObject("isNotUser", true);
                modelAndView.addObject("userEmail", user.getUsername());
                if (mailingNewsFormParam.getEmailNews() != null) {
                    Subscriber newSubscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                    mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
                    if (!bindingResult.hasErrors()) {
                        modelAndView.addObject("isNotUser", false);
                        this.subscribertDao.create(newSubscriber);
                    }
                }
            }
        } else {
            modelAndView.addObject("isNotUser", true);
            modelAndView.addObject("isNotSubscriber", true);
            if (mailingNewsFormParam.getEmailNews() != null) {
                Subscriber newSubscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
                if (!bindingResult.hasErrors()) {
                    MailingNewsForm mailingNewsForm = new MailingNewsForm();
                    if (this.subscribertDao.isExists(newSubscriber)) {
                        mailingNewsForm.setEmailNews("Вы уже подписаны.");
                    } else {
                        modelAndView.addObject("isNotSubscriber", "true");
                        this.subscribertDao.create(newSubscriber);
                        mailingNewsForm.setEmailNews("Ваш e-mail добавлен.");
                    }
                    modelAndView.addObject("mailingNewsForm", mailingNewsForm);
                }
            }
        }
        return modelAndView;
    }

    private List<Advertisement> findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(
            final String[] categories, final String keyWordsStr, final SortOrder sortOrder, final String sortColumn
    ) {
        if (categories.length == 0) {
            return Collections.emptyList();
        }
        StringTokenizer token = new StringTokenizer(keyWordsStr);
        String[] keyWords = new String[token.countTokens()];
        for (int i = 0; i < keyWords.length; i++) {
            keyWords[i] = token.nextToken();
        }
        return this.advertisementDao.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(categories, keyWords, sortOrder, sortColumn);
    }

    private List<Advertisement> findAllAdvertisementsWithCategoryOrderBy(
            final String[] categories, final SortOrder sortOrder, final String sortColumn
    ) {
        if (categories.length == 0) {
            return Collections.emptyList();
        }
        return this.advertisementDao.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(categories, null, sortOrder, sortColumn);
    }

    private String stringArrayToString(final String[] src) {
        if (src == null) {
            return null;
        }
        String dest = " ";
        for (String aSrc : src) {
            dest += aSrc + " ";
        }
        return dest;
    }

    private String[] stringToTokensArray(final String str) {
        if (str == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str);
        String[] words = new String[token.countTokens()];
        for (int i = 0 ; i < words.length ; i++) {
            words[i] = token.nextToken();
        }
        return words;
    }
    /**
     * Gives information about one advertisement by id for display
     *
     * @return jsp-page with advertisement information
     */

    @RequestMapping(value = "/moderator/list.html", method = RequestMethod.GET)
    public ModelAndView moderatorList(@RequestParam(value = "sortedBy", required = false)final String sortByNameParam,
                                      @RequestParam(value = "sortOrder", required = false)final String sortOrderParam,
                                      @RequestParam(value = "currentPage", required = false)final Integer currentPageParam,
                                      @RequestParam(value = "pageSize", required = false)final Integer pageSizeParam,
                                      @RequestParam(value = "currentCategory", required = false)final String currentCategoryParam,
                                      @RequestParam(value = "currentKeyWords", required = false)final String keyWordsParam,
                                      final AdvertisementSearchingForm advertisementSearchingFormParam) {
        ModelAndView modelAndView = new ModelAndView("advertisement/listModerator");
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();

        String[] selectedCategories = advertisementSearchingFormParam.getCategories();
        String[] currentCategories;
        advertisementSearchingForm.setCategories(selectedCategories);
        advertisementSearchingForm.setKeyWords(advertisementSearchingFormParam.getKeyWords());

        if (currentCategoryParam == null) { //запуск пустой страницы
            advertisementSearchingForm.setCategories(new String[]{"new","delete"});
            currentCategories = advertisementSearchingForm.getCategories();
        } else {
            //Пришли параметры от формы выбора поиска
            if (selectedCategories != null) {
                currentCategories = selectedCategories;
            } else { //От какогото другого элемента
                currentCategories = stringToTokensArray(currentCategoryParam);
            }
            advertisementSearchingForm.setCategories(currentCategories);
            advertisementSearchingForm.setKeyWords(advertisementSearchingFormParam.getKeyWords());
        }
        modelAndView.addObject("currentCategory", stringArrayToString(currentCategories));
        modelAndView.addObject("advertisementSearchingForm", advertisementSearchingForm);
        String currentColumn;
        SortOrder currentSortOrder;
        if (sortByNameParam == null) {
            currentColumn = Advertisement.CREATED_DATE_COLUMN_CODE;
            currentSortOrder = SortOrder.DESCENDING;
        } else  {
            //TODO: check matching with columns
            currentColumn = sortByNameParam;
            if (sortOrderParam == null) {
                currentSortOrder = SortOrder.ASCENDING;
            } else {
                try {
                    currentSortOrder = SortOrder.valueOf(sortOrderParam);
                } catch (IllegalArgumentException e) {
                    currentSortOrder = SortOrder.NONE;
                }
            }
        }
        SortOrder newSortOrder = SortOrder.getViceVersa(currentSortOrder);
        if (currentColumn.equals(Advertisement.TITLE_COLUMN_CODE)) {
            modelAndView.addObject("sortedByTitle", Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle", newSortOrder.toString());
            modelAndView.addObject("sortedByDate", Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate", SortOrder.ASCENDING.toString());
        } else {
            modelAndView.addObject("sortedByTitle", Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle", SortOrder.ASCENDING.toString());
            modelAndView.addObject("sortedByDate", Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate", newSortOrder.toString());
        }
        List<Advertisement> advertisements;
        if (currentCategories.length == 2) {
            advertisements = this.advertisementDao.findAllAdvertisementsWithKeyWordsOrderBy(
                    stringToTokensArray(advertisementSearchingFormParam.getKeyWords()),
                    currentSortOrder, currentColumn, true, true
            );
        } else if (currentCategories.length == 1) {
            if (currentCategories[0].equals("new")) {
                advertisements = this.advertisementDao.findAllAdvertisementsWithKeyWordsOrderBy(
                        stringToTokensArray(advertisementSearchingFormParam.getKeyWords()),
                        currentSortOrder, currentColumn, false, true
                );
            } else {
                advertisements = this.advertisementDao.findAllAdvertisementsWithKeyWordsOrderBy(
                        stringToTokensArray(advertisementSearchingFormParam.getKeyWords()),
                        currentSortOrder, currentColumn, true, false
                );
            }
        } else {
            advertisements = Collections.EMPTY_LIST;
        }
        PagedListHolder<Advertisement> pageList = new PagedListHolder<Advertisement>();
        pageList.setSource(advertisements);
        int pageSize;
        if (pageSizeParam == null) {
            pageSize = defaultPageSize();
        } else {
            pageSize = pageSizeParam.intValue();
        }
        pageList.setPageSize(pageSize);
        int noOfPage = pageList.getPageCount();
        int currentPage;
        if (currentPageParam == null || currentPageParam >= noOfPage) {
            currentPage = 0;
        } else {
            currentPage = currentPageParam;
        }
        pageList.setPage(currentPage);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("defaultPageSize", defaultPageSize());
        modelAndView.addObject("noOfPage", noOfPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentColumn", currentColumn);
        modelAndView.addObject("currentSortOrder", currentSortOrder);
        return modelAndView;
    }

    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(value = "id", required = true) final Long id,
                             @RequestParam(value = "currentCategory", required = true) final String currentCategory,
                             final MailingNewsForm mailingNewsFormParam, final BindingResult bindingResult,
                             HttpServletResponse servletResponse
    ) {
        //TODO: need to control viewing of deleted and new advertisement
        ModelAndView modelAndView = new ModelAndView("advertisement/view");
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setCategories(stringToTokensArray(currentCategory));
        modelAndView.addObject("advertisementSearchingForm", advertisementSearchingForm);
        modelAndView.addObject("currentCategory", currentCategory);
        modelAndView.addObject("currentId", id);
        Advertisement advertisement = this.advertisementDao.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            if((advertisement.getIs_new() || advertisement.getIs_deleted()) && !user.getAuthorities().contains(Role.createModeratorRole())) {
                return new ModelAndView();
            }
        } else {
            if(advertisement.getIs_new() || advertisement.getIs_deleted()) {
                return new ModelAndView();
            }
        }
        modelAndView.addObject("advertisement", advertisement);

        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Subscriber subscriber = new Subscriber(user.getUsername());
            if (!this.subscribertDao.isExists(subscriber)) {
                // check aside.jsp for flags isNotUser, userEmail and isNotSubscriber
                modelAndView.addObject("isNotUser", true);
                modelAndView.addObject("userEmail", user.getUsername());
                if (mailingNewsFormParam.getEmailNews() != null) {
                    Subscriber newSubscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                    mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
                    if (!bindingResult.hasErrors()) {
                        modelAndView.addObject("isNotUser", false);
                        this.subscribertDao.create(newSubscriber);
                    }
                }
            }
        } else {
            modelAndView.addObject("isNotUser", true);
            modelAndView.addObject("isNotSubscriber", true);
            if (mailingNewsFormParam.getEmailNews() != null) {
                Subscriber newSubscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
                if (!bindingResult.hasErrors()) {
                    MailingNewsForm mailingNewsForm = new MailingNewsForm();
                    if (this.subscribertDao.isExists(newSubscriber)) {
                        mailingNewsForm.setEmailNews("Вы уже подписаны.");
                    } else {
                        modelAndView.addObject("isNotSubscriber", "true");
                        this.subscribertDao.create(newSubscriber);
                        mailingNewsForm.setEmailNews("Ваш e-mail добавлен.");
                    }
                    modelAndView.addObject("mailingNewsForm", mailingNewsForm);
                }
            }
        }
        return modelAndView;
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placing(@RequestParam(value = "id", required = false) final Long id) {
        ModelAndView modelAndView = new ModelAndView("advertisement/placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        advertisementPlacingForm.setCategory("clothes");
        if (id != null) {
            Advertisement advertisement = this.advertisementDao.findById(id);
            advertisementPlacingForm.setCategory(advertisement.getCategory().getName());
            advertisementPlacingForm.setText(advertisement.getText());
            advertisementPlacingForm.setTitle(advertisement.getTitle());
        }
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        modelAndView.addObject("mailingNewsForm", new MailingNewsForm());
        modelAndView.addObject("isEditing",false);
        return modelAndView;
    }

    /**
     * Placing or editing the advertisement.
     * @param advertisementPlacingFormParam parameters from advertisement form
     * @param result
     * @param mailingNewsFormParam
     * @param mailRes
     * @param editingAdvertisementId id of advertisement which will
     *                               be editing
     * @return ModelAndView object
     */
    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacing(
            final AdvertisementPlacingForm advertisementPlacingFormParam,
            final BindingResult result,
            final MailingNewsForm mailingNewsFormParam,
            final BindingResult mailRes,
            final Long editingAdvertisementId
    ) {
        String defaultPhoto = "no_photo.png";
        if (mailingNewsFormParam.getEmailNews() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam, mailRes);
            ModelAndView modelAndView = new ModelAndView("advertisement/placing");
            if (!mailRes.hasErrors()) {
                Subscriber subscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                if (this.subscribertDao.isExists(subscriber)) {
                    mailingNewsForm.setEmailNews("Вы уже подписаны.");
                } else {
                    this.subscribertDao.create(subscriber);
                    mailingNewsForm.setEmailNews("Ваш e-mail добавлен.");
                }
                modelAndView.addObject("mailingNewsForm", mailingNewsForm);
            }
            AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
            advertisementPlacingForm.setCategory("clothes");
            modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
            modelAndView.addObject("isEditing",false);
            return modelAndView;
        } else {
            advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
            if (result.hasErrors()) {
                return new ModelAndView("advertisement/placing");
            }
            FileManager fileManager = new FileManager();
            String photo = null;
            if (advertisementPlacingFormParam.getImage().isEmpty() && editingAdvertisementId == null) {
                photo = defaultPhoto;
            } else if (!advertisementPlacingFormParam.getImage().isEmpty()) {
                photo = fileManager.savingFile(advertisementPlacingFormParam.getImage());
            }

            Advertisement advertisement = null;
            if (editingAdvertisementId == null) {
                advertisement = new Advertisement();
                advertisement.setPhotoFile(photo);
            } else {
                advertisement = advertisementDao.findById(editingAdvertisementId);
                if (photo != null) {
                    advertisement.setPhotoFile(photo);
                }
            }
            advertisement.setText(advertisementPlacingFormParam.getText());
            advertisement.setTitle(advertisementPlacingFormParam.getTitle());

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName;
            if (principal instanceof UserDetails) {//Alex: что это черт возьми???
                userName = ((UserDetails) principal).getUsername();
            } else {
                userName = principal.toString();
            }
            if (editingAdvertisementId != null) {
                this.advertisementDao.update(editingAdvertisementId,advertisement, advertisementPlacingFormParam.getCategory());
            } else {
                this.advertisementDao.create(advertisement, advertisementPlacingFormParam.getCategory(), userName);
            }
        }
        return new ModelAndView("advertisement/placingRequest");
    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public @ResponseBody byte[] captcha(HttpServletRequest request) {
        Captcha captcha = Captcha.newCaptcha(120, 60);
        HttpSession session = request.getSession();
        session.removeAttribute("captchaStr");
        session.setAttribute("captchaStr", captcha.getCaptchaString());
        return captcha.getCaptchaData();
    }


    @RequestMapping(value = "/post.html", method = RequestMethod.GET)
    public ModelAndView post() {

        ModelAndView modelAndView = new ModelAndView("advertisement/post");
        NewsPostingForm newsPostingForm = new NewsPostingForm();
        modelAndView.addObject("newsPostingForm", newsPostingForm);
        return modelAndView;
    }

    /**
     * News posting Validator class
     */
    @Autowired
    private NewsPostingValidator newsPostingValidator;

    @RequestMapping(value = "/post.html", method = RequestMethod.POST)
    public ModelAndView posting(final NewsPostingForm newsPostingFormParam, final BindingResult result) {

        if (newsPostingFormParam != null) {
            newsPostingValidator.validate(newsPostingFormParam, result);
            if (result.hasErrors()) {
                return new ModelAndView("advertisement/post");
            }
            this.mailSenderService.newsPosting(newsPostingFormParam.getNewsTitle(), newsPostingFormParam.getNewsText());
        }
        return new ModelAndView("advertisement/post");
    }



    private int defaultPageSize() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            return DEFAULT_PAGE_SIZE;
        }
        return Integer.parseInt(prop.getProperty("list.count"));
    }

    @RequestMapping(value = "/user/auth_failed.html", method = RequestMethod.GET)
    public ModelAndView auth() {
       return new ModelAndView("user/auth_failed");
    }

    /**
     * Saving searching parameters.
     * @param keyWordsParam Key words
     * @param emailParam Email
     * @param categoriesParam Categories
     * @return 0 - if user with that email doesn't exists
     *         1 - if user with that email exists
     */

    @RequestMapping(value = "/savingSearch.html", method = RequestMethod.POST)
    public @ResponseBody JSONObject getSavingSearchRequest(@RequestParam(value = "wordSearch", required = false) final String keyWordsParam,
                      @RequestParam(value = "email", required = false) final String emailParam,
                      @RequestParam(value = "categorySearch", required = false) final String categoriesParam,
                      @RequestParam(value = "isNeedCapcha", required = true) final boolean isAnonimus,
                      @RequestParam(value = "captcha", required = false) final String userCaptchaText,
                      HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        if (!userDao.isExistUserWithEmail(emailParam)) {
            resultJson.put("success","auth");
        } else if(isAnonimus) {
            if (!userCaptchaText.equals(request.getSession().getAttribute("captchaStr")) && isAnonimus) {
                resultJson.put("success","captcha");
            }
        } else {
            boolean isDoubleSearch = false;
            mailSenderService.sendSearchVariant(emailParam, keyWordsParam, categoriesParam);
            SearchVariant searchVariant = new SearchVariant(emailParam, keyWordsParam, categoriesParam);

            List<SearchVariant> allSearchesOfUser = this.searchVariantDao.findByEmail(emailParam);
            for (SearchVariant searches : allSearchesOfUser) {
                if (searches.equals(searchVariant)) {
                    resultJson.put("success", "doubleSearch");
                    isDoubleSearch = true;
                    break;
                }
            }

            if (!isDoubleSearch) {
                this.searchVariantDao.create(searchVariant);
                resultJson.put("success","true");
            }
        }
        return resultJson;
    }

    @RequestMapping(value = "/delete.html", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) final Long advertisementId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else {
            return "redirect:/advertisement/list.html";
        }
        Advertisement advertisement = this.advertisementDao.findById(advertisementId);
        String userEmail = advertisement.getUser().getEmail();
        if(userDetails.getAuthorities().contains(Role.createModeratorRole()) || userDetails.getUsername().equals(userEmail)) {
            this.advertisementDao.setDeleted(advertisementId);
        }
        return "redirect:/advertisement/list.html";
    }

    @RequestMapping(value = "/approve.html", method = RequestMethod.GET)
    public String approve(@RequestParam(value = "id", required = true) final Long advertisementId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) principal;
        if(user.getAuthorities().contains(Role.createModeratorRole())) {
            this.advertisementDao.setApproved(advertisementId);
        }
        return "redirect:/advertisement/moderator/list.html";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id", required = true) final Long advertisementId) {
        ModelAndView modelAndView =  new ModelAndView("advertisement/placing");
        modelAndView.addObject("isEditing",true);
        modelAndView.addObject("advertisementId",advertisementId);
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        Advertisement advertisement = this.advertisementDao.findById(advertisementId);
        advertisementPlacingForm.setCategory(advertisement.getCategory().getName());
        advertisementPlacingForm.setText(advertisement.getText());
        advertisementPlacingForm.setTitle(advertisement.getTitle());

        modelAndView.addObject("advertisementPlacingForm",advertisementPlacingForm);
        return modelAndView;
    }

}