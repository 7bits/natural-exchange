package it.sevenbits.controller.newdesign;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.UserEntity;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.DatePair;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.UtilsMessage;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.SearchUserForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "moderator")
public class ModeratorController {
    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;
    private static final int DEFAULT_USERS_PER_LIST = 8;
    private static final long MILLISECONDS_IN_A_DAY = 86400000;

    private Logger logger = LoggerFactory.getLogger(ModeratorController.class);

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSenderService mailSenderService;

    @RequestMapping(value = "/advertisementList.html", method = RequestMethod.GET)
    public ModelAndView showAllAdvertisements(
        @RequestParam(value = "currentPage", required = false) final Integer previousPage,
        @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
        @RequestParam(value = "isDeleted", required = false) final Boolean previousDeletedMark,
        @RequestParam(value = "dateFrom", required = false) final String previousDateFrom,
        @RequestParam(value = "dateTo", required = false) final String previousDateTo,
        final AdvertisementSearchingForm previousAdvertisementSearchingForm,
        final BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("moderator.jade");

        DatePair datePair = this.takeAndValidateDate(previousDateFrom, previousDateTo, bindingResult, modelAndView);
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

        List<Advertisement> advertisements = this.advertisementDao.findAdvertisementsWithKeyWordsFilteredByDelete(
                stringToKeyWords(keyWordSearch), mainSortOrder, sortBy, isDeleted, dateFrom, dateTo
        );

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

        this.addPages(modelAndView, currentPage, pageCount);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("pageCount", pageCount);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("dateFrom", previousDateFrom);
        modelAndView.addObject("dateTo", previousDateTo);

        return modelAndView;
    }

    @RequestMapping(value = "/DeleteOrRestoreAdvertisement.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String deleteOrRestoreAdvertisement(
        @RequestParam(value = "advertisementId", required = true) final Long id
    ) {
        this.advertisementDao.changeDeleted(id);
        return "redirect:new/moderator/advertisementList.html";
    }

