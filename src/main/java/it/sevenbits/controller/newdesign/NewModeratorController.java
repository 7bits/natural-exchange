package it.sevenbits.controller.newdesign;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.util.DatePair;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "new/moderator")
public class NewModeratorController {

    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;
    private static final long MILLISECONDS_IN_A_DAY = 86400000;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

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

        List<Advertisement> advertisements = this.advertisementDao.findAllAdvertisementsWithKeyWordsOrderBy(
                stringToTokensArray(keyWordSearch), mainSortOrder, sortBy, isDeleted, dateFrom, dateTo);

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
        //it made for null dates
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
            String errorDate = bindingResult.
                               getAllErrors().
                               get(0).
                               getDefaultMessage();
            modelAndView.addObject("dateError", errorDate);
        }
        return new DatePair(longDateFrom, longDateTo);
    }

    private static Long strDateToUnixTimestamp(String dt) {
        if (dt.equals("")) {
            return null;
        }
        DateFormat formatter;
        Date date = null;
        long unixtime;
        formatter = new SimpleDateFormat("dd.MM.yy");
        try {
            date = formatter.parse(dt);
        } catch (ParseException ex) {
            //Bad
            ex.printStackTrace();
        }
        unixtime = date.getTime();
        return unixtime;
    }

    private String[] stringToTokensArray(final String str) {
        if (str == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str);
        String[] words = new String[token.countTokens()];
        for (int i = 0 ; i < words.length ; i++) {
            words[i] = token.nextToken();
        }
        return words;
    }

    private List<Advertisement> findAllAdvertisementsWithDeleteCtirerionAndKeyWordsOrderBy(
            final Boolean isDeleted, final String keyWordsStr, final SortOrder sortOrder, final String sortColumn
    ) {
        StringTokenizer token = new StringTokenizer(keyWordsStr);
        String[] keyWords = new String[token.countTokens()];
        for (int i = 0; i < keyWords.length; i++) {
            keyWords[i] = token.nextToken();
        }
        return this.advertisementDao.findAllAdvertisementsWithKeyWordsOrderBy(keyWords, sortOrder, sortColumn,
            isDeleted, null, null);
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

    @RequestMapping(value = "/advertisementList.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String deleteOrRestoreAdvertisement(
        @RequestParam(value = "advertisementId", required = true) final Long id
    ) {
        String redirectAddress = "redirect:new/moderator/advertisementList.html";
        this.advertisementDao.setDeleted(id);
        return redirectAddress;
    }
}
