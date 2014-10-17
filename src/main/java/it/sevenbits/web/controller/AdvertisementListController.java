package it.sevenbits.web.controller;

import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.User;
import it.sevenbits.repository.entity.hibernate.*;
import it.sevenbits.services.*;
import it.sevenbits.services.parsers.DateParser;
import it.sevenbits.web.helpers.EncodeDecodeHelper;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.web.util.*;
import it.sevenbits.web.util.form.advertisement.AdvertisementEditingForm;
import it.sevenbits.web.util.form.advertisement.AdvertisementPlacingForm;
import it.sevenbits.web.util.form.advertisement.AdvertisementSearchingForm;
import it.sevenbits.web.util.form.advertisement.ExchangeForm;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementEditingValidator;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementPlacingValidator;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementSearchingValidator;
import it.sevenbits.web.util.form.validator.advertisement.ExchangeFormValidator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementListController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private DateParser dateParser;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private SearchVariantService searchVariantService;

    @Autowired
    private TagService tagService;

    @Autowired
    private Presentation presentation;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private AdvertisementSearchingValidator advertisementSearchingValidator;

    @Autowired
    private AdvertisementEditingValidator advertisementEditingValidator;

    @Autowired
    private ExchangeFormValidator exchangeFormValidator;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(
        @Valid final AdvertisementSearchingForm advertisementSearchingForm,
        final BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("list.jade");
        if (bindingResult.hasErrors()) {
            String errorDate = bindingResult.
                    getAllErrors().
                    get(0).
                    getDefaultMessage();
            modelAndView.addObject("dateError", errorDate);
        }
        DatePair datePair = dateParser.takeAndValidateDate(advertisementSearchingForm.getDateFrom(), advertisementSearchingForm.getDateTo(),
            bindingResult, advertisementSearchingValidator);

        Long dateFrom = datePair.getDateFrom();
        Long dateTo = datePair.getDateTo();

        List<Category> categoryList = categoryService.findAllCategories();
        String allCategories = categoryService.findAllCategoriesAsString();

        String currentCategory = allCategories;
        if (advertisementSearchingForm.getCurrentCategory() != null) {
            currentCategory = advertisementSearchingForm.getCurrentCategory();
        }

        List<Advertisement> advertisements = advertisementService.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
            advertisementSearchingForm.getCurrentCategory(), advertisementSearchingForm.getKeyWords(), dateFrom, dateTo);

        String keyWordSearch = "";
        if (advertisementSearchingForm.getKeyWords() != null) {
            keyWordSearch = EncodeDecodeHelper.decode(advertisementSearchingForm.getKeyWords());
        }

        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(advertisementService.getDEFAULT_ADVERTISEMENTS_PER_LIST());

        int pageCount = pageList.getPageCount();
        int currentPage = advertisementSearchingForm.getRealCurrentPage(pageCount);

        pageList.setPage(currentPage - 1);

        List<Advertisement> userAdvertisements = advertisementService.findAuthUserAdvertisements();

        presentation.addPages(modelAndView, currentPage, pageCount);

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
            Set<CategoryEntity> categoryEntities = categoryService.findByCategory(previousCategory);
            searchVariantService.createSearchVariant(searchVariantEntity, categoryEntities);
        }
        return map;
    }

    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView showAdvertisement(@RequestParam(value = "id", required = true) final Long id) {
        ModelAndView modelAndView = new ModelAndView("view.jade");
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        User user = advertisement.getUser();
        Category category = advertisement.getCategory();
        modelAndView.addObject("fullUserName", user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("advertisement", advertisement);
        modelAndView.addObject("category", category);
        Set<TagEntity> tagsSet = tagService.getTagsFromAdvertisementById(id);
        List<Advertisement> userAdvertisements = advertisementService.findAuthUserAdvertisements();
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
            Advertisement advertisement = advertisementService.findAdvertisementById(id);
            advertisementPlacingForm.setCategory(advertisement.getCategory().getSlug());
            advertisementPlacingForm.setText(advertisement.getText());
            advertisementPlacingForm.setTitle(advertisement.getTitle());
        }
        List<Category> categories = categoryService.findAllCategories();
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "id", required = true) final Long advertisementId) {
        AdvertisementEntity advertisement = (AdvertisementEntity) advertisementService.findAdvertisementById(advertisementId);
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
        advertisementEditingForm.setTags(tagService.getTagsFromAdvertisementByIdAsString(advertisementId));
        advertisementEditingForm.setAdvertisementId(advertisementId);
        modelAndView.addObject("advertisementEditingForm", advertisementEditingForm);
        List<Category> categories = categoryService.findAllCategories();
        Set<TagEntity> tags = tagService.getTagsFromAdvertisementById(advertisementId);
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
            List<Category> categories = categoryService.findAllCategories();
            modelAndView.addObject("categories", categories);
            return modelAndView;
        }
        String photo = photoService.validateAndSavePhoto(advertisementPlacingFormParam.getImage());
        Advertisement advertisement = new Advertisement();
        advertisement.setPhotoFile(photo);
        advertisement.setText(advertisementPlacingFormParam.getText());
        advertisement.setTitle(advertisementPlacingFormParam.getTitle());
        List<String> tagList = tagService.selectTags(advertisementPlacingFormParam.getTags());

        advertisementService.createAdvertisement(advertisement, advertisementPlacingFormParam.getCategory(), tagList);
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
            List<Category> categories = categoryService.findAllCategories();
            modelAndView.addObject("tags", advertisementEditingFormParam.getTags());
            modelAndView.addObject("advertisementPhotoName", advertisementService.findAdvertisementById(advertisementEditingFormParam.getAdvertisementId()).getPhotoFile());
            modelAndView.addObject("categories", categories);
            return modelAndView;
        }

        Long editingAdvertisementId = advertisementEditingFormParam.getAdvertisementId();
        String advertisementOldImageName = advertisementService.findAdvertisementById(editingAdvertisementId).getPhotoFile();
        String photo = null;

        if (isDeletePhoto) {
            photoService.deletePhoto(advertisementOldImageName);
            photo = photoService.DEFAULT_PHOTO;
        } else {
            photo = photoService.validateAndSavePhotoWhenEditing(advertisementEditingFormParam.getImage(), advertisementOldImageName);
        }
        Advertisement advertisement = advertisementService.findAdvertisementById(editingAdvertisementId);
        advertisement.setPhotoFile(photo);
        advertisement.setText(advertisementEditingFormParam.getText());
        advertisement.setTitle(advertisementEditingFormParam.getTitle());

        List<String> tagList = tagService.selectTags(advertisementEditingFormParam.getTags());
        advertisementService.updateAdvertisement(editingAdvertisementId, advertisement, advertisementEditingFormParam.getCategory(), tagList);
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
            User owner = advertisementService.findAdvertisementById(exchangeForm.getIdExchangeOwnerAdvertisement()).getUser();
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
            Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            String title;
            String userName;
            String moderAction;
            if (advertisement.getUser().getLastName().equals("")) {
                userName = "Уважаемый пользователь";
            } else {
                userName = "Уважаемый, " + advertisement.getUser().getLastName();
            }

            if (advertisementService.findAdvertisementById(advertisementId).getIs_deleted()) {
                title = "Ваше предложение восстановлено";
                moderAction = "Было восстановлено. Теперь его снова можно увидеть на списке предложений";
            } else {
                title = "Ваше предложение удалено модератором";
                moderAction = "Было удалено модератором";
            }
            Map<String, String> letter = UtilsMessage.createLetterToUserFromModerator(advertisement.getTitle(),
                    userEmail, moderAction, advertisement.getText(), userName, title);
            advertisementService.changeDeleted(advertisementId);
            mailSenderService.sendMail(letter.get("email"), letter.get("title"), letter.get("text"));
            redirectAttributes.addFlashAttribute("deleteAdvertisementMessage", "Вы удалили предложение");
        } else {
            redirectAddress = "redirect:/advertisement/list.html";
            Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
            String userEmail = advertisement.getUser().getEmail();
            if(AuthService.getUserDetails().getUsername().equals(userEmail)) {
                advertisementService.changeDeleted(advertisementId);
                redirectAttributes.addFlashAttribute("deleteAdvertisementMessage", "Ваше предложение удалено");
            }
        }
        return redirectAddress;
    }
}
