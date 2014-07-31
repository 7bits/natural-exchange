package it.sevenbits.dao;

import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Tag;

import java.util.List;

public interface TagDao {
    Tag create(Tag tag, Advertisement advertisement);
    Tag findById(int id);
    void delete(Tag tag);
    void update(int id, String name);
    void setTagByID(int id, String newName);
    List<Tag> findByName(String name);
}
