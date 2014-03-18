package it.sevenbits.dao;

import it.sevenbits.entity.SearchVariant;

import java.util.List;

/**
 *Интерфейс
 */
public interface SearchVariantDao {

    /**
     * create variant of search and add it to DB
     * @param searchVariant  search variant
     */
    void create(SearchVariant searchVariant);

    /**
     *
     * @param searchVariant old searchVar to change
     * @param keyWordsParam new value for field keyWords
     * @param categoriesParam  new value for field categories
     */
    void update(final SearchVariant searchVariant, final String keyWordsParam, final String categoriesParam);

    /**
     *  id
     * @param id - id of entity
     * @return SearchVar of SearchVarEntity(id)
     */
    SearchVariant findById(Long id);

    /**
     * возвращает  список всех подписчиков
     * @return list
     */
    List<SearchVariant> find();

    /**
     * удалить вариант поиска
     * @param searchVariant to delete
     */
    void delete(SearchVariant searchVariant);

    /**
     * Returns the list of all searching variant for user with e-mail
     * @param email  user's e-mail
     * @return list of search..var.s
     */
    List<SearchVariant> findByEmail(final String email);


    void updateAdvertSearch(String email, String oldKeyWordsParam, String oldCategoriesParam, String keyWords, String categories);

    public boolean isExist(final SearchVariant searchVariant);
}