    @RequestMapping(value = "/userList.html", method = RequestMethod.GET)
    public ModelAndView administrationPage(
        final SearchUserForm searchUserForm,
        final BindingResult bindingResult
    ) {
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

        DatePair datePair = this.takeAndValidateDate(dateFromString, dateToString, bindingResult, modelAndView);
        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        List<User> userList = this.getAllUsersExceptCurrent(keyWords, dateFrom, dateTo, isBanned, mainSortOrder);

        PagedListHolder<User> pageList = new PagedListHolder<>();
        pageList.setSource(userList);
        pageList.setPageSize(DEFAULT_USERS_PER_LIST);

        int pageCount = pageList.getPageCount();
        pageList.setPage(currentPage - 1);
        this.addPages(modelAndView, currentPage, pageCount);

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
    public @ResponseBody String BanOrUnbanUser(@RequestParam(value = "userId", required = true) final Long id) {
        User user = this.userDao.findById(id);
        String message;
        if (user.getIsBanned()) {
            message = "Поздравляем, вы разбанены и можете снова посещать наш сайт со своей учетной записи :-)";
        } else {
            message = "Вы забанены модератором";
        }
        Map<String, String> letter = UtilsMessage.createLetterForBannedUser(
                user.getEmail(), user.getFirstName(), "Уведомление о бане", message);
        mailSenderService.sendMail(letter.get("email"), letter.get("title"), letter.get("text"));
        this.userDao.changeBan(id);
        return "redirect:new/moderator/userList.html";
    }

    private List<User> getAllUsersExceptCurrent(String keyWords, Long dateFrom, Long dateTo,
                                                boolean isBanned, SortOrder currentSortOrder) {
        List<User> listUsers = this.userDao.findUsersByKeywordsDateAndBan(keyWords, dateFrom, dateTo,
                isBanned, currentSortOrder);
        User currentUser = this.getCurrentUser();
        if (currentUser != null) {
            listUsers.remove(currentUser);
        }
        return listUsers;
    }

    private UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserEntity) auth.getPrincipal();
        }
        return null;
    }

    private DatePair takeAndValidateDate(String dateFrom, String dateTo, BindingResult bindingResult,
                                         ModelAndView modelAndView) {
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        if (dateFrom != null) {
            advertisementSearchingForm.setDateFrom(dateFrom);
        } else if (advertisementSearchingForm.getDateFrom() == null) {
            advertisementSearchingForm.setDateFrom("");
        }
        if (dateTo != null) {
            advertisementSearchingForm.setDateTo(dateTo);
        } else if (advertisementSearchingForm.getDateTo() == null) {
            advertisementSearchingForm.setDateTo("");
        }
        this.advertisementSearchingValidator.validate(advertisementSearchingForm, bindingResult);
        String stringDateFrom = advertisementSearchingForm.getDateFrom();
        String stringDateTo = advertisementSearchingForm.getDateTo();
        Long longDateFrom = null;
        Long longDateTo = null;
        if (!bindingResult.hasErrors()) {
            longDateFrom = strDateToUnixTimestamp(stringDateFrom);
            longDateTo = strDateToUnixTimestamp(stringDateTo);
            if (longDateTo != null) {
                longDateTo += MILLISECONDS_IN_A_DAY;
            }
        } else {
            String errorDate = bindingResult
                    .getAllErrors()
                    .get(0)
                    .getDefaultMessage();
            modelAndView.addObject("dateError", errorDate);
        }
        return new DatePair(longDateFrom, longDateTo);
    }

    private Long strDateToUnixTimestamp(String dt) {
        if (dt.equals("")) {
            return null;
        }
        Date date = null;
        DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
        try {
            date = formatter.parse(dt);
        } catch (ParseException ex) {
            this.logger.error("Wrong date format");
            ex.printStackTrace();
        }
        return date.getTime();
    }

    private String[] stringToKeyWords(final String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.split(StringUtils.trim(str));
    }

    private void addPages(ModelAndView modelAndView, int currentPage, int pageCount) {
        Map<String, Integer> pageMap = new HashMap<>();
        int excess = currentPage % 3;
        switch (excess) {
            case 0:
                if (currentPage + 1 > pageCount) {
                    modelAndView.addObject("next", false);
                } else {
                    modelAndView.addObject("next", true);
                    modelAndView.addObject("nextPages", currentPage + 1);
                }
                if (currentPage - 3 <= 0) {
                    modelAndView.addObject("previous", false);
                } else {
                    modelAndView.addObject("previous", true);
                    modelAndView.addObject("previousPages", -5);
                }
                pageMap.put("first", currentPage - 2);
                pageMap.put("second", currentPage - 1);
                pageMap.put("third", currentPage);
                break;
            case 1:
                if (currentPage + 3 > pageCount) {
                    modelAndView.addObject("next", false);
                } else {
                    modelAndView.addObject("next", true);
                    modelAndView.addObject("nextPages", currentPage + 3);
                }
                if (currentPage - 1 <= 0) {
                    modelAndView.addObject("previous", false);
                } else {
                    modelAndView.addObject("previous", true);
                    modelAndView.addObject("previousPages", -3);
                }
                pageMap.put("first", currentPage);
                if (pageCount - currentPage - 1 >= 0) {
                    pageMap.put("second", currentPage + 1);
                } else {
                    pageMap.put("second", null);
                }
                if (pageCount - currentPage - 2 >= 0) {
                    pageMap.put("third", currentPage + 2);
                } else {
                    pageMap.put("third", null);
                }
                break;
            case 2:
                if (currentPage + 2 > pageCount) {
                    modelAndView.addObject("next", false);
                } else {
                    modelAndView.addObject("next", true);
                    modelAndView.addObject("nextPages", currentPage + 2);
                }
                if (currentPage - 2 <= 0) {
                    modelAndView.addObject("previous", false);
                } else {
                    modelAndView.addObject("previous", true);
                    modelAndView.addObject("previousPages", -4);
                }
                pageMap.put("first", currentPage - 1);
                pageMap.put("second", currentPage);
                if (pageCount - currentPage - 1 >= 0) {
                    pageMap.put("third", currentPage + 1);
                } else {
                    pageMap.put("third", null);
                }
                break;
        }
        modelAndView.addObject("pageList", pageMap);
    }
}
