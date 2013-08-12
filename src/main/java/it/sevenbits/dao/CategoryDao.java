package it.sevenbits.dao;

import it.sevenbits.entity.Category;

import java.util.List;

/**
 *Interface
 */
public interface CategoryDao {

    /**
     * Create category to DB
     * @param category  category
     */
    void create(Category category);

    /**
     *  id
     * @param id -
     * @return
     */
    Category findById(Long id);

    Category findByTitle(final String title);


    /**
     * Return list of categories
     * @return
     */
    List<Category> findAll();

    /**
     * Change category
     * @param category
     */
    void update(Category category);

    /**
     * Delete category
     * @param category
     */
    void delete(Category category);
}
