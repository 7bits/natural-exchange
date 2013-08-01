package it.sevenbits.controller;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.MailingNewsForm;
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
        MailingNewsForm mailingNewsForm = new MailingNewsForm();



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


        String currentColumn = null;
        SortOrder currentSortOrder = null;
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

        if (mailingNewsFormParam != null) {
            //valid
         //   MailingNewsValidator mailingNewsValidator = new MailingNewsValidator();
            mailingNewsValidator.validate(mailingNewsFormParam,bindingResult);
            if (bindingResult.hasErrors()) {
                  return new ModelAndView("advertisement/list");
            } else {
                //TODO add e-mail to Mailng news table
            }

        }
        modelAndView.addObject("mailingNewsForm", mailingNewsForm);

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
    /*

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listSubmit(
            @RequestParam(value = "sortedBy", required = false) String sortByNameParam,
            @RequestParam(value = "sortOrder", required = false) String sortOrderParam,
            @RequestParam(value = "currentPage", required = false) Integer currentPageParam,
            @RequestParam(value = "pageSize", required = false) Integer pageSizeParam)
            throws FileNotFoundException {

        return null;
    }
      */
    /**
     * Gives information about one advertisement by id for display
     *
     * @return jsp-page with advertisement information
     */
    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(value = "id", required = true) Long id,
                             @RequestParam(value = "currentCategory", required = true) String currentCategory) {

        // Создаем вьюшку по list.jsp, которая выведется этим контроллером на
        // экран
        ModelAndView modelAndView = new ModelAndView("advertisement/view");

        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        advertisementSearchingForm.setCategory(currentCategory);
        modelAndView.addObject("advertisementSearchingForm",advertisementSearchingForm);
        modelAndView.addObject("currentCategory",currentCategory);

        Advertisement advertisement = this.advertisementDao.findById(id);
        modelAndView.addObject("advertisement", advertisement);
        List<String> categories = new ArrayList<String>();
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placing() {
        ModelAndView modelAndView = new ModelAndView("advertisement/placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        advertisementPlacingForm.setCategory("clothes");
        modelAndView.addObject("advertisementPlacingForm",
                advertisementPlacingForm);
        return modelAndView;
    }

    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacing(
            AdvertisementPlacingForm advertisementPlacingForm,
            BindingResult result) {
        advertisementPlacingValidator
                .validate(advertisementPlacingForm, result);
        if (result.hasErrors()) {
            return new ModelAndView("advertisement/placing");
        }
        AdvertisementEntity tmp = new AdvertisementEntity();
        tmp.setText(advertisementPlacingForm.getText());
        tmp.setPhotoFile(advertisementPlacingForm.getPhotoFile());
        tmp.setTitle(advertisementPlacingForm.getTitle());
        CategoryEntity categoryEntity = null;
        if(advertisementPlacingForm.getCategory().equals(Category.NAME_CLOTHES)) {
            categoryEntity = new CategoryEntity(Category.NAME_CLOTHES,"very good",460l,461l,false);
            categoryEntity.setId(1l);
        }
        else if (advertisementPlacingForm.getCategory().equals(Category.NAME_NOT_CLOTHES)) {
            categoryEntity = new CategoryEntity(Category.NAME_NOT_CLOTHES,"very good",460l,461l,false);
            categoryEntity.setId(2l);
        }
        tmp.setCategoryEntity(categoryEntity);
        this.advertisementDao.create(tmp);
        return new ModelAndView("advertisement/placingRequest");
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


    /*
    @Autowired
    private MailingNewsValidator mailingNewsValidator;
    @RequestMapping(value = "/list", method = RequestMethod.POST)
        public ModelAndView mailingSubmit(
                @RequestParam(value = "sortedBy", required = false) String sortByNameParam,
                @RequestParam(value = "sortOrder", required = false) String sortOrderParam,
                @RequestParam(value = "currentPage", required = false) Integer currentPageParam,
                @RequestParam(value = "pageSize", required = false) Integer pageSizeParam,
                @RequestParam(value = "currentCategory", required = false) String currentCategoryParam,
                @RequestParam(value = "currentKeyWords", required = false) String keyWordsParam,
                @RequestParam(value = "currentColumn", required = false) String currentColumnParam,
                @RequestParam(value = "currentSortOrder", required = false) SortOrder currentSortOrderParam,
                MailingNewsForm mailingNewsForm, BindingResult result) {

            ModelAndView modelAndView = new ModelAndView("advertisement/list");
            mailingNewsValidator.validate(mailingNewsForm, result);

            modelAndView.addObject("currentPage", currentPageParam);
            modelAndView.addObject("pageSize", pageSizeParam);
            modelAndView.addObject("currentColumn", currentColumnParam );
            modelAndView.addObject("currentSortOrder", currentSortOrderParam);
            modelAndView.addObject("currentKeyWords",keyWordsParam );
            modelAndView.addObject("sortedBy", sortByNameParam);
            modelAndView.addObject("sortOrder", sortOrderParam);
            modelAndView.addObject("currentCategory", currentCategoryParam);
            return modelAndView;
        }

        */

}