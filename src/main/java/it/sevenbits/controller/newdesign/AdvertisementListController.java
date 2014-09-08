package it.sevenbits.controller.newdesign;


import it.sevenbits.entity.*;
import it.sevenbits.dao.*;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.TagEntity;
import it.sevenbits.security.Role;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.DatePair;
import it.sevenbits.util.FileManager;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import it.sevenbits.util.form.ExchangeForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;
import it.sevenbits.util.form.validator.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.ExchangeFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "new/advertisement")
public class AdvertisementListController {
    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;
    private static final long MILLISECONDS_IN_A_DAY = 86400000;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private SearchVariantDao searchVariantDao;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(
        @RequestParam(value = "currentPage", required = false) final Integer previousPage,
        @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
        @RequestParam(value = "currentCategory", required = false) final String previousCategory,
        @RequestParam(value = "dateFrom", required = false) final String previousDateFrom,
        @RequestParam(value = "dateTo", required = false) final String previousDateTo,
        final AdvertisementSearchingForm previousAdvertisementSearchingForm,
        final BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("list.jade");

        DatePair datePair = this.takeAndValidateDate(previousDateFrom, previousDateTo, bindingResult, modelAndView);
        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        String[] currentCategories = this.getAllCategories();
        if (previousCategory != null) {
            currentCategories = this.stringToArray(previousCategory);
        }
        modelAndView.addObject("currentCategory", this.arrayToString(currentCategories));

        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";

        String keyWordSearch = "";
        if (previousKeyWords != null) {
            keyWordSearch = previousKeyWords;
        }
        modelAndView.addObject("keyWords", keyWordSearch);

        List<Advertisement> advertisements = this.advertisementDao.findAllAdvertisementsWithKeyWordsAndCategoryOrderBy(
            this.stringToArray(previousCategory),
            this.stringToArray(previousKeyWords),
            mainSortOrder,
            sortBy,
            dateFrom,
            dateTo
        );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Advertisement> userAdvertisements = new LinkedList<>();
        if (auth.getPrincipal() instanceof UserDetails) {
            User user = this.userDao.findUserByEmail(auth.getName());
            userAdvertisements = this.advertisementDao.findAllByEmail(user);
        }
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
        modelAndView.addObject("userAdvertisements", userAdvertisements);
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

    private Long strDateToUnixTimestamp(String dt) {
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
            this.logger.error("Wrong date format");
            ex.printStackTrace();
        }
        unixtime = date.getTime();
        return unixtime;
    }

    /**
     * Adding pages and previous-next links according current page and amount of all pages.
     * In view it will be #{pageList} with attributes:
     * "first", "second", "third" for correspond pages.
     * Also in touch
     * "next" for next link (true, false)
     * "previous" for previous link (true, false)
     * @param modelAndView
     * @param currentPage
     * @param pageCount
     */
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



    private String[] stringToArray(String str) {
        if (str == null) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(str);
        String[] allCategories = new String[token.countTokens()];
        for (int i = 0; i < allCategories.length; i++) {
            allCategories[i] = token.nextToken();
        }
        return allCategories;
    }

