package it.sevenbits.controller.newdesign;


import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.dao.CategoryDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.Tag;
import it.sevenbits.util.FileManager;
import it.sevenbits.entity.User;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.TagEntity;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(value = "new/advertisement")
public class AdvertisementListController {
    private static final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubscriberDao subscriberDao;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "currentPage", required = false) final Integer previousPage,
                             @RequestParam(value = "keyWords", required = false) final String previousKeyWords,
                             @RequestParam(value = "currentCategory", required = false) final String previousCategory) {
        ModelAndView modelAndView = new ModelAndView("list.jade");

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

        List<Advertisement> advertisements = this.findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(
                currentCategories, keyWordSearch, mainSortOrder, sortBy
        );
        PagedListHolder<Advertisement> pageList = new PagedListHolder<>();
        pageList.setSource(advertisements);
        pageList.setPageSize(this.DEFAULT_ADVERTISEMENTS_PER_LIST);

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

        return modelAndView;
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
        modelAndView.addObject("tags", tagsSet);
        return modelAndView;
    }

    private Set<TagEntity> getTagsFromAdvertisementById(long id) {
        AdvertisementEntity advertisementEntity = (AdvertisementEntity) this.advertisementDao.findById(id);
        return advertisementEntity.getTags();
    }

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placingAdvertisement(@RequestParam(value = "id", required = false) final Long id) {
        ModelAndView modelAndView = new ModelAndView("placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        advertisementPlacingForm.setCategory("clothes");
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
            final Long editingAdvertisementId
    ) {
        String defaultPhoto = "no_photo.png";
        advertisementPlacingValidator.validate(advertisementPlacingFormParam, result);
        if (result.hasErrors()) {
            return new ModelAndView("placing");
        }
        FileManager fileManager = new FileManager();
        String photo = null;
        if (advertisementPlacingFormParam.getImage() == null && editingAdvertisementId == null) {
            photo = defaultPhoto;
        } else if (!(advertisementPlacingFormParam.getImage() == null)) {
            photo = fileManager.savingFile(advertisementPlacingFormParam.getImage());
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
}
