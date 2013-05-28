package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
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
        List<Advertisement> advertisements;
        List<String> categories = new ArrayList<String>();
        advertisements = sorting(modelAndView,sortedBy,sortOrder);//получили списко обьявлений с параметрами сортировки
        modelAndView.addObject("advertisements", advertisements);
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }


    public  List<Advertisement> sorting(ModelAndView modelAndView,String sortedBy,String sortOrder) {
        List<Advertisement> advertisements;
        if((sortedBy == null)&&(sortOrder==null)){
            advertisements = this.advertisementDao.findAll("none","createdDate");
            modelAndView.addObject("sortOrderNew","none");
        }
        else {
            if(sortedBy.equals("title")){
                if(sortOrder.equals("none")) {
                    sortOrder = "asc";
                    modelAndView.addObject("sortOrderNew","asc");
                }else if(sortOrder.equals("asc")) {
                    sortOrder = "desc";
                    modelAndView.addObject("sortOrderNew","desc");
                }else if(sortOrder.equals("desc")) {
                    sortOrder = "asc";
                    modelAndView.addObject("sortOrderNew","asc");
                }
                advertisements = this.advertisementDao.findAll(sortOrder,sortedBy);
            }
            else { //sortedBy == date
                if(sortOrder.equals("none")) {
                    sortOrder = "asc";
                    modelAndView.addObject("sortOrderNew","asc");
                }else if(sortOrder.equals("asc")) {
                    sortOrder = "desc";
                    modelAndView.addObject("sortOrderNew","desc");
                }else if(sortOrder.equals("desc")) {
                    sortOrder = "asc";
                    modelAndView.addObject("sortOrderNew","asc");
                }
                advertisements = this.advertisementDao.findAll(sortOrder,sortedBy);
            }
        }
        return advertisements;
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
