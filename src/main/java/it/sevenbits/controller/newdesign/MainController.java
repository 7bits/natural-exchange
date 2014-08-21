package it.sevenbits.controller.newdesign;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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
    private UserDao userDao;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Advertisement> userAdvertisements = null;
        if (auth.getPrincipal() instanceof UserDetails) {
            User user = this.userDao.findUserByEmail(auth.getName());
            userAdvertisements = this.advertisementDao.findAllByEmail(user);
        }
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        return modelAndView;
    }

    @RequestMapping(value = "/main.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map subscribe(@ModelAttribute("email") MailingNewsForm form,
                         final BindingResult bindingResult) {
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
