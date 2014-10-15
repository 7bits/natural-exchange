package it.sevenbits.web.controller;

import it.sevenbits.repository.dao.AdvertisementDao;
import it.sevenbits.repository.dao.CategoryDao;
import it.sevenbits.repository.dao.SubscriberDao;
import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.Subscriber;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.AdvertisementService;
import it.sevenbits.services.CategoryService;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.parsers.DateParser;
import it.sevenbits.web.util.Presentation;
import it.sevenbits.web.util.SortOrder;
import it.sevenbits.web.util.form.user.MailingNewsForm;
import it.sevenbits.web.util.form.validator.user.MailingNewsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {
    private static final int MAIN_ADVERTISEMENTS = 4;
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("main.jade");
        List<Advertisement> advertisements;
        String[] allCategories = categoryService.findAllCategoriesAsArray();
        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";

        advertisements = this.advertisementDao.findAdvertisementsWithCategoryAndKeyWords(allCategories, null, mainSortOrder, sortBy);

        List<Category> categoryList = categoryDao.findThreeLastCategories();

        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(MAIN_ADVERTISEMENTS);
        pageList.setPage(0);
        List<Advertisement> userAdvertisements = new LinkedList<>();
        User user = AuthService.getUser();
        if (user != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(user);
        }
        modelAndView.addObject("categories", categoryList);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        return modelAndView;
    }

    @RequestMapping(value = "/subscribe.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    Map subscribe(@ModelAttribute("email") MailingNewsForm form, final BindingResult bindingResult) {
        Map map = new HashMap();
        mailingNewsValidator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            Subscriber newSubscriber = new Subscriber(form.getEmailNews());
            if (this.subscriberDao.isExists(newSubscriber)) {
                map.put("success", false);
                Map errors = new HashMap();
                errors.put("exist", "Вы уже подписаны.");
                map.put("errors", errors);
            } else {
                this.subscriberDao.create(newSubscriber);
                map.put("success", true);
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
}
