package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.CategoryDao;
import it.sevenbits.entity.Category;
import it.sevenbits.entity.hibernate.CategoryEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryDao implementation
 */
@Repository(value = "categoryDao")
public class CategoryDaoHibernate implements CategoryDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public CategoryDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void create(final Category category) {
        CategoryEntity tmp = new CategoryEntity(category);
        this.hibernateTemplate.save(tmp);
    }

    @Override
    public Category findById(final Long id) {
        return  (Category) this.hibernateTemplate.get(CategoryEntity.class, id);
    }

    @Override
    public Category findByName(final String name) {
        return (Category) findEntityByName(name);
    }

    @Override
    public CategoryEntity findEntityByName(final String name) {
        DetachedCriteria criteria = DetachedCriteria.forClass(CategoryEntity.class);
        criteria.add(Restrictions.eq("name", name));
        List<CategoryEntity> categories = this.hibernateTemplate.findByCriteria(criteria);
        return categories.get(0);
    }

    @Override
    public List<Category> findAll() {
        return null;
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
