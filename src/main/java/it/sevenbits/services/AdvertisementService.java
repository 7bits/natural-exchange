package it.sevenbits.services;

import it.sevenbits.repository.dao.AdvertisementDao;
import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Tag;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.web.util.Conversion;
import it.sevenbits.web.util.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class AdvertisementService {
    public final int DEFAULT_MAIN_ADVERTISEMENTS = 4;
    public final int DEFAULT_ADVERTISEMENTS_PER_LIST = 8;

    @Autowired
    private AuthService authService;

    @Autowired
    private AdvertisementDao advertisementDao;

    public List<Advertisement> findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(final String currentCategory, final String keywords, final Long dateFrom, final Long dateTo) {
        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";
        return this.advertisementDao.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(Conversion.stringToArray(currentCategory), Conversion.stringToArray(keywords), false, mainSortOrder, sortBy, dateFrom, dateTo);
    }

    public List<Advertisement> findAdvertisementsWithCategoryAndKeyWords(final String[] categories, final String[] keywords, final SortOrder sortOrder, final String sortPropertyName) {
        return this.advertisementDao.findAdvertisementsWithCategoryAndKeyWords(categories, keywords, sortOrder, sortPropertyName);
    }

    public List<Advertisement> findAuthUserAdvertisements() {
        User user = authService.getUser();
        List<Advertisement> userAdvertisements = new LinkedList<>();
        if (user != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(user);
        }
        return userAdvertisements;
    }

    public List<Advertisement> findAdvertisementsWithKeyWordsFilteredByDelete(final String[] keywords, final SortOrder sortOrder, final String sortPropertyName, final Boolean isDeleted, final Long dateFrom, final Long dateTo) {
        return this.advertisementDao.findAdvertisementsWithKeyWordsFilteredByDelete(keywords, sortOrder, sortPropertyName, isDeleted, dateFrom, dateTo);
    }

    public Advertisement findAdvertisementById(final Long id) {
        return this.advertisementDao.findById(id);
    }

    public void createAdvertisement(final Advertisement advertisement, final String category, final List<String> tagList) {
        String userName = authService.findUserNameFromPrincipal();
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
        this.advertisementDao.create(advertisement, category, userName, newTags);
    }

    public void changeDeleted(final Long id) {
        this.advertisementDao.changeDeleted(id);
    }

    public void updateAdvertisement(final Long id, final Advertisement advertisement, final String category, final List<String> tagList) {
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
        this.advertisementDao.update(id, advertisement, category, newTags);
    }

    public List<Advertisement> findUserAdvertisements(final User user) {
        return this.advertisementDao.findUserAdvertisements(user);
    }
}
