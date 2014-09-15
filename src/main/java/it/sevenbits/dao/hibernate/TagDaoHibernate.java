package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.TagDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Tag;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.TagEntity;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

public class TagDaoHibernate implements TagDao {

    private HibernateTemplate hibernateTemplate;

    @Override
    public Tag create(Tag tag, Advertisement advertisement) {
        AdvertisementEntity advertisementEntity = this.advertisementToEntity(advertisement);
        TagEntity tagEntity = toEntity(tag, advertisementEntity);
        tagEntity = this.hibernateTemplate.merge(tagEntity);
        return tagEntity;
    }

    private AdvertisementEntity advertisementToEntity(Advertisement advertisement) {
        return null;
    }

    private TagEntity toEntity(Tag tag, AdvertisementEntity advertisement) {
        TagEntity tmp = new TagEntity();
        tmp.setName(tag.getName());
        tmp.setAdvertisement(advertisement);
        return tmp;
    }

    @Override
    public Tag findById(final int id) {
        return this.hibernateTemplate.get(TagEntity.class, (long) id);
    }


    @Override
    public void delete(Tag tag) {
    //In the future
    }

    @Override
    public void update(int id, String name) {

    }
}