    private String arrayToString(String[] strings) {
        String result = "";
        int length = strings.length;
        for (int i = 0; i < length - 1; i++) {
            result += strings[i] + ' ';
        }
        result += strings[length - 1];
        return result;
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

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map saveSearch(
            @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
            @RequestParam(value = "currentCategory", required = false) final String previousCategory,
            @RequestParam(value = "currentPage", required = false) final Integer previousPage) {
//        String redirectAddress = "redirect:/new/advertisement/list.html?";
//        if (previousPage != null) {
//            redirectAddress += "currentPage=" + previousPage.toString() + "&";
//        }
//        if (previousKeyWords != null) {
//            redirectAddress += "currentPage=" + previousKeyWords + "&";
//        }
//        if (previousKeyWords != null) {
//            redirectAddress += "currentPage=" + previousCategory;
//        }
        Map map = new HashMap<>();
        map.put("currentCategory", previousCategory);
        map.put("currentPage", previousPage);
        map.put("keyWords", previousKeyWords);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            String email = user.getUsername();
            SearchVariant searchVariant = new SearchVariant(email, previousKeyWords, previousCategory);
            searchVariantDao.create(searchVariant);
        }
        return map;
    }

    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView showAdvertisement(@RequestParam(value = "id", required = true) final Long id) {
        ModelAndView modelAndView = new ModelAndView("view.jade");
        Advertisement advertisement = this.advertisementDao.findById(id);
        User user = advertisement.getUser();
        Category category = advertisement.getCategory();
        modelAndView.addObject("fullUserName", user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("advertisement", advertisement);
        modelAndView.addObject("category", category);
        Set<TagEntity> tagsSet = this.getTagsFromAdvertisementById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Advertisement> userAdvertisements = new LinkedList<>();
        if (auth.getPrincipal() instanceof UserDetails) {
            user = this.userDao.findUserByEmail(auth.getName());
            userAdvertisements = this.advertisementDao.findAllByEmail(user);
        }
        modelAndView.addObject("tags", tagsSet);
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        return modelAndView;
    }

    private Set<TagEntity> getTagsFromAdvertisementById(long id) {
        AdvertisementEntity advertisementEntity = (AdvertisementEntity) this.advertisementDao.findById(id);
        return advertisementEntity.getTags();
    }

    private String getTagsFromAdvertisementByIdAsString(long id) {
        AdvertisementEntity advertisement = (AdvertisementEntity) this.advertisementDao.findById(id);
        Set<TagEntity> tags = advertisement.getTags();
        String forTags = "";
        for(TagEntity tag: tags) {
            forTags += tag.getName() + " ";
        }
        return forTags;
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placingAdvertisement(@RequestParam(value = "id", required = false) final Long id) {
        ModelAndView modelAndView = new ModelAndView("placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        if (id != null) {
            Advertisement advertisement = this.advertisementDao.findById(id);
            advertisementPlacingForm.setCategory(advertisement.getCategory().getName());
            advertisementPlacingForm.setText(advertisement.getText());
            advertisementPlacingForm.setTitle(advertisement.getTitle());
        }
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        modelAndView.addObject("isEditing", false);
        return modelAndView;
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id", required = true) final Long advertisementId) {
        ModelAndView modelAndView =  new ModelAndView("placing");
        modelAndView.addObject("isEditing", true);
        modelAndView.addObject("advertisementId", advertisementId);
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        AdvertisementEntity advertisement = (AdvertisementEntity) this.advertisementDao.findById(advertisementId);
        advertisementPlacingForm.setCategory(advertisement.getCategory().getName());
        advertisementPlacingForm.setText(advertisement.getText());
        advertisementPlacingForm.setTitle(advertisement.getTitle());
        advertisementPlacingForm.setTags(getTagsFromAdvertisementByIdAsString(advertisementId));
        modelAndView.addObject("advertisementPlacingForm",advertisementPlacingForm);
        Set<TagEntity> tags = this.getTagsFromAdvertisementById(advertisementId);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("imageUrl", "/resources/images/user_images/" + advertisement.getPhotoFile());
        modelAndView.addObject("imageFileName", advertisement.getPhotoFile());
        return modelAndView;
    }

    /**
     * Placing or editing the advertisement.
     *
     * @param advertisementPlacingFormParam parameters from advertisement form
     * @param result
     * @param editingAdvertisementId        id of advertisement which will
     *                                      be editing
     * @return ModelAndView object
     */
    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacingAdvertisement(
            final AdvertisementPlacingForm advertisementPlacingFormParam,
            final BindingResult result,
            final Long editingAdvertisementId,
            final String advertisementOldImageName
    ) {
        String defaultPhoto = "no_photo.png";
        advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            ModelAndView modelAndView = new ModelAndView("placing");
            modelAndView.addObject("isErrors", true);
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        FileManager fileManager = new FileManager();
        String photo = null;
        if (!(advertisementPlacingFormParam.getImage() == null)) {
            if (advertisementPlacingFormParam.getImage().getOriginalFilename().equals("") && editingAdvertisementId == null) {
                photo = defaultPhoto;
            } else if (!(advertisementPlacingFormParam.getImage().getOriginalFilename().equals("")) && advertisementOldImageName != null) {
                // photo downloaded new and editing, we must delete old photo, but if old photo is default, we don't delete him.
                photo = fileManager.savingFile(advertisementPlacingFormParam.getImage(), true);
                if (advertisementOldImageName.equals("image1.jpg") || advertisementOldImageName.equals("image2.jpg") ||
                        advertisementOldImageName.equals("image3.jpg")) {

                } else {
                    File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + advertisementOldImageName);
                    if (!advertisementOldImageFile.delete()) {
                        //fail
                    }
                }
            } else if (advertisementOldImageName != null) {
                photo = advertisementOldImageName;
            } else {
                photo = fileManager.savingFile(advertisementPlacingFormParam.getImage(), true);
            }
        } else {
            photo = defaultPhoto;
        }
        Advertisement advertisement = null;
        if (editingAdvertisementId == null) {
            advertisement = new Advertisement();
            advertisement.setPhotoFile(photo);
        } else {
            advertisement = advertisementDao.findById(editingAdvertisementId);
            if (photo != null) {
                advertisement.setPhotoFile(photo);
            }
        }
        advertisement.setText(advertisementPlacingFormParam.getText());
        advertisement.setTitle(advertisementPlacingFormParam.getTitle());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {//Alex: что это черт возьми???
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        List<String> tagList = selectTags(advertisementPlacingFormParam.getTags());
        Set<Tag> newTags = null;
        if (tagList != null) {
            newTags = new HashSet<Tag>();
            for (String newTag : tagList) {
                if (!newTag.equals("")) {
                    Tag addingTag = new Tag();
                    addingTag.setName(newTag);
                    newTags.add(addingTag);
                }
            }
        }
        if (editingAdvertisementId != null) {
            //change tags
//                Set<TagEntity> existingTags = this.getTagsFromAdvertisementById(editingAdvertisementId);

            this.advertisementDao.update(editingAdvertisementId, advertisement, advertisementPlacingFormParam.getCategory(), newTags);
        } else {
            this.advertisementDao.create(advertisement, advertisementPlacingFormParam.getCategory(), userName, newTags);
        }
        return new ModelAndView("placingRequest");
    }

    @Autowired
    private ExchangeFormValidator exchangeFormValidator;

    //    We must return object cause in one way we must return string, otherwise ModelAndView
    @RequestMapping(value = "/exchange.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map submitExchange(@ModelAttribute("email") ExchangeForm exchangeForm,
                                 final BindingResult bindingResult) {
        Map map = new HashMap();
        exchangeFormValidator.validate(exchangeForm, bindingResult);
        if (!bindingResult.hasErrors()) {
            Advertisement offerAdvertisement = this.advertisementDao.findById(exchangeForm.getIdExchangeOfferAdvertisement());
            User offer = offerAdvertisement.getUser();
            User owner = this.advertisementDao.findById(exchangeForm.getIdExchangeOwnerAdvertisement()).getUser();
            String advertisementUrl = mailSenderService.getDomen() + "/new/advertisement/view.html?id=";
            String advertisementUrlResidue = "&currentCategory=+clothes+games+notclothes+";
            String titleExchangeMessage = "С вами хотят обменяться!";
            String userName;
            if (owner.getLastName().equals("")) {
                userName = "владелец вещи";
            } else {
                userName = owner.getLastName();
            }
            String message = "Пользователь с email'ом : " + offer.getUsername() +  "\nХочет обменяться с вами на вашу вещь : \n"
                    + advertisementUrl + exchangeForm.getIdExchangeOwnerAdvertisement() + advertisementUrlResidue
                    + "\nИ предлагает вам взамен\n" + advertisementUrl + exchangeForm.getIdExchangeOfferAdvertisement()
                    + advertisementUrlResidue + "\nПрилагается сообщение :\n " + exchangeForm.getExchangePropose()
                    + "\n Уважаемый " + userName + "\nПока что наш сервис находится в разработке, так что мы оставляем за вами " +
                    "право связаться с заинтересованным пользователем на вашу вещь.\n"
                    + "\nЕсли ваш обмен состоится, то, пожалуйста, удалите ваши объявления с нашего сервиса.\n" + "Спасибо!";
            mailSenderService.sendMail(owner.getEmail(), titleExchangeMessage, message);
            map.put("success", true);
        } else {
            map.put("success", false);
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            Map errors = new HashMap();
            errors.put("wrong", errorMessage);
            map.put("errors", errors);
        }
        return map;
    }

    private List<String> selectTags(String tags) {
        if (tags == null) {
            return null;
        }

        StringTokenizer token = new StringTokenizer(tags);
        List<String> saparateTags = new ArrayList<String>();
        int length = token.countTokens();
        for (int i = 0; i < length; i++) {
            saparateTags.add(token.nextToken());
        }
        return saparateTags;
    }

    @RequestMapping(value = "/delete.html", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) final Long advertisementId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails;
        String redirectAddress = "redirect:/advertisement/moderator/list.html" + "?currentCategory=";
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else {
            return redirectAddress;
        }
        User user = this.userDao.findUserByEmail(userDetails.getUsername());
        if (user.getRole().equals("ROLE_MODERATOR")) {
            Advertisement advertisement = this.advertisementDao.findById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            String title = "Ваше объявление удалено модератором";
            String userName;
            if (advertisement.getUser().getLastName().equals("")) {
                userName = "Уважаемый пользователь";
            } else {
                userName = "Уважаемый, " + advertisement.getUser().getLastName();
            }
            String message = userName + "\nВаше объявление с заголовком : " + advertisement.getTitle()
                    + "\nС описанием : " + advertisement.getText() + "\nБыло удалено модератором";
            if(userDetails.getAuthorities().contains(Role.createModeratorRole()) || userDetails.getUsername().equals(userEmail)) {
                this.advertisementDao.setDeleted(advertisementId);
                mailSenderService.sendMail(userEmail, title, message);
            }
        } else {
            redirectAddress = "redirect:/new/advertisement/list.html";
            Advertisement advertisement = this.advertisementDao.findById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            if(userDetails.getAuthorities().contains(Role.createModeratorRole()) || userDetails.getUsername().equals(userEmail)) {
                this.advertisementDao.setDeleted(advertisementId);
            }
        }
        return redirectAddress;
    }
}
