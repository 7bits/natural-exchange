package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.util.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Annoted spring controller class
 */
@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementController {

    @Resource(name = "advertisementDao")
    private AdvertisementDao advertisementDao;

    /**
     * Gives information about all advertisements for display
     * @return  jsp-page with advertisements information
     */

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "sortedBy",required = false) String sortedBy,@RequestParam(value = "sortOrder",required = false) String sortOrder) {

        //Создаем вьюшку по list.jsp, которая выведется этим контроллером на экран
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        SortOrder sortType = SortOrder.getViceVersa((sortOrder == null) ? null : SortOrder.valueOf(sortOrder));
        //TODO Check a behavior when sortOrder is wrong
        List<Advertisement> advertisements = this.advertisementDao.findAll(sortType, sortedBy);
        modelAndView.addObject("advertisements", advertisements);
        modelAndView.addObject("sortOrderNew", sortType.toString());

        List<String> categories = new ArrayList<String>();
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }


    /**
     * Gives information about one advertisement by id for display
     * @return  jsp-page with advertisement information
     */
    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(required = true) String id) {

        //Создаем вьюшку по list.jsp, которая выведется этим контроллером на экран
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        List<Advertisement> advertisements = this.advertisementDao.findAll();
        modelAndView.addObject("advertisements", advertisements);
        List<String> categories = new ArrayList<String>();
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }
}
