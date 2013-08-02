package it.sevenbits.controller;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.NewsPostingValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        String selectedCategory = advertisementSearchingFormParam.getCategory();
        String currentCategory;
        if(currentCategoryParam == null) {
            advertisementSearchingForm.setCategory("nothing");
            currentCategory = "nothing";
        }
        else {
            if (selectedCategory!=null) currentCategory = selectedCategory;
            else currentCategory = currentCategoryParam;
            advertisementSearchingForm.setCategory(currentCategory);
            advertisementSearchingForm.setKeyWords(advertisementSearchingFormParam.getKeyWords());
        }
        modelAndView.addObject("currentCategory",currentCategory);
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
            advertisements = findAllAdvertisementsWithCategoryAndKeyWords(currentCategory,keyWordsParam);
            advertisementSearchingForm.setKeyWords(keyWordsParam);
            modelAndView.addObject("currentKeyWords",keyWordsParam);
        }
        else {
            if(advertisementSearchingFormParam.getKeyWords()==null || advertisementSearchingFormParam.getKeyWords().equals(""))
            {
                if(advertisementSearchingForm.getCategory().equals("nothing"))
                    advertisements = this.advertisementDao.findAll(currentSortOrder, currentColumn);
                else
                    advertisements = this.advertisementDao.findAllAdvertisementsWithCategoryAndOrderBy(
                            currentCategory,
                            currentSortOrder,
                            currentColumn
                    );
            }
            else {
                modelAndView.addObject("currentKeyWords",advertisementSearchingFormParam.getKeyWords());
                advertisements = findAllAdvertisementsWithCategoryAndKeyWords(
                        currentCategory,
                        advertisementSearchingFormParam.getKeyWords());
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
                modelAndView.addObject("mailingNewsForm",new MailingNewsForm());
            }
        }
        return modelAndView;
    }

    private List<Advertisement> findAllAdvertisementsWithCategoryAndKeyWords(String category,String keyWordsStr) {
        if (category.equals("nothing")) category = null;
        StringTokenizer token = new StringTokenizer(keyWordsStr);
        String[] keyWords = new String[token.countTokens()];
        for(int i=0;i<keyWords.length;i++) {
            keyWords[i] = token.nextToken();
        }
        return this.advertisementDao.findAllAdvertisementsWithCategoryAndKeyWords(category,keyWords);
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
        advertisementSearchingForm.setCategory(currentCategory);
        modelAndView.addObject("advertisementSearchingForm",advertisementSearchingForm);
        modelAndView.addObject("currentCategory",currentCategory);
        modelAndView.addObject("currentId",id);

        Advertisement advertisement = this.advertisementDao.findById(id);
        modelAndView.addObject("advertisement", advertisement);
        if (mailingNewsFormParam.getEmail() != null) {
            mailingNewsValidator.validate(mailingNewsFormParam,bindingResult);
            if (!bindingResult.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                modelAndView.addObject("mailingNewsForm",new MailingNewsForm());
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
        return modelAndView;
    }

    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacing(
            AdvertisementPlacingForm advertisementPlacingFormParam,
            BindingResult result,
            MailingNewsForm mailingNewsFormParam,
            BindingResult mailRes) {
        if(mailingNewsFormParam.getEmail() != null ){
            mailingNewsValidator.validate(mailingNewsFormParam,mailRes);
            ModelAndView mdv = new ModelAndView("advertisement/placing");
            if (!mailRes.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                mdv.addObject("mailingNewsForm",new MailingNewsForm());
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
            AdvertisementEntity tmp = new AdvertisementEntity();
            tmp.setText(advertisementPlacingFormParam.getText());
            tmp.setPhotoFile(advertisementPlacingFormParam.getPhotoFile());
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
}