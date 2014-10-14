package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.TagDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.Tag;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.entity.hibernate.TagEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository(value = "tagDao")
public class TagDaoHibernate implements TagDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public TagDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

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
    public List<TagEntity> findTagsByAdvertisementId(Long id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TagEntity.class);
        criteria.add(Restrictions.eq("advertisement_id", id));
        List<TagEntity> tagEntityList = this.hibernateTemplate.findByCriteria(criteria);
        return tagEntityList;
    }

    @Override
    public void deleteTagsByAdvertisementId(Long id) {
        Set<TagEntity> deletingTags = this.hibernateTemplate.get(AdvertisementEntity.class, id).getTags();
        for (TagEntity tag : deletingTags) {
            this.hibernateTemplate.delete(tag);
        }
    }


    @Override
    public void delete(Tag tag) {
        this.hibernateTemplate.delete(tag);
    }

    @Override
    public void update(int id, String name) {

    }
}
