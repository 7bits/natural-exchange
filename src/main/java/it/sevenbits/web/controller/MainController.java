package it.sevenbits.web.controller;

import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.Subscriber;
import it.sevenbits.services.AdvertisementService;
import it.sevenbits.services.CategoryService;
import it.sevenbits.services.SubscriberService;
import it.sevenbits.web.util.SortOrder;
import it.sevenbits.web.util.form.user.MailingNewsForm;
import it.sevenbits.web.util.form.validator.user.MailingNewsValidator;
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

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;

    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("main.jade");

        String[] allCategories = categoryService.findAllCategoriesAsArray();
        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";
        List<Advertisement> advertisements = advertisementService.findAdvertisementsWithCategoryAndKeyWords(allCategories, null, mainSortOrder, sortBy);
        List<Category> categoryList = categoryService.findThreeLastCategories();

        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(advertisementService.DEFAULT_MAIN_ADVERTISEMENTS);
        pageList.setPage(0);

        List<Advertisement> userAdvertisements = advertisementService.findAuthUserAdvertisements();
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
            if (subscriberService.isExists(newSubscriber)) {
                map.put("success", false);
                Map errors = new HashMap();
                errors.put("exist", "Вы уже подписаны.");
                map.put("errors", errors);
            } else {
                subscriberService.create(newSubscriber);
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
