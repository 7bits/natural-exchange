package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Tag;
import it.sevenbits.entity.hibernate.TagEntity;

import java.util.List;

public interface TagDao {
    Tag create(Tag tag, Advertisement advertisement);
    Tag findById(int id);
    List<TagEntity> findTagsByAdvertisementId(Long id);
    void deleteTagsByAdvertisementId(Long id);
    void delete(Tag tag);
    void update(int id, String name);
}
