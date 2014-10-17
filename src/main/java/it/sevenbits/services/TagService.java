package it.sevenbits.services;

import it.sevenbits.repository.dao.TagDao;
import it.sevenbits.repository.entity.hibernate.AdvertisementEntity;
import it.sevenbits.repository.entity.hibernate.TagEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private AdvertisementService advertisementService;

    public List<String> selectTags(final String tags) {
        if (tags == null) {
            return null;
        }
        String trimString = StringUtils.trim(tags);
        String[] separateTags = StringUtils.split(trimString);
        List<String> result = new ArrayList<>();
        for (String str : separateTags) {
            result.add(str);
        }
        return result;
    }

    public Set<TagEntity> getTagsFromAdvertisementById(long id) {
        AdvertisementEntity advertisementEntity = (AdvertisementEntity) advertisementService.findAdvertisementById(id);
        return advertisementEntity.getTags();
    }

    public String getTagsFromAdvertisementByIdAsString(long id) {
        AdvertisementEntity advertisement = (AdvertisementEntity) advertisementService.findAdvertisementById(id);
        Set<TagEntity> tags = advertisement.getTags();
        String forTags = "";
        for (TagEntity tag : tags) {
            forTags += tag.getName() + " ";
        }
        return forTags;
    }
}
