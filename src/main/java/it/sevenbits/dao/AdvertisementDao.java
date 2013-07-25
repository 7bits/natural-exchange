package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;
import it.sevenbits.util.SortOrder;

import java.util.List;
import java.util.Map;

/**
 *Interface, which provide methods for working with object Advertisement
 */
public interface AdvertisementDao {

    /**
     * Create new advertisement
     * @param advertisement
     */
    Advertisement create(Advertisement advertisement);

    /**
     * Find advertisement by id
     * @param id
     * @return
     */
    Advertisement findById(Long id);

    /**
     * Find all advertisements
     * @return
     */
    List<Advertisement> findAll();

    List<Advertisement> findAll(final SortOrder sortOrder, final String sortPropertyName);

    List<Advertisement> findByNamedQueryAndNamedParam(String queryName, Map<String, Object> queryParams, Integer maxResults);
    /**
     * Change advertisement
     * @param advertisement
     */
    void update(Advertisement advertisement);

    /**
     * Delete advertisement
     * @param advertisement
     */
    void delete(Advertisement advertisement);
}
