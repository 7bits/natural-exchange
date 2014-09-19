package it.sevenbits.dao;

import it.sevenbits.entity.Category;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.hibernate.CategoryEntity;
import it.sevenbits.entity.hibernate.SearchVariantEntity;

import java.util.List;
import java.util.Set;

/**
 *Интерфейс
 */
public interface SearchVariantDao {

    /**
     * create variant of search, assign to it categories and add it to DB
     * @param searchVariant  search variant
     */
    void create(SearchVariant searchVariant, final Set<CategoryEntity> categories);

    /**
     *
     * @param searchVariant old searchVar to change
     * @param keyWordsParam new value for field keyWords
     * @param categories  new value for field categories
     */
    void update(final SearchVariantEntity searchVariant, final String keyWordsParam, final Set<CategoryEntity> categories);

    /**
     *  id
     * @param id - id of entity
     * @return SearchVar of SearchVarEntity(id)
     */
    SearchVariantEntity findById(Long id);

    /**
     * возвращает  список всех подписчиков
     * @return list
     */
    List<SearchVariant> find();

    /**
     * удалить вариант поиска
     * @param searchVariant to delete
     */
    void delete(SearchVariantEntity searchVariant);

    /**
     * Returns the list of all searching variant for user with e-mail
     * @param email  user's e-mail
     * @return list of search..var.s
     */
    List<SearchVariantEntity> findByEmail(final String email);


    void updateAdvertSearch(String email, String oldKeyWordsParam, String oldCategoriesParam, String keyWords, String categories);

    public boolean isExist(final SearchVariant searchVariant);
}
