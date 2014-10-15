package it.sevenbits.web.controller;

import it.sevenbits.repository.dao.AdvertisementDao;
import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.services.parsers.DateParser;
import it.sevenbits.web.util.DatePair;
import it.sevenbits.web.util.Presentation;
import it.sevenbits.web.util.SortOrder;
import it.sevenbits.web.util.UtilsMessage;
import it.sevenbits.web.util.form.advertisement.AdvertisementSearchingForm;
import it.sevenbits.web.util.form.user.SearchUserForm;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementSearchingValidator;
import org.apache.commons.lang.StringUtils;
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
    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;
    private static final int DEFAULT_USERS_PER_LIST = 8;

    private Logger logger = LoggerFactory.getLogger(ModeratorController.class);

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DateParser dateParser;

    @Autowired
    private Presentation presentation;

    @Autowired
    private MailSenderService mailSenderService;

    @RequestMapping(value = "/advertisementList.html", method = RequestMethod.GET)
    public ModelAndView showAllAdvertisements(@RequestParam(value = "currentPage", required = false) final Integer previousPage, @RequestParam(value = "keyWords", required = false) final String previousKeyWords, @RequestParam(value = "isDeleted", required = false) final Boolean previousDeletedMark, @RequestParam(value = "dateFrom", required = false) final String previousDateFrom, @RequestParam(value = "dateTo", required = false) final String previousDateTo, final AdvertisementSearchingForm previousAdvertisementSearchingForm, final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("moderator.jade");

        DatePair datePair = dateParser.takeAndValidateDate(previousDateFrom, previousDateTo, bindingResult, advertisementSearchingValidator);

        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        Boolean isDeleted = false;
        if (previousDeletedMark != null) {
            isDeleted = previousDeletedMark;
        }
        modelAndView.addObject("isDeleted", isDeleted);

        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";

        String keyWordSearch = "";
        if (previousKeyWords != null) {
            keyWordSearch = previousKeyWords;
        }
        modelAndView.addObject("keyWords", keyWordSearch);

        List<Advertisement> advertisements = this.advertisementDao.findAdvertisementsWithKeyWordsFilteredByDelete(stringToKeyWords(keyWordSearch), mainSortOrder, sortBy, isDeleted, dateFrom, dateTo);

        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(DEFAULT_ADVERTISEMENTS_PER_LIST);

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

        return modelAndView;
    }

    @RequestMapping(value = "/DeleteOrRestoreAdvertisement.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public
    @ResponseBody
    String deleteOrRestoreAdvertisement(@RequestParam(value = "advertisementId", required = true) final Long id) {
        this.advertisementDao.changeDeleted(id);
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

        List<User> userList = this.getAllUsersExceptCurrent(keyWords, dateFrom, dateTo, isBanned, mainSortOrder);

        PagedListHolder<User> pageList = new PagedListHolder<>();
        pageList.setSource(userList);
        pageList.setPageSize(DEFAULT_USERS_PER_LIST);

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
        User user = this.userDao.findById(id);
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
        this.userDao.changeBan(id);
        return "redirect:new/moderator/userList.html";
    }

    private List<User> getAllUsersExceptCurrent(String keyWords, Long dateFrom, Long dateTo, boolean isBanned, SortOrder currentSortOrder) {
        List<User> listUsers = this.userDao.findUsersByKeywordsDateAndBan(keyWords, dateFrom, dateTo, isBanned, currentSortOrder);
        User currentUser = AuthService.getUser();
        if (currentUser != null) {
            listUsers.remove(currentUser);
        }
        return listUsers;
    }

    private String[] stringToKeyWords(final String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.split(StringUtils.trim(str));
    }
}
