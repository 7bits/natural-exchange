package it.sevenbits.dao.impl;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестовая имплементация интерфейса UserDao
 */
@Repository(value = "advertisementDao")
public class AdvertisementDaoImpl implements AdvertisementDao {

    @Override
    public void create(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    
    /**
    * Creates and returns the ad. Works with no database.
    */
    @Override
    public Advertisement findById(final Integer id) {
        Advertisement advertisement = new Advertisement();
 
       // advertisement.setId(1L);
        advertisement.setTitle("demi-season coat");
        advertisement.setText("girl's coat, the growth of 116. Looking for figure skates size 29.");
        advertisement.setPhotoFile("coat.jpg");
        advertisement.setCreatedDate(1000l);
        advertisement.setUpdatedDate(2000l);
        advertisement.setIsDeleted(false);
        return  advertisement;  
    }

    @Override
    public List<Advertisement> findAll() {

        List<Advertisement> advertisementList = new ArrayList<Advertisement>();
        Advertisement advertisement = new Advertisement();
 
      //  advertisement.setId(1L);
        advertisement.setTitle("demi-season coat");
        advertisement.setText("girl's coat, the growth of 116. Looking for figure skates size 29.");
        advertisement.setPhotoFile("coat.jpg");
        advertisement.setCreatedDate(1000l);
        advertisement.setUpdatedDate(2000l);
        advertisement.setIsDeleted(false);
        advertisementList.add(advertisement);

        advertisement = new Advertisement();
     //   advertisement.setId(2L);
        advertisement.setTitle("Sneakers");
        advertisement.setText("For boys, size 34. Need a scooter for children 2-4 years old");
        advertisement.setPhotoFile("sneakers.jpg");
        advertisement.setCreatedDate(2500l);
        advertisement.setUpdatedDate(2600l);
        advertisement.setIsDeleted(false);
        advertisementList.add(advertisement);

        advertisement = new Advertisement();
       // advertisement.setId(3L);
        advertisement.setTitle("");
        advertisement.setText(
            "Clothes for girl 2-3 years growth 98-104-116, and footwear. Looking for jeans (size 34), growth of 128."
        );
        advertisement.setPhotoFile("clothes.jpg");
        advertisement.setCreatedDate(3000l);
        advertisement.setUpdatedDate(3000l);
        advertisement.setIsDeleted(false);
        advertisementList.add(advertisement);

        return advertisementList;
 }

    @Override
    public void update(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    @Override
    public void delete(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }
}
