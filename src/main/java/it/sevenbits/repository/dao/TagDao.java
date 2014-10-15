package it.sevenbits.repository.dao;

import it.sevenbits.repository.entity.Advertisement;
import it.sevenbits.repository.entity.Tag;
import it.sevenbits.repository.entity.hibernate.TagEntity;

import java.util.List;

public interface TagDao {
    Tag create(Tag tag, Advertisement advertisement);
    Tag findById(int id);
    List<TagEntity> findTagsByAdvertisementId(Long id);
    void deleteTagsByAdvertisementId(Long id);
    void delete(Tag tag);
    void update(int id, String name);
}
