package it.sevenbits.controller.newdesign;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

@Controller
@RequestMapping(value = "new")
public class MainController {
    private static final int MAIN_ADVERTISEMENTS = 4;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("main.jade");
        List<Advertisement> advertisements;
        String[] allCategories = this.getAllCategories();
        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";
        advertisements = this.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(
                allCategories, "", mainSortOrder, sortBy
        );
        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(MAIN_ADVERTISEMENTS);
        pageList.setPage(0);
        modelAndView.addObject("advertisements", pageList.getPageList());
        return modelAndView;
    }

    @RequestMapping(value = "/main.html", method = RequestMethod.POST)
    public ModelAndView subscribe(
            final MailingNewsForm mailingNewsFormParam,
            final BindingResult bindingResult) {
        ModelAndView modelAndView = this.mainPage();

//        modelAndView.addObject("isAnonym", true);
//        modelAndView.addObject("isNotUser", true);
//        modelAndView.addObject("isNotSubscriber", true);
        if (mailingNewsFormParam.getEmailNews() != null) {
            Subscriber newSubscriber = new Subscriber(mailingNewsFormParam.getEmailNews());
            mailingNewsValidator.validate(mailingNewsFormParam, bindingResult);
            if (!bindingResult.hasErrors()) {
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                if (this.subscriberDao.isExists(newSubscriber)) {
                    mailingNewsForm.setEmailNews("Вы уже подписаны.");
                } else {
                    modelAndView.addObject("isNotSubscriber", "true");
                    this.subscriberDao.create(newSubscriber);
                    mailingNewsForm.setEmailNews("Ваш e-mail добавлен.");
                }
                modelAndView.addObject("mailingNewsForm", mailingNewsForm);
            }
        }
        return modelAndView;
    }

    private String[] getAllCategories() {
        List<Category> categories = this.categoryDao.findAll();
        int categoryLength = categories.size();
        String[] allCategories = new String[categoryLength];
        for (int i = 0; i < categoryLength; i++) {
            allCategories[i] = categories.
                    get(i).
                    getName();
        }
        return allCategories;
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

}
