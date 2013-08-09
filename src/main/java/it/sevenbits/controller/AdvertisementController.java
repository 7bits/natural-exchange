package it.sevenbits.controller;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.*;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.NewsPostingValidator;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Annotated spring controller class
 */
@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementController {
    final Logger logger = LoggerFactory
            .getLogger(AdvertisementController.class);

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

    /**
     * Gives information about all advertisements for display
     *
     * @return jsp-page with advertisements information
     * @throws FileNotFoundException
     *             sortDateOrder = true if ascending, false othewise
     *             sortTitleOrder = so on
     */

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(
            @RequestParam(value = "sortedBy", required = false) String sortByNameParam,
            @RequestParam(value = "sortOrder", required = false) String sortOrderParam,
            @RequestParam(value = "currentPage", required = false) Integer currentPageParam,
            @RequestParam(value = "pageSize", required = false) Integer pageSizeParam,
            @RequestParam(value = "currentCategory", required = false) String currentCategoryParam,
            @RequestParam(value = "currentKeyWords", required = false) String keyWordsParam,
            AdvertisementSearchingForm advertisementSearchingFormParam,
            MailingNewsForm mailingNewsFormParam, BindingResult bindingResult)
            throws FileNotFoundException {
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setAll();
       // modelAndView.addObject("currentCategory","kall"/*stringArrayToString(advertisementSearchingForm.getCategories())*/);

        String[] selectedCategories = advertisementSearchingFormParam.getCategories();
        String[] currentCategories;
        //Запуск голой страницы
        if(currentCategoryParam == null) {
            advertisementSearchingForm.setAll();
            currentCategories = advertisementSearchingForm.getCategories();
        }
        //Страница с какимито параметрами
        else {
            //Пришли параметры от формы выбора поиска
            if (selectedCategories!=null) {
                currentCategories = selectedCategories;
            }
            //От какогото другого элемента
            else {
                currentCategories = stringToTokensArray(currentCategoryParam);
            }
            advertisementSearchingForm.setCategories(currentCategories);
            advertisementSearchingForm.setKeyWords(advertisementSearchingFormParam.getKeyWords());
        }
        modelAndView.addObject("currentCategory",stringArrayToString(currentCategories));
        modelAndView.addObject("advertisementSearchingForm",advertisementSearchingForm);
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
            modelAndView.addObject("sortedByTitle",Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle",newSortOrder.toString());
            modelAndView.addObject("sortedByDate",Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate",SortOrder.ASCENDING.toString());
        } else {
            modelAndView.addObject("sortedByTitle",Advertisement.TITLE_COLUMN_CODE);
            modelAndView.addObject("sortOrderTitle",SortOrder.ASCENDING.toString());
            modelAndView.addObject("sortedByDate",Advertisement.CREATED_DATE_COLUMN_CODE);
            modelAndView.addObject("sortOrderDate",newSortOrder.toString());
        }
        List<Advertisement> advertisements;
        if(keyWordsParam != null) {
            advertisements = findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(currentCategories, keyWordsParam, currentSortOrder, currentColumn);
            advertisementSearchingForm.setKeyWords(keyWordsParam);
            modelAndView.addObject("currentKeyWords",keyWordsParam);
        }
        else {
            if(advertisementSearchingFormParam.getKeyWords()==null || advertisementSearchingFormParam.getKeyWords().equals(""))
            {
                if(advertisementSearchingForm.getCategories()==null)
                    advertisements = this.advertisementDao.findAll(currentSortOrder, currentColumn);
                else
                    advertisements = findAllAdvertisementsWithCategoryOrderBy(currentCategories, currentSortOrder, currentColumn);
            }
            else {
                modelAndView.addObject("currentKeyWords",advertisementSearchingFormParam.getKeyWords());
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
        if (pageSizeParam == null)
            pageSize = defaultPageSize();
        else
            pageSize = pageSizeParam.intValue();

        pageList.setPageSize(pageSize);
        int noOfPage = pageList.getPageCount();

        int currentPage;
        if (currentPageParam == null || currentPageParam >= noOfPage)
            currentPage = 0;
        else
            currentPage = currentPageParam.intValue();
        pageList.setPage(currentPage);

        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("defaultPageSize", defaultPageSize());
        modelAndView.addObject("noOfPage", noOfPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentColumn", currentColumn);
        modelAndView.addObject("currentSortOrder", currentSortOrder);
        if (mailingNewsFormParam.getEmail() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam,bindingResult);
            if (!bindingResult.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                modelAndView.addObject("mailingNewsForm",mailingNewsForm);
            }
        }
        return modelAndView;
    }

    private List<Advertisement> findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(String[] categories,
                                                                                    String keyWordsStr,
                                                                                    SortOrder sortOrder,
                                                                                    String sortColumn) {
        //mailSenderService.sendMail(MailSenderService.SERVICE_MAILBOX,"dimaaasik.s@gmail.com","test",categories.toString());
        if(categories.length == 0) return Collections.emptyList();
        StringTokenizer token = new StringTokenizer(keyWordsStr);
        String[] keyWords = new String[token.countTokens()];
        for(int i=0;i<keyWords.length;i++) {
            keyWords[i] = token.nextToken();
        }
        return this.advertisementDao.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(categories, keyWords, sortOrder, sortColumn);
    }

    private List<Advertisement> findAllAdvertisementsWithCategoryOrderBy(String[] categories,
                                                                         SortOrder sortOrder,
                                                                         String sortColumn) {
        if(categories.length == 0) return Collections.emptyList();
        return this.advertisementDao.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(categories,null,sortOrder,sortColumn);
    }

    private String stringArrayToString(String[] src) {
        if(src == null) {
            return null;
        }
        String dest = " ";
        for(int i=0;i<src.length;i++)
            dest+=src[i]+" ";
        return dest;
    }

    private String[] stringToTokensArray(String str) {
        if(str == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str);
        String[] words = new String[token.countTokens()];
        for(int i=0;i<words.length;i++) {
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
    public ModelAndView view(@RequestParam(value = "id", required = true) Long id,
                             @RequestParam(value = "currentCategory", required = true) String currentCategory,
                             MailingNewsForm mailingNewsFormParam,
                             BindingResult bindingResult) {

        // Создаем вьюшку по list.jsp, которая выведется этим контроллером на
        // экран
        ModelAndView modelAndView = new ModelAndView("advertisement/view");

        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setCategories(stringToTokensArray(currentCategory));
        modelAndView.addObject("advertisementSearchingForm",advertisementSearchingForm);
        modelAndView.addObject("currentCategory",currentCategory);
        modelAndView.addObject("currentId",id);

        Advertisement advertisement = this.advertisementDao.findById(id);
        modelAndView.addObject("advertisement", advertisement);
        if (mailingNewsFormParam.getEmail() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam,bindingResult);
            if (!bindingResult.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                modelAndView.addObject("mailingNewsForm",mailingNewsForm);
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
        modelAndView.addObject("advertisementPlacingForm",advertisementPlacingForm);
        modelAndView.addObject("mailingNewsForm",new MailingNewsForm());
        mailSenderService.sendSearchVariants();
        return modelAndView;
    }

    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacing(
            AdvertisementPlacingForm advertisementPlacingFormParam,
            BindingResult result,
            MailingNewsForm mailingNewsFormParam,
            BindingResult mailRes) {
        if(mailingNewsFormParam.getEmail() != null ){

            mailingNewsValidator.validate(mailingNewsFormParam, mailRes);
            ModelAndView mdv = new ModelAndView("advertisement/placing");
            if (!mailRes.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                mdv.addObject("mailingNewsForm",mailingNewsForm);

            }
            AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
            advertisementPlacingForm.setCategory("clothes");
            mdv.addObject("advertisementPlacingForm",advertisementPlacingForm);
            return mdv;
        } else {
            advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
            if (result.hasErrors()) {
                return new ModelAndView("advertisement/placing");
            }
            FileManager fileManager = new FileManager();
            String photo = fileManager.savingFile(advertisementPlacingFormParam.getImage());
            AdvertisementEntity tmp = new AdvertisementEntity();
            tmp.setText(advertisementPlacingFormParam.getText());
            tmp.setPhotoFile(photo);
            tmp.setTitle(advertisementPlacingFormParam.getTitle());
            CategoryEntity categoryEntity = null;
            if(advertisementPlacingFormParam.getCategory().equals(Category.NAME_CLOTHES)) {
                categoryEntity = new CategoryEntity(Category.NAME_CLOTHES,"very good",460l,461l,false);
                categoryEntity.setId(1l);
            }
            else if (advertisementPlacingFormParam.getCategory().equals(Category.NAME_NOT_CLOTHES)) {
                categoryEntity = new CategoryEntity(Category.NAME_NOT_CLOTHES,"very good",460l,461l,false);
                categoryEntity.setId(2l);
            }
            tmp.setCategoryEntity(categoryEntity);
            this.advertisementDao.create(tmp);
        }
        return new ModelAndView("advertisement/placingRequest");
    }

    @RequestMapping(value = "/makeCaptcha.html")
    public ModelAndView makeCap() {
        ModelAndView modelAndView = new ModelAndView("advertisement/makeCaptcha");
        return modelAndView;
    }

    @Autowired
    private NewsPostingValidator newsPostingValidator;


    @RequestMapping(value = "/post.html", method = RequestMethod.GET)
    public ModelAndView postNews() {
        ModelAndView modelAndView = new ModelAndView("advertisement/post");
        modelAndView.addObject("newsPostingForm", new NewsPostingForm());
        return modelAndView;
    }

    @RequestMapping(value = "/post.html", method = RequestMethod.POST)
    public ModelAndView processPostingNews( NewsPostingForm  newsPostingForm, BindingResult result) {

        newsPostingValidator.validate(newsPostingForm , result);
        /*
        if (result.hasErrors()) {
            return new ModelAndView("advertisement/post");
        }
          */
        return new ModelAndView("advertisement/post");
    }

    private int defaultPageSize() {
        Properties prop = new Properties();
        try {
            FileInputStream inStream = new FileInputStream(
                    "D://Julia/Java/workspace/n-exchange/src/main/resources/list.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            return 2;
        }
        return Integer.parseInt(prop.getProperty("list.count"));
    }

    /**
     *
     * @param keyWordsParam
     * @param emailParam
     * @param categoriesParam
     * @return 0 - if user with that email doesnt exists
     *         1 - if user with that email exists
     */
    @RequestMapping(value = "/savingSearch.html", method = RequestMethod.GET)
    public @ResponseBody
    String getSavingSearchRequest(@RequestParam(value = "wordSearch", required = false) String keyWordsParam,
                      @RequestParam(value = "email", required = false) String emailParam,
                      @RequestParam(value = "categorySearch", required = false) String categoriesParam) {
        if(userDao.isExistUserWithEmail(emailParam))
            return "auth";
        SearchVariant searchVariant = new SearchVariant(emailParam,keyWordsParam,categoriesParam);
        this.searchVariantDao.create(searchVariant);
        return "save";
    }
}