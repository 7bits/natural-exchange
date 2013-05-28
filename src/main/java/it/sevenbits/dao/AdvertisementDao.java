package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;
import it.sevenbits.util.SortOrder;

import java.util.List;

/**
 *Interface, which provide methods for working with object Advertisement
 */
public interface AdvertisementDao {

    /**
     * Create new advertisement
     * @param advertisement
     */
    void create(Advertisement advertisement);

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

    List<Advertisement> findAll(final SortOrder sortType, final String sortPropertyName);

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
