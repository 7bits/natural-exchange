/**
 * Interface which provide methods for working with object Advertisement
 */
package it.sevenbits.repository.dao;

import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Tag;
import it.sevenbits.repository.entity.User;
import it.sevenbits.web.util.SortOrder;

import java.util.List;
import java.util.Set;

/**
 * Interface which provide methods for working with object Advertisement
 */
public interface AdvertisementDao {

    /**
     * Create new advertisement
     * @param advertisement - advertisement to add in DB
     */
    Advertisement create(Advertisement advertisement, String categoryName, String userName, Set<Tag> tags);

    /**
     * Find advertisement by id
     * @param id  - primary key, id of advertisement in DB
     * @return  advertisement by it's id from DB
     */
    Advertisement findById(Long id);

    /**
     *
     * @param categories  Array of string, that represents categories for sorting
     * @param keyWords    Array of string that we are looking for.
     * @param sortOrder  parameter that defines sort
     * @param sortPropertyName  the name of field of table Advertisement
     * @return sorted list of all advertisement
     */
    List<Advertisement> findAdvertisementsWithCategoryAndKeyWords(final String[] categories,//3
                                                                  final String[] keyWords,
                                                                  final SortOrder sortOrder,
                                                                  final String sortPropertyName);

    List<Advertisement> findAdvertisementsWithKeyWordsFilteredByDelete(final String[] keyWords,//2
                                                                       final SortOrder sortOrder,
                                                                       final String sortPropertyName,
                                                                       final Boolean isDeleted,
                                                                       final Long dateFrom,
                                                                       final Long dateTo);

    List<Advertisement> findAdvertisementsWithKeyWordsAndCategoriesFilteredByDate(//1
        final String[] categories,
        final String[] keyWords,
        final Boolean isDeleted,
        final SortOrder sortOrder,
        final String sortPropertyName,
        final Long dateFrom,
        final Long dateTo
    );

    /**
     * Change advertisement
     * @param advertisement advertisement to update
     */
    void update(Advertisement advertisement);

    /**
     * Delete advertisement
     * @param advertisement  to delete
     */
    void delete(Advertisement advertisement);

    void changeDeleted(Long id);

    void update(final Long id, final Advertisement advertisement, final String categoryName, Set<Tag> tags);

    List<Advertisement> findUserAdvertisements(final User user);
}
