package it.sevenbits.services;

import it.sevenbits.repository.dao.CategoryDao;
import it.sevenbits.repository.entity.Category;
import it.sevenbits.repository.entity.hibernate.CategoryEntity;
import it.sevenbits.web.util.Conversion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public String findAllCategoriesAsString() {
        List<Category> categories = this.categoryDao.findAll();
        int categoryLength = categories.size();
        String[] allCategories = new String[categoryLength];
        for (int i = 0; i < categoryLength; i++) {
            allCategories[i] = categories.
                    get(i).
                    getSlug();
        }
        return Conversion.arrayToString(allCategories);
    }

    public List<Category> findThreeLastCategories() {
        return this.categoryDao.findThreeLastCategories();
    }

    public String[] findAllCategoriesAsArray() {
        List<Category> categories = this.categoryDao.findAll();
        int categoryLength = categories.size();
        String[] allCategories = new String[categoryLength];
        for (int i = 0; i < categoryLength; i++) {
            allCategories[i] = categories.
                get(i).
                getSlug();
        }
        return allCategories;
    }

    public List<Category> findAllCategories() {
        return this.categoryDao.findAll();
    }

    public Set<CategoryEntity> findByCategory(final String category) {
        String[] categorySlugs = Conversion.stringToArray(category);
        return this.categoryDao.findBySlugs(categorySlugs);
    }

    public String allCategoriesSlug() {
        List<Category> allCategories = findAllCategories();
        String result = "";
        for (Category currentCategory : allCategories) {
            result += currentCategory.getSlug() + " ";
        }
        return StringUtils.trim(result);
    }

    public int categoryCount() {
        return this.categoryDao.categoryCount();
    }

    public Set<CategoryEntity> findBySlugs(final String[] slugs) {
        return this.categoryDao.findBySlugs(slugs);
    }
}
