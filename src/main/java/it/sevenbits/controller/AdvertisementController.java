package it.sevenbits.controller;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.NewsPostingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Annotated spring controller class
 */
@Controller
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

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    /**
     * Gives information about all advertisements for display
     *
     * @return jsp-page with advertisements information
     * @throws FileNotFoundException
     *             sortDateOrder = true if ascending, false othewise
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

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("bandit@gmail.com", "111");
        UserDetails usrDet = myUserDetailsService.loadUserByUsername("bandit@gmail.com");
        token.setDetails(usrDet);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

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
            currentPage = currentPageParam.intValue();
        }
        pageList.setPage(currentPage);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("defaultPageSize", defaultPageSize());
        modelAndView.addObject("noOfPage", noOfPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentColumn", currentColumn);
        modelAndView.addObject("currentSortOrder", currentSortOrder);
        if (mailingNewsFormParam.getEmailNews() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
            if (!bindingResult.hasErrors()) {
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
        for (int i = 0 ; i < src.length ; i++) {
            dest += src[i] + " ";
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
    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(value = "id", required = true) final Long id,
                             @RequestParam(value = "currentCategory", required = true) final String currentCategory,
                             final MailingNewsForm mailingNewsFormParam, final BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("advertisement/view");
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setCategories(stringToTokensArray(currentCategory));
        modelAndView.addObject("advertisementSearchingForm", advertisementSearchingForm);
        modelAndView.addObject("currentCategory", currentCategory);
        modelAndView.addObject("currentId", id);
        Advertisement advertisement = this.advertisementDao.findById(id);
        modelAndView.addObject("advertisement", advertisement);
        if (mailingNewsFormParam.getEmailNews() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
            if (!bindingResult.hasErrors()) {
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
        }
        return modelAndView;
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placing() {
        ModelAndView modelAndView = new ModelAndView("advertisement/placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        advertisementPlacingForm.setCategory("clothes");
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        modelAndView.addObject("mailingNewsForm", new MailingNewsForm());
        return modelAndView;
    }

    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacing(
            final AdvertisementPlacingForm advertisementPlacingFormParam,
            final BindingResult result,
            final MailingNewsForm mailingNewsFormParam,
            final BindingResult mailRes
    ) {
        if (mailingNewsFormParam.getEmailNews() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam, mailRes);
            ModelAndView mdv = new ModelAndView("advertisement/placing");
            if (!mailRes.hasErrors()) {
                Subscriber subscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                if (this.subscribertDao.isExists(subscriber)) {
                    mailingNewsForm.setEmailNews("Вы уже подписаны.");
                } else {
                    this.subscribertDao.create(subscriber);
                    mailingNewsForm.setEmailNews("Ваш e-mail добавлен.");
                }
                mdv.addObject("mailingNewsForm", mailingNewsForm);
            }
            AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
            advertisementPlacingForm.setCategory("clothes");
            mdv.addObject("advertisementPlacingForm", advertisementPlacingForm);
            return mdv;
        } else {
            advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
            if (result.hasErrors()) {
                return new ModelAndView("advertisement/placing");
            }
            FileManager fileManager = new FileManager();
            String photo;
            if (advertisementPlacingFormParam.getImage().isEmpty()) {
                photo = "no_photo.png";
            } else {
                photo = fileManager.savingFile(advertisementPlacingFormParam.getImage());
            }
            Advertisement advertisement = new Advertisement();
            advertisement.setText(advertisementPlacingFormParam.getText());
            advertisement.setPhotoFile(photo);
            advertisement.setTitle(advertisementPlacingFormParam.getTitle());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName;
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            } else {
                userName = principal.toString();
            }
            this.advertisementDao.create(advertisement, advertisementPlacingFormParam.getCategory(), userName);
        }
        return new ModelAndView("advertisement/placingRequest");
    }

    @RequestMapping(value = "/makeCaptcha.html")
    public ModelAndView makeCap() {
        ModelAndView modelAndView = new ModelAndView("advertisement/makeCaptcha");
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView("user/auth_failed");
        return modelAndView;
    }

    /**
     *
     * @param keyWordsParam Key words
     * @param emailParam Email
     * @param categoriesParam Categories
     * @return 0 - if user with that email doesnt exists
     *         1 - if user with that email exists
     */
    @RequestMapping(value = "/savingSearch.html", method = RequestMethod.GET)
    @ResponseBody public
    String getSavingSearchRequest(@RequestParam(value = "wordSearch", required = false) final String keyWordsParam,
                      @RequestParam(value = "email", required = false) final String emailParam,
                      @RequestParam(value = "categorySearch", required = false) final String categoriesParam) {

        if (userDao.isExistUserWithEmail(emailParam)) {
            return "auth";
        }
        mailSenderService.sendSearchVariant(emailParam, keyWordsParam, categoriesParam);
        SearchVariant searchVariant = new SearchVariant(emailParam, keyWordsParam, categoriesParam);
        this.searchVariantDao.create(searchVariant);
        return "save";
    }
}