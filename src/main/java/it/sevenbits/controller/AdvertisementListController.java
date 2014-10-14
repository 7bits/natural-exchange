package it.sevenbits.controller;


import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.Tag;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.*;
import it.sevenbits.helpers.EncodeDecodeHelper;
import it.sevenbits.services.advertisements.AdvertisementOperations;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.util.*;
import it.sevenbits.util.form.advertisement.AdvertisementEditingForm;
import it.sevenbits.util.form.advertisement.AdvertisementPlacingForm;
import it.sevenbits.util.form.advertisement.AdvertisementSearchingForm;
import it.sevenbits.util.form.advertisement.ExchangeForm;
import it.sevenbits.util.form.validator.advertisement.AdvertisementEditingValidator;
import it.sevenbits.util.form.validator.advertisement.AdvertisementPlacingValidator;
import it.sevenbits.util.form.validator.advertisement.AdvertisementSearchingValidator;
import it.sevenbits.util.form.validator.advertisement.ExchangeFormValidator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementListController {
    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private AdvertisementEditingValidator advertisementEditingValidator;

    @Autowired
    private SearchVariantDao searchVariantDao;

    @Autowired
    private ExchangeFormValidator exchangeFormValidator;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(
        final AdvertisementSearchingForm advertisementSearchingForm,
        final BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("list.jade");
        DatePair datePair = AdvertisementOperations.takeAndValidateDate(advertisementSearchingForm.getDateFrom(), advertisementSearchingForm.getDateTo(),
            bindingResult, modelAndView, this.advertisementSearchingValidator);
        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        List<Category> categoryList = categoryDao.findAll();
        String allCategories = Conversion.arrayToString(getAllCategories());
        String currentCategory = allCategories;
        if (advertisementSearchingForm.getCurrentCategory() != null) {
            currentCategory = advertisementSearchingForm.getCurrentCategory();
        }

        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";

        String keyWordSearch = "";
        if (advertisementSearchingForm.getKeyWords() != null) {
            keyWordSearch = EncodeDecodeHelper.decode(advertisementSearchingForm.getKeyWords());
        }

        List<Advertisement> advertisements = this.advertisementDao.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
            Conversion.stringToArray(advertisementSearchingForm.getCurrentCategory()),
            Conversion.stringToArray(advertisementSearchingForm.getKeyWords()),
            false,
            mainSortOrder,
            sortBy,
            dateFrom,
            dateTo
        );
        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(DEFAULT_ADVERTISEMENTS_PER_LIST);
        List<Advertisement> userAdvertisements = new LinkedList<>();
        User user = AuthService.getUser();
        if (user != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(user);
        }
        int pageCount = pageList.getPageCount();
        int currentPage;
        if (advertisementSearchingForm.getCurrentPage() == null || advertisementSearchingForm.getCurrentPage() > pageCount
            || advertisementSearchingForm.getCurrentPage() == 0) {
            currentPage = 1;
        } else {
            currentPage = advertisementSearchingForm.getCurrentPage();
        }
        pageList.setPage(currentPage - 1);
        AdvertisementOperations.addPages(modelAndView, currentPage, pageCount);

        modelAndView.addObject("allCategories", allCategories);
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        modelAndView.addObject("currentCategory", currentCategory);
        modelAndView.addObject("categories", categoryList);
        modelAndView.addObject("keyWords", keyWordSearch);
        modelAndView.addObject("advertisements", pageList.getPageList());
        modelAndView.addObject("pageCount", pageCount);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("dateFrom", advertisementSearchingForm.getDateFrom());
        modelAndView.addObject("dateTo", advertisementSearchingForm.getDateTo());

        return modelAndView;
    }

    @RequestMapping(value = "/saveSearch.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map saveSearch(
        @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
        @RequestParam(value = "currentCategory", required = false) final String previousCategory,
        @RequestParam(value = "currentPage", required = false) final Integer previousPage
    ) {
        Map map = new HashMap<>();
        if (previousKeyWords.length() > 20) {
            map.put("keywordTooLong", "Недопустимо больше 20 символов.");
            return map;
        }
        map.put("currentCategory", previousCategory);
        map.put("currentPage", previousPage);
        map.put("keyWords", previousKeyWords);
        User user = AuthService.getUser();
        if (user != null) {
            String email = user.getUsername();
            SearchVariantEntity searchVariantEntity = new SearchVariantEntity(email, StringUtils.trim(previousKeyWords), null);
            String[] categorySlugs = Conversion.stringToArray(previousCategory);
            Set<CategoryEntity> categoryEntities = this.categoryDao.findBySlugs(categorySlugs);
            searchVariantDao.create(searchVariantEntity, categoryEntities);
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
        List<Advertisement> userAdvertisements = new LinkedList<>();
        User currentUser = AuthService.getUser();
        if (currentUser != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(user);
        }
        modelAndView.addObject("tags", tagsSet);
        modelAndView.addObject("userAdvertisements", userAdvertisements);
        return modelAndView;
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placingAdvertisement(@RequestParam(value = "id", required = false) final Long id) {
        if (AuthService.getUser() == null) {
            return new ModelAndView("redirect:/main.html");
        }
        ModelAndView modelAndView = new ModelAndView("placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        if (id != null) {
            Advertisement advertisement = this.advertisementDao.findById(id);
            advertisementPlacingForm.setCategory(advertisement.getCategory().getSlug());
            advertisementPlacingForm.setText(advertisement.getText());
            advertisementPlacingForm.setTitle(advertisement.getTitle());
        }
        List<Category> categories =  this.categoryDao.findAll();
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id", required = true) final Long advertisementId) {
        AdvertisementEntity advertisement = (AdvertisementEntity) this.advertisementDao.findById(advertisementId);
        String userEmail = advertisement.getUser().getEmail();
        User user = AuthService.getUser();
        if (user != null) {
            if (!user.getEmail().equals(userEmail)) {
                return new ModelAndView("redirect:/main.html");
            }
        }
        ModelAndView modelAndView =  new ModelAndView("edit");
        AdvertisementEditingForm advertisementEditingForm = new AdvertisementEditingForm();
        advertisementEditingForm.setCategory(advertisement.getCategory().getSlug());
        advertisementEditingForm.setText(advertisement.getText());
        advertisementEditingForm.setTitle(advertisement.getTitle());
        advertisementEditingForm.setTags(getTagsFromAdvertisementByIdAsString(advertisementId));
        advertisementEditingForm.setAdvertisementId(advertisementId);
        modelAndView.addObject("advertisementEditingForm", advertisementEditingForm);
        List<Category> categories = this.categoryDao.findAll();
        Set<TagEntity> tags = this.getTagsFromAdvertisementById(advertisementId);
        List<ObjectError> errors = new ArrayList<>();
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("advertisementPhotoName", advertisement.getPhotoFile());
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("errors", errors);
        return modelAndView;
    }

    /**
     * Placing or editing the advertisement.
     *
     * @param advertisementPlacingFormParam parameters from advertisement form
     * @param result
     * @return ModelAndView object
     */
    @RequestMapping(value = "/placing.html", method = RequestMethod.POST)
    public ModelAndView processPlacingAdvertisement(
        final AdvertisementPlacingForm advertisementPlacingFormParam,
        final BindingResult result
    ) {
        String defaultPhoto = "no_photo.png";
        advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("placing");
            Map<String, String> errorMessages = ErrorMessages.getFieldsErrorMessages(result);
            modelAndView.addObject("imageError", errorMessages.get("image"));
            modelAndView.addObject("titleError", errorMessages.get("title"));
            modelAndView.addObject("categoryError", errorMessages.get("category"));
            modelAndView.addObject("textError", errorMessages.get("text"));
            modelAndView.addObject("tagsError", errorMessages.get("tags"));
            modelAndView.addObject("advertisementPlacingForm", advertisementPlacingFormParam);
            modelAndView.addObject("tags", StringUtils.split(advertisementPlacingFormParam.getTags()));
            List<Category> categories = this.categoryDao.findAll();
            modelAndView.addObject("categories", categories);
            return modelAndView;
        }
        FileManager fileManager = new FileManager();
        String photo = null;
        if (advertisementPlacingFormParam.getImage().getOriginalFilename().equals("")) {
            photo = defaultPhoto;
        } else {
            photo = fileManager.savePhotoFile(advertisementPlacingFormParam.getImage(), true);
        }
        Advertisement advertisement = new Advertisement();
        advertisement.setPhotoFile(photo);
        advertisement.setText(advertisementPlacingFormParam.getText());
        advertisement.setTitle(advertisementPlacingFormParam.getTitle());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        User user = AuthService.getUser();
        if (user != null) {
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
        this.advertisementDao.create(advertisement, advertisementPlacingFormParam.getCategory(), userName, newTags);
        return new ModelAndView("redirect:/advertisement/list.html");
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public ModelAndView processEditingAdvertisement(
        final AdvertisementEditingForm advertisementEditingFormParam,
        final BindingResult result,
        final boolean isDeletePhoto
    ) {
        advertisementEditingValidator.validate(advertisementEditingFormParam, result);
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("advertisementEditingForm", advertisementEditingFormParam);
            Map<String, String> errorMessages = ErrorMessages.getFieldsErrorMessages(result);
            modelAndView.addObject("imageError", errorMessages.get("image"));
            modelAndView.addObject("titleError", errorMessages.get("title"));
            modelAndView.addObject("categoryError", errorMessages.get("category"));
            modelAndView.addObject("textError", errorMessages.get("text"));
            modelAndView.addObject("tagsError", errorMessages.get("tags"));
            List<Category> categories = this.categoryDao.findAll();
            modelAndView.addObject("tags", advertisementEditingFormParam.getTags());
            modelAndView.addObject("advertisementPhotoName", this.advertisementDao.findById(advertisementEditingFormParam.getAdvertisementId()).getPhotoFile());
            modelAndView.addObject("categories", categories);
            return modelAndView;
        }

        String defaultPhoto = "no_photo.png";
        Long editingAdvertisementId = advertisementEditingFormParam.getAdvertisementId();
        String advertisementOldImageName = this.advertisementDao.findById(editingAdvertisementId).getPhotoFile();
        FileManager fileManager = new FileManager();
        String photo = null;

        if (isDeletePhoto) {
            if (advertisementOldImageName.equals("image1.jpg") || advertisementOldImageName.equals("image2.jpg") ||
                    advertisementOldImageName.equals("image3.jpg") || advertisementOldImageName.equals("no_photo.png")) {
            } else {
                File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + advertisementOldImageName);
                if (!advertisementOldImageFile.delete()) {
                    logger.info("file " + advertisementOldImageName + " has been deleted");
                }
            }
            photo = defaultPhoto;
        } else if (advertisementEditingFormParam.getImage().getOriginalFilename().equals("")) {
            photo = advertisementOldImageName;
        } else {
            photo = fileManager.savePhotoFile(advertisementEditingFormParam.getImage(), true);
            if (advertisementOldImageName.equals("image1.jpg") || advertisementOldImageName.equals("image2.jpg") ||
                    advertisementOldImageName.equals("image3.jpg") || advertisementOldImageName.equals("no_photo.png")) {
            } else {
                File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + advertisementOldImageName);
                if (!advertisementOldImageFile.delete()) {
                    logger.info("file " + advertisementOldImageName + " has been deleted");
                }
            }
        }
        Advertisement advertisement = advertisementDao.findById(editingAdvertisementId);
        if (photo != null) {
            advertisement.setPhotoFile(photo);
        }
        advertisement.setText(advertisementEditingFormParam.getText());
        advertisement.setTitle(advertisementEditingFormParam.getTitle());

        List<String> tagList = selectTags(advertisementEditingFormParam.getTags());
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
        this.advertisementDao.update(editingAdvertisementId, advertisement, advertisementEditingFormParam.getCategory(), newTags);
        return new ModelAndView("redirect:/advertisement/view.html?id=" + advertisementEditingFormParam.getAdvertisementId());
    }

    @RequestMapping(value = "/exchange.html", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map submitExchange(
        @ModelAttribute("email") ExchangeForm exchangeForm,
        final BindingResult bindingResult
    ) {
        Map map = new HashMap();
        exchangeFormValidator.validate(exchangeForm, bindingResult);
        if (!bindingResult.hasErrors()) {
            User offer = AuthService.getUser();
            User owner = this.advertisementDao.findById(exchangeForm.getIdExchangeOwnerAdvertisement()).getUser();
            String advertisementUrl = mailSenderService.getDomen() + "/advertisement/view.html?id=";
            String titleExchangeMessage = "Уведомление об обмене";
            String ownerName;
            String offerName;
            StringBuilder advertisementUrlOwner = new StringBuilder(advertisementUrl + exchangeForm.getIdExchangeOwnerAdvertisement());
            StringBuilder advertisementUrlOffer = new StringBuilder(advertisementUrl + exchangeForm.getIdExchangeOfferAdvertisement());
            ownerName = AuthService.getUserName(owner);
            offerName = AuthService.getUserName(offer);
            Map<String, String> letterToOwner = UtilsMessage.createLetterForExchange(
                titleExchangeMessage, exchangeForm.getExchangePropose(), owner.getEmail(), offer.getUsername(),
                advertisementUrlOwner.toString(), advertisementUrlOffer.toString(), ownerName, offerName
            );
            mailSenderService.sendMail(letterToOwner.get("email"), letterToOwner.get("title"), letterToOwner.get("text"));
            logger.info("email about exchange sending to " + letterToOwner.get("email"));
            map.put("success", true);
        } else {
            Map<String, String> errorMessages = ErrorMessages.getFieldsErrorMessages(bindingResult);
            map.put("success", false);
            map.put("errors", errorMessages);
        }
        return map;
    }

    @RequestMapping(value = "/delete.html", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) final Long advertisementId,
            final RedirectAttributes redirectAttributes) {
        String redirectAddress = "redirect:/moderator/advertisementList.html";
        User user = AuthService.getUser();
        if (user == null) {
            return redirectAddress;
        }
        if (user.getRole().equals("ROLE_MODERATOR") || user.getRole().equals("ROLE_ADMIN")) {
            Advertisement advertisement = this.advertisementDao.findById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            String title;
            String userName;
            String moderAction;
            if (advertisement.getUser().getLastName().equals("")) {
                userName = "Уважаемый пользователь";
            } else {
                userName = "Уважаемый, " + advertisement.getUser().getLastName();
            }

            if (this.advertisementDao.findById(advertisementId).getIs_deleted()) {
                title = "Ваше предложение восстановлено";
                moderAction = "Было восстановлено. Теперь его снова можно увидеть на списке предложений";
            } else {
                title = "Ваше предложение удалено модератором";
                moderAction = "Было удалено модератором";
            }
            Map<String, String> letter = UtilsMessage.createLetterToUserFromModerator(advertisement.getTitle(),
                    userEmail, moderAction, advertisement.getText(), userName, title);
            this.advertisementDao.changeDeleted(advertisementId);
            mailSenderService.sendMail(letter.get("email"), letter.get("title"), letter.get("text"));
            redirectAttributes.addFlashAttribute("deleteAdvertisementMessage", "Вы удалили предложение");
        } else {
            redirectAddress = "redirect:/advertisement/list.html";
            Advertisement advertisement = this.advertisementDao.findById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            if(AuthService.getUserDetails().getUsername().equals(userEmail)) {
                this.advertisementDao.changeDeleted(advertisementId);
                redirectAttributes.addFlashAttribute("deleteAdvertisementMessage", "Ваше предложение удалено");
            }
        }
        return redirectAddress;
    }

    private String[] getAllCategories() {
        List<Category> categories = this.categoryDao.findAll();
        int categoryLength = categories.size();
        String[] allCategories = new String[categoryLength];
        for (int i = 0; i < categoryLength; i++) {
            allCategories[i] = categories.
                    get(i).
                    getSlug();
        }
        return allCategories;
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



    private List<String> selectTags(String tags) {
        if (tags == null) {
            return null;
        }
        String trimString = StringUtils.trim(tags);
        String[] separateTags = StringUtils.split(trimString);
        List<String> result = new ArrayList<>();
        for (String str: separateTags) {
            result.add(str);
        }
        return result;
    }
}
