package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.CategoryDao;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.hibernate.CategoryEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CategoryDao implementation
 */
@Repository(value = "categoryDao")
public class CategoryDaoHibernate implements CategoryDao {
    private static final int CATEGORIES_ON_MAIN_PAGE = 3;

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public CategoryDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    private CategoryEntity toEntity(final Category category) {
        return new CategoryEntity(
                category.getSlug(), category.getName(), category.getDescription(), category.getUpdatedDate(),
                category.getCreatedDate(), category.getIsDeleted()
        );
    }

    @Override
    public void create(final Category category) {
        this.hibernateTemplate.save(toEntity(category));
    }

    @Override
    public Category findById(final Long id) {
        return this.hibernateTemplate.get(CategoryEntity.class, id);
    }

    @Override
    public CategoryEntity findEntityBySlug(final String slug) {
        DetachedCriteria criteria = DetachedCriteria.forClass(CategoryEntity.class);
        criteria.add(Restrictions.eq("slug", slug));
        List<CategoryEntity> categories = this.hibernateTemplate.findByCriteria(criteria);
        return categories.get(0);
    }

    @Override
    public Set<CategoryEntity> findBySlugs(String[] slugs) {
        if (slugs == null) {
            return null;
        }
        Set<CategoryEntity> categoryEntities = new HashSet<>();
        for (String slug: slugs) {
            categoryEntities.add(this.findEntityBySlug(slug));
        }
        return categoryEntities;
    }

    @Override
    public int categoryCount() {
        return this.findAll().size();
    }

    @Override
    public List<Category> findAll() {
        DetachedCriteria criteria = DetachedCriteria.forClass(CategoryEntity.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return this.hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    public List<Category> findThreeLastCategories() {
        List<Category> categoryList = findAll();
        while (categoryList.size() > CATEGORIES_ON_MAIN_PAGE) {
            int lastIndex = categoryList.size() - 1;
            categoryList.remove(lastIndex);
        }
        return categoryList;
    }

    @Override
    public void update(final Category category) {
    }

    @Override
    public void delete(final Category category) {
    }

    private List<Category> convertEntityList(final List entities) {
        List<Category> categories = new ArrayList<Category>();
        if (entities != null) {
            List<CategoryEntity> categoryEntityList = (List<CategoryEntity>) entities;
            for (CategoryEntity entity : categoryEntityList) {
                categories.add(entity);
            }
        }
        return categories;
    }

}
