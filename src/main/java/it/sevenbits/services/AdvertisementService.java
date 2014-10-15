package it.sevenbits.services;

import it.sevenbits.repository.dao.AdvertisementDao;
import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.Tag;
import it.sevenbits.repository.entity.User;
import it.sevenbits.services.authentication.AuthService;
import it.sevenbits.web.util.Conversion;
import it.sevenbits.web.util.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementDao advertisementDao;

    public List<Advertisement> findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(final String currentCategory,
        final String keywords, final Long dateFrom, final Long dateTo) {
        SortOrder mainSortOrder = SortOrder.DESCENDING;
        String sortBy = "createdDate";
        return this.advertisementDao.findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(
            Conversion.stringToArray(currentCategory),
            Conversion.stringToArray(keywords),
            false,
            mainSortOrder,
            sortBy,
            dateFrom,
            dateTo
        );
    }

    public List<Advertisement> findAuthUserAdvertisements() {
        User user = AuthService.getUser();
        List<Advertisement> userAdvertisements = new LinkedList<>();
        if (user != null) {
            userAdvertisements = this.advertisementDao.findUserAdvertisements(user);
        }
        return userAdvertisements;
    }

    public Advertisement findAdvertisementById(final Long id) {
        return this.advertisementDao.findById(id);
    }

    public void createAdvertisement(final Advertisement advertisement, final Category category, final List<String> tagList) {
        User user = AuthService.getUser();
        if (user != null) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
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
}
