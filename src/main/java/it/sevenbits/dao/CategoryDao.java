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
     * @return Category
     */
    Category findById(Long id);

    Category findBySlug(final String slug);

    Category findByName(final String name);

    CategoryEntity findEntityBySlug(final String name);

    /**
     * Return list of categories
     * @return  list
     */
    List<Category> findAll();

    List<Category> findThreeLastCategories();

    /**
     * Change category
     * @param category to change
     */
    void update(Category category);

    /**
     * Delete category
     * @param category to delete
     */
    void delete(Category category);
}
