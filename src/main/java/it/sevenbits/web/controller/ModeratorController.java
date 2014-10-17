package it.sevenbits.web.controller;

import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.AdvertisementService;
import it.sevenbits.services.UserService;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.services.parsers.DateParser;
import it.sevenbits.services.KeywordsService;
import it.sevenbits.web.util.DatePair;
import it.sevenbits.web.util.Presentation;
import it.sevenbits.web.util.SortOrder;
import it.sevenbits.web.util.UtilsMessage;
import it.sevenbits.web.util.form.advertisement.AdvertisementSearchingForm;
import it.sevenbits.web.util.form.user.SearchUserForm;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementSearchingValidator;
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
@RequestMapping(value = "moderator")
public class ModeratorController {

    private Logger logger = LoggerFactory.getLogger(ModeratorController.class);

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private KeywordsService keywordsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private DateParser dateParser;

    @Autowired
    private Presentation presentation;

    @RequestMapping(value = "/advertisementList.html", method = RequestMethod.GET)
    public ModelAndView showAllAdvertisements(@RequestParam(value = "currentPage", required = false) final Integer previousPage,
        @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
        @RequestParam(value = "isDeleted", required = false) final Boolean previousDeletedMark,
        @RequestParam(value = "dateFrom", required = false) final String previousDateFrom,
        @RequestParam(value = "dateTo", required = false) final String previousDateTo,
        final AdvertisementSearchingForm previousAdvertisementSearchingForm,
        final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("moderator.jade");
        String sortBy = "createdDate";

        DatePair datePair = dateParser.takeAndValidateDate(previousDateFrom, previousDateTo, bindingResult, advertisementSearchingValidator);

        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        Boolean isDeleted = false;
        if (previousDeletedMark != null) {
            isDeleted = previousDeletedMark;
        }

        SortOrder mainSortOrder = SortOrder.DESCENDING;

        String keyWordSearch = "";
        if (previousKeyWords != null) {
            keyWordSearch = previousKeyWords;
        }

        List<Advertisement> advertisements = advertisementService.findAdvertisementsWithKeyWordsFilteredByDelete(keywordsService.stringToKeyWords(keyWordSearch),
            mainSortOrder, sortBy, isDeleted, dateFrom, dateTo);

        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(advertisementService.getDEFAULT_ADVERTISEMENTS_PER_LIST());

        int pageCount = pageList.getPageCount();
        int currentPage;
        if (previousPage == null || previousPage > pageCount) {
            currentPage = 1;
        } else {
            currentPage = previousPage;
        }
        pageList.setPage(currentPage - 1);

        presentation.addPages(modelAndView, currentPage, pageCount);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("pageCount", pageCount);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("dateFrom", previousDateFrom);
        modelAndView.addObject("dateTo", previousDateTo);
        modelAndView.addObject("isDeleted", isDeleted);
        modelAndView.addObject("keyWords", keyWordSearch);

        return modelAndView;
    }

    @RequestMapping(value = "/DeleteOrRestoreAdvertisement.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    String deleteOrRestoreAdvertisement(@RequestParam(value = "advertisementId", required = true) final Long id) {
        advertisementService.changeDeleted(id);
        return "redirect:new/moderator/advertisementList.html";
    }

    @RequestMapping(value = "/userList.html", method = RequestMethod.GET)
    public ModelAndView administrationPage(final SearchUserForm searchUserForm, final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("administrator.jade");
        String keyWords = null;
        if (searchUserForm.getKeyWords() != null) {
            keyWords = searchUserForm.getKeyWords();
        }
        Boolean isBanned = false;
        if (searchUserForm.getIsBanned() != null) {
            isBanned = searchUserForm.getIsBanned();
        }
        Integer currentPage = 1;
        if (searchUserForm.getCurrentPage() != null) {
            currentPage = searchUserForm.getCurrentPage();
        }
        String dateFromString = "";
        if (searchUserForm.getDateFrom() != null) {
            dateFromString = searchUserForm.getDateFrom();
        }
        String dateToString = "";
        if (searchUserForm.getDateTo() != null) {
            dateToString = searchUserForm.getDateTo();
        }

        SortOrder mainSortOrder = SortOrder.DESCENDING;

        DatePair datePair = dateParser.takeAndValidateDate(dateFromString, dateToString, bindingResult, this.advertisementSearchingValidator);
        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        List<User> userList = userService.getAllUsersExceptCurrent(keyWords, dateFrom, dateTo, isBanned, mainSortOrder);

        PagedListHolder<User> pageList = new PagedListHolder<>();
        pageList.setSource(userList);
        pageList.setPageSize(userService.getDEFAULT_USERS_PER_LIST());

        int pageCount = pageList.getPageCount();
        pageList.setPage(currentPage - 1);
        presentation.addPages(modelAndView, currentPage, pageCount);

        modelAndView.addObject("users", pageList.getPageList());
        modelAndView.addObject("pageCount", pageCount);
        modelAndView.addObject("keyWords", keyWords);
        modelAndView.addObject("isBanned", isBanned);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("dateFrom", dateFromString);
        modelAndView.addObject("dateTo", dateToString);
        return modelAndView;
    }

    @RequestMapping(value = "/banOrUnbanUser.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    String BanOrUnbanUser(@RequestParam(value = "userId", required = true) final Long id) {
        User user = userService.findById(id);
        String message;
        String title;
        String userName = AuthService.getUserName(user);
        if (user.getIsBanned()) {
            message = UtilsMessage.createLetterToUnbannedUser(userName);
            title = "Уведомление о снятии бана";
        } else {
            message = UtilsMessage.createLetterToBannedUser(userName);
            title = "Уведомление о бане";
        }
        mailSenderService.sendMail(user.getEmail(), title, message);
        userService.changeBan(id);
        return "redirect:new/moderator/userList.html";
    }
}
