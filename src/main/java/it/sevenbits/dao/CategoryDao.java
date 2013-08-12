package it.sevenbits.dao;

import it.sevenbits.entity.Category;
import it.sevenbits.entity.hibernate.CategoryEntity;

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

    Category findByName(final String name);

    CategoryEntity findEntityByName(final String name);

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
