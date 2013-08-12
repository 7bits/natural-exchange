/**
 * Interface which provide methods for working with object Advertisement
 */
package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.User;
import it.sevenbits.util.SortOrder;

import java.util.List;

/**
 * Interface which provide methods for working with object Advertisement
 */
public interface AdvertisementDao {

    /**
     * Create new advertisement
     * @param advertisement - advertisement to add in DB
     */
    Advertisement create(Advertisement advertisement, Category category, User user);

    /**
     * Find advertisement by id
     * @param id  - primary key, id of advertisement in DB
     * @return  advertisement by it's id from DB
     */
    Advertisement findById(Long id);

    /**
     * Find all advertisements
     * @return  list of all advertisement
     */
    List<Advertisement> findAll();

    /**
     *
     * @param sortOrder - of type SortOrder, it shows the order how to sort list of advertisement
     * @param sortPropertyName  - the name of field, used in defining of sortOrder
     * @return sorted list of all advertisement
     */
    List<Advertisement> findAll(final SortOrder sortOrder, final String sortPropertyName);

    /**
     *
     * @param category  given category
     * @param sortOrder  - parameter that defines sort
     * @param sortPropertyName the name of field of table Advertisement
     * @return   sorted list of all advertisement
     */
    List<Advertisement> findAllAdvertisementsWithCategoryAndOrderBy(final String category,
                                                                    final SortOrder sortOrder,
                                                                    final String sortPropertyName);

    /**
     *
     * @param categories  Array of string, that represents categories for sorting
     * @param keyWords    Array of string that we are looking for.
     * @param sortOrder  parameter that defines sort
     * @param sortPropertyName  the name of field of table Advertisement
     * @return sorted list of all advertisement
     */
    List<Advertisement> findAllAdvertisementsWithCategoryAndKeyWordsOrderBy(final String[] categories,
                                                                            final String[] keyWords,
                                                                            final SortOrder sortOrder,
                                                                            final String sortPropertyName);
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
}
